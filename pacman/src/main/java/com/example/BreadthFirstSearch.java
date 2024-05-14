package com.example;

import java.util.LinkedList;
import java.util.ArrayList;

public class BreadthFirstSearch implements SearchAlgorithm{
    Ruleset ruleset;

    public BreadthFirstSearch(Ruleset ruleset) {
        this.ruleset = ruleset;
    }

    public Direction search(int startX, int startY, int goalX, int goalY) {
        Node node = new Node(startX, startY, null);
        if (node.getPosX() == goalX && node.getPosY() == goalY) {
            return node.getDirection();
        }
        LinkedList<Node> frontier = new LinkedList<Node>();
        frontier.add(node);
        ArrayList<String> reached = new ArrayList<String>();
        reached.add(node.getPosX() + ""  + node.getPosY());
        while (frontier.peek() != null) {
            node = frontier.poll();
            expand(node);
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

    private void expand(Node node) {
        int[] north = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.north);
        // check if next position exists / is legal
        if (north != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(north[0], north[1], node.getDirection()));
            } else {
                node.addChild(new Node(north[0], north[1], Direction.north));
            }
        }
        int[] west = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.west);
        if (west != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(west[0], west[1], node.getDirection()));
            } else {
                node.addChild(new Node(west[0], west[1], Direction.west));
            }
        }
        int[] east = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.east);
        if (east != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(east[0], east[1], node.getDirection()));
            } else {
                node.addChild(new Node(east[0],east[1], Direction.east));
            }
        }
        int[] south = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.south);
        if (south != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(south[0], south[1], node.getDirection()));
            } else {
                node.addChild(new Node(south[0], south[1], Direction.south));
            }
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