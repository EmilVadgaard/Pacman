package com.example;

/**
 * A ghost in the game of Pac-man.
 */
public class Ghost extends Character {
    private boolean sleeping;
    private int intelligence;
    private GhostState state;
    private int homeX;
    private int homeY;

    /**
     * Creates an instance of a ghost.
     * @param posX Start position x value.
     * @param posY Start position y value.
     * @param speed Speed of ghost.
     * @param intelligence How random the ghost moves (lower is more). Must be greater than 3.
     * @param state The state the ghost should start in.
     */
    public Ghost(int posX, int posY, int speed, int intelligence, GhostState state) {
        this.posX = posX;
        this.posY = posY;
        this.homeX = posX;
        this.homeY = posY;
        this.speed = speed;
        this.currentDirection = Direction.north;
        this.sleeping = true;
        this.intelligence = intelligence;
        this.state = state;
    }

    /**
     * Finds the ghosts next direction.
     * @param goalX the player's position x value.
     * @param goalY the player's position y value.
     * @return The direction the ghost wants to move.
     */
    public Direction nextDirection(int goalX, int goalY) {
        return state.nextDirection(posX, posY, goalX, goalY, homeX, homeY, intelligence, currentDirection);
    }

    /**
     * @return true if the ghost can be eaten.
     */
    public boolean canBeEaten() {
        return state.canBeEaten();
    }

    /**
     * @return true if the ghost has collision.
     */
    public boolean hasCollision() {
        return state.hasCollision();
    }

    /**
     * Changes the ghosts current behavior.
     */
    public void changeState(GhostState state) {
        this.state = state;
    }

    /**
     * Wakes the ghost.
     */
    public void wake() {
        this.sleeping = false;
    }

    /**
     * Makes the ghost sleep.
     */
    public void sleep() {
        this.sleeping = true;
    }

    /**
     * @return The ghosts intelligence.
     */
    public int getIntelligence() {
        return intelligence;
    }

    /**
     * @return True if the player is sleeping.
     */
    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * @return The x value of the ghosts start position.
     */
    public int getHomeX() {
        return homeX;
    }

    /**
     * @return The y value of the ghosts start position.
     */
    public int getHomeY() {
        return homeY;
    }
}