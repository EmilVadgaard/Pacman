package com.example;

import java.util.LinkedList;
import java.util.ArrayList;

public class BreadthFirstSearch implements SearchAlgorithm{

    public Direction search(Character ghost, Entity[][] map, int goalX, int goalY) {
        Node node = new Node(ghost.posX, ghost.posY, null);
        if (node.getPosX() == goalX && node.getPosY() == goalY) {
            return node.getDirection();
        }
        LinkedList<Node> frontier = new LinkedList<Node>();
        frontier.add(node);
        ArrayList<String> reached = new ArrayList<String>();
        reached.add(node.getPosX() + ""  + node.getPosY());
        while (frontier.peek() != null) {
            node = frontier.poll();
            expand(node, map);
            for (Node child: node.children) {
                if (child.getPosX() == goalX && child.getPosY() == goalY) {
                    return child.getDirection();
                }
                if (!reached.contains(child.getPosX() + "" + child.getPosY())) {
                    reached.add(child.getPosX() + "" + child.getPosY());
                    frontier.add(child);
                }
            }
        }
        return null;
    }

    private void expand(Node node, Entity[][] map) {
        if (isLegal(map, node.getPosX(), node.getPosY(), Direction.north)) {
            if (node.getDirection() != null) {
                node.addChild(new Node(node.getPosX(),node.getPosY()-1, node.getDirection()));
            } else {
                node.addChild(new Node(node.getPosX(),node.getPosY()-1, Direction.north));
            }
        }
        if (isLegal(map, node.getPosX(), node.getPosY(), Direction.west)) {
            if (node.getDirection() != null) {
                node.addChild(new Node(node.getPosX()-1,node.getPosY(), node.getDirection()));
            } else {
                node.addChild(new Node(node.getPosX()-1,node.getPosY(), Direction.west));
            }
        }
        if (isLegal(map, node.getPosX(), node.getPosY(), Direction.east)) {
            if (node.getDirection() != null) {
                node.addChild(new Node(node.getPosX()+1,node.getPosY(), node.getDirection()));
            } else {
                node.addChild(new Node(node.getPosX()+1,node.getPosY(), Direction.east));
            }
        }
        if (isLegal(map, node.getPosX(), node.getPosY(), Direction.south)) {
            if (node.getDirection() != null) {
                node.addChild(new Node(node.getPosX(),node.getPosY()+1, node.getDirection()));
            } else {
                node.addChild(new Node(node.getPosX(),node.getPosY()+1, Direction.south));
            }
        }
    }

    private boolean isLegal(Entity[][] map, int posX, int posY, Direction direction) {
        switch(direction) {
            case north:
                if (posY - 1 >= 0) {
                    return map[posY-1][posX] != Entity.wall;
                } else {
                    return false;
                }
            case west:
                if (posX - 1 >= 0) {
                    return map[posY][posX-1] != Entity.wall;
                } else {
                    return false;
                }
            case east:
                if (posX + 1 < map[0].length) {
                    return map[posY][posX+1] != Entity.wall;
                } else {
                    return false;
                }
            case south:
                if (posY + 1 < map.length) {
                    return map[posY+1][posX] != Entity.wall;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    private class Node {
        protected int posX;
        protected int posY;
        protected Direction initialDirection;
        protected ArrayList<Node> children;

        private Node(int posX, int posY, Direction initialDirection) {
            this.posX = posX;
            this.posY = posY;
            this.initialDirection = initialDirection;
            this.children = new ArrayList<Node>();
        }

        public void addChild(Node child) {
            children.add(child);
        }

        public int getPosX() {
            return posX;
        }
        public int getPosY() {
            return posY;
        }
        public Direction getDirection() {
            return this.initialDirection;
        }
    }
}
