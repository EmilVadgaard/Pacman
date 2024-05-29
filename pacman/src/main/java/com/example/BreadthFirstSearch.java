package com.example;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * An implementation of the Breadth First Search algorithm.
 */
public class BreadthFirstSearch implements SearchAlgorithm{
    private Ruleset ruleset;
    private boolean collision;

    /**
     * 
     * @param ruleset Transition model for the search algorithm
     * @param collision Whether or not the search algorithm should look for paths phasing through certain entities.
     */
    public BreadthFirstSearch(Ruleset ruleset, boolean collision) {
        this.ruleset = ruleset;
        this.collision = collision;
    }

    /**
     * Finds the initial direction of the optimal path from the start position to the goal position.
     */
    public Direction search(int startX, int startY, int goalX, int goalY) {
        Node node = new Node(startX, startY, null);
        if (node.getPosX() == goalX && node.getPosY() == goalY) {
            return node.getDirection();
        }
        LinkedList<Node> frontier = new LinkedList<Node>();
        frontier.add(node);
        ArrayList<String> reached = new ArrayList<String>();
        reached.add(node.getKey());
        while (frontier.peek() != null) {
            node = frontier.poll();
            expand(node);
            for (Node child: node.getChildren()) {
                if (child.getPosX() == goalX && child.getPosY() == goalY) {
                    return child.getDirection();
                } else if (!reached.contains(child.getKey())) {
                    reached.add(child.getKey());
                    frontier.add(child);
                }
            }
        }
        return null;
    }

    /*
     * Calculates all the valid direction to move from a certain position and adds them to a Node's list
     * of children.
     */
    private void expand(Node node) {
        int[] north = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.north, collision);
        // check if next position exists / is legal
        if (north != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(north[0], north[1], node.getDirection()));
            } else {
                node.addChild(new Node(north[0], north[1], Direction.north));
            }
        }
        int[] west = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.west, collision);
        if (west != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(west[0], west[1], node.getDirection()));
            } else {
                node.addChild(new Node(west[0], west[1], Direction.west));
            }
        }
        int[] east = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.east,collision);
        if (east != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(east[0], east[1], node.getDirection()));
            } else {
                node.addChild(new Node(east[0],east[1], Direction.east));
            }
        }
        int[] south = ruleset.nextPosition(node.getPosX(), node.getPosY(), Direction.south,collision);
        if (south != null) {
            if (node.getDirection() != null) {
                node.addChild(new Node(south[0], south[1], node.getDirection()));
            } else {
                node.addChild(new Node(south[0], south[1], Direction.south));
            }
        }
    }

    /*
     * Node used in the search tree.
     */
    private class Node {
        private int posX;
        private int posY;
        private Direction initialDirection;
        private ArrayList<Node> children;
        private String key;

        private Node(int posX, int posY, Direction initialDirection) {
            this.posX = posX;
            this.posY = posY;
            this.initialDirection = initialDirection;
            this.children = new ArrayList<Node>();
            this.key = posX + " " + posY;
        }

        private void addChild(Node child) {
            children.add(child);
        }

        private ArrayList<Node> getChildren() {
            return children;
        }

        private int getPosX() {
            return posX;
        }

        private int getPosY() {
            return posY;
        }

        private String getKey() {
            return key;
        }

        private Direction getDirection() {
            return this.initialDirection;
        }
    }
}