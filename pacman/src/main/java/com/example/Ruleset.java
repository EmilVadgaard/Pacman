package com.example;

/**
 * Interface for finding the next position of a move in a given direction.
 */
public interface Ruleset {
    /**
     * Returns the following position if a move would be made from the specific position and in the specific direction.
     * @param hasCollision false if certain objects should be seen as empty spaces.
     * @return an array of lenth 2, with the first as the x value and the second as the y value of a position.
     * null if resulting position is outside of the movable area.
     */
    public int[] nextPosition(int posX, int posY, Direction direction, boolean hasCollision);
}