package com.example;

public class PacmanRuleset implements Ruleset {
    private Game game;

    public PacmanRuleset(Game game) {
        this.game = game;
    }

    public int[] nextPosition(int posX, int posY, Direction direction) {
        if (!game.isLegal(new Ghost(posX, posY, 0, 0), direction) || !inBounds(posX, posY)) {
            return null;
        }
        int[] coords = new int[2];
        switch(direction) {
            case north:
                if (posY == 0) {
                    coords[1] = game.getGrid().getLengthY() - 1;
                } else {
                    coords[1] = posY - 1;
                }
                coords[0] = posX;
                return coords;
            case west:
                if (posX == 0) {
                    coords[0] = game.getGrid().getLengthX() - 1;
                } else {
                    coords[0] = posX - 1;
                }
                coords[1] = posY;
                return coords;
            case east:
                if (posX == game.getGrid().getLengthX() - 1) {
                    coords[0] = 0;
                } else {
                    coords[0] = posX + 1;
                }
                coords[1] = posY;
                return coords;
            case south:
                if (posY == game.getGrid().getLengthY() - 1) {
                    coords[1] = 0;
                } else {
                    coords[1] = posY + 1;
                }
                coords[0] = posX;
                return coords;
            default:
                return null;
        }
    }
    
    private boolean inBounds(int posX, int posY) {
        if (posX < 0) {
            return false;
        } else if (posX >= game.getGrid().getLengthX()) {
            return false;
        } else if (posY < 0) {
            return false;
        } else if (posY >= game.getGrid().getLengthY()) {
            return false;
        } else {
            return true;
        }
    }
}
