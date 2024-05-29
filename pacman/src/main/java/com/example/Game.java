package com.example;

import java.net.URL;
import java.io.File;
import java.util.ArrayList;

/**
 * The class responsible for all the logic of the Pac-man game.
 */
public class Game implements Ruleset {
    private Grid grid;
    private Player player;
    private int lifeCounter;
    private int score;
    private int pellets;
    private int multiplier;
    private ArrayList<Ghost> ghosts;
    private boolean playerIsDead;

    /**
     * Returns a new instance of the Pac-man game.
     * @param filepath A valid filepath to a .txt file that has even length lines.
     */
    public Game(String filepath){
        resetGame(filepath);
    }

    /**
     * Resets the entire game.
     * @param filepath A valid filepath to a .txt file that has even length lines.
     */
    public void resetGame(String filepath){
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(14, 21, 10);
        this.lifeCounter = 2;
        this.score = 0;
        this.pellets = countPellets();
        this.ghosts = new ArrayList<Ghost>();
        this.multiplier = 1;
        this.playerIsDead = true;
        this.ghosts.add(new Ghost(12,13,10,6, new NormalGhostState(this)));
        this.ghosts.add(new Ghost(13, 13, 10, 12, new NormalGhostState(this)));
        this.ghosts.add(new Ghost(14,13,10,18, new NormalGhostState(this)));
        this.ghosts.add(new Ghost(15,13,10,24, new NormalGhostState(this)));
    }

    /**
     * Moves a given character one space in the direction they are facing.
     * @param character The character which needs to be moved.
     */
    public void moveCharacter(Character character){
        if (character.getPosX() == grid.getLengthX() - 1 && character.getDirection() == Direction.east) {
            character.setPos(0, character.getPosY());
        } else if (character.getPosX() == 0 && character.getDirection() == Direction.west) {
            character.setPos(grid.getLengthX() - 1, character.getPosY());
        } else if (character.getPosY() == 0 && character.getDirection() == Direction.north) {
            character.setPos(character.getPosX(), grid.getLengthY() - 1);
        } else if (character.getPosY() == grid.getLengthY() - 1 && character.getDirection() == Direction.south) {
            character.setPos(character.getPosX(), 0);
        } else {
            character.move();
        }
    }

    /**
     * Moves all the ghosts one space.
     */
    public void moveGhosts() {
        for (Ghost ghost: ghosts) {
            if (!ghost.isSleeping()) {
                Direction direction = ghost.nextDirection(player.getPosX(), player.getPosY());
                switchDirection(ghost, direction);
                // If ghost is in dead state and has reached its home
                if (!ghost.hasCollision() && ghost.getHomeX() == ghost.getPosX() && ghost.getHomeY() == ghost.getPosY()) {
                    ghost.changeState(new NormalGhostState(this));
                    ghost.sleep();
                    ghost.switchDirection(Direction.north);
                } else {
                    moveCharacter(ghost);
                }
            }
        }
    }

    /**
     * Moves a specific ghost one space.
     * @param ghost The specific ghost which needs to be moved.
     */
    public void moveGhost(Ghost ghost) {
        if (!ghost.isSleeping()) {
            Direction direction = ghost.nextDirection(player.getPosX(), player.getPosY());
            switchDirection(ghost, direction);
            // If ghost is in dead state and has reached its home
            if (!ghost.hasCollision() && ghost.getHomeX() == ghost.getPosX() && ghost.getHomeY() == ghost.getPosY()) {
                ghost.changeState(new NormalGhostState(this));
                ghost.setSpeed(12);
                //ghost.sleep();
                ghost.switchDirection(Direction.north);
                ghost.setPos(13, 11);
            } else {
                moveCharacter(ghost);
            }
        }
    }

    /**
     * Returns true if the given character can move in the given direction.
     * @param character The character which is being moved.
     * @param direction Direction of the character.
     * @return True if the movement in the specified direction is allowed.
     */
    public boolean isLegal(Character character, Direction direction) {
        if (direction == null) {
            return false;
        }
        return nextPosition(character.getPosX(), character.getPosY(), direction, character.hasCollision()) != null;
    }

    /**
     * Returns the following position if a move would be made from the specific position and in the specific direction.
     * @param posX X-value position.
     * @param posY Y-value position.
     * @param direction Direction from the position.
     * @param hasCollision false if certain object should be seen as empty spaces.
     * @return an array of lenth 2, with the first as the x value and the second as the y value of a position.
     * @return null if is outside of the movable area.
     */
    public int[] nextPosition(int posX, int posY, Direction direction, boolean hasCollision) {
        int[] coords = new int[2];
        switch(direction) {
            case north:
                if (posY == 0) {
                    coords[1] = grid.getLengthY() - 1;
                } else {
                    coords[1] = posY - 1;
                }
                coords[0] = posX;
                break;
            case west:
                if (posX == 0) {
                    coords[0] = grid.getLengthX() - 1;
                } else {
                    coords[0] = posX - 1;
                }
                coords[1] = posY;
                break;
            case east:
                if (posX == grid.getLengthX() - 1) {
                    coords[0] = 0;
                } else {
                    coords[0] = posX + 1;
                }
                coords[1] = posY;
                break;
            case south:
                if (posY == grid.getLengthY() - 1) {
                    coords[1] = 0;
                } else {
                    coords[1] = posY + 1;
                }
                coords[0] = posX;
                break;
            default:
                return null;
        }
        if (grid.getEntity(coords[0], coords[1]) == Entity.wall) {
            return null;
        } else if (hasCollision && grid.getEntity(coords[0], coords[1]) == Entity.door) {
            return null;
        } else {
            return coords;
        }
    }

    /**
     * Returns true if the entity at the coordinates given can be eaten.
     * @param x X-value position.
     * @param y Y-value position.
     * @return True if the position has a pellet or big pellet.
     */
    public boolean isEatable(int x, int y) {
        return grid.getEntity(x, y) == Entity.pellet || grid.getEntity(x, y) == Entity.bigPellet;
    }

    /**
     * Returns true if the entity at the coordinates is a big pellet.
     * @param x X-value position.
     * @param y Y-value position.
     * @return True if position has a big pellet.
     */
    public boolean isBigPellet(int x, int y) {
        return grid.getEntity(x, y) == Entity.bigPellet;
    }

    /**
     * Removes an entity from the game grid, and adds score corresponding to the
     * entity removed.
     * Changes the gamestate to power state, if a big pellet is eaten. 
     * @param x X-value position.
     * @param y Y-value position.
     */
    public void eat(int x, int y) {
        if (grid.getEntity(x, y) == Entity.pellet) {
            addScore(10);
            pellets--;
        } else if (grid.getEntity(x, y) == Entity.bigPellet) {
            addScore(50);
            pellets--;
            for (Ghost ghost: ghosts) {
                if (ghost.hasCollision()) {
                    ghost.changeState(new ScaredGhostState(this));
                    ghost.setSpeed(18);
                }
            }
        }
        grid.setEntity(x,y,Entity.empty);
    }

    /**
     * Resets all ghosts to their normal state and sets the score multiplier to 1.
     */
    public void endPowerUpTime() {
        for (Ghost ghost: ghosts) {
            if (ghost.hasCollision() && ghost.canBeEaten()) {
                ghost.changeState(new NormalGhostState(this));
                ghost.setSpeed(12);
            }
        }
        multiplier = 1;
    }

    /**
     * Changes a given character's direction.
     * @param character The character whose direction will be changed.
     * @param desiredDirection The new direction.
     */
    public void switchDirection(Character character, Direction desiredDirection){
        character.switchDirection(desiredDirection);
    }

    /**
     * Returns a given character's current direction.
     * @param character The character
     * @return Direction of character.
     */
    public Direction getCharacterDirection(Character character){
        return character.getDirection();
    }

    /**
     * Returns the current game grid.
     * @return Grid of game.
     */
    public Grid getGrid() {
        return this.grid;
    }

    /*
     * Counts the pellets in the grid.
     */
    private int countPellets() { 
        int pellets = 0;
        for (int y = 0; y < grid.getLengthY(); y++) {
            for (int x = 0; x < grid.getLengthX(); x++) {
                if (grid.getEntity(x, y) == Entity.pellet || grid.getEntity(x, y) == Entity.bigPellet) {
                    pellets++;
                }
            }
        }
        return pellets;
    }

    /**
     * Returns the player character.
     * @return The player.
     */
    public Player getPlayer() {     
        return player;
    }

    /**
     * Returns the ghost characters.
     * @return An array of ghost characters.
     */
    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    /**
     * Returns true if atleast one of the game's ghosts are sleeping.
     * @return True if one or more ghosts are sleeping.
     */
    public boolean hasSleepingGhosts() {
        for (Ghost ghost: ghosts) {
            if (ghost.isSleeping()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Wakes the next sleeping ghost in the order of their instantiation.
     */
    public void wakeNextGhost() {
        boolean haveAwoken = false;
        for (Ghost ghost: ghosts) {
            if (ghost.isSleeping() && !haveAwoken) {
                ghost.wake();
                ghost.setPos(13, 11);
                haveAwoken = true;
            }
        }
    }

    /**
     * Returns true if a ghost and another character are in the exact same position.
     * @param ghost The ghost.
     * @param player The player.
     * @return True if player has a collision with the ghost.
     */
    public boolean characterCollision(Ghost ghost, Character player) {
        if (ghost.hasCollision()) {
            return ghost.getPosX() == player.getPosX() && ghost.getPosY() == player.getPosY();
        } else {
            return false;
        }
    }

    /**
     * Kills the given ghost if it can be eaten, kills the player otherwise.
     * @param ghost The ghost.
     * @param player The player.
     */
    public void handleCollision(Ghost ghost, Character player) {
        if (ghost.canBeEaten()) {
            addScore(200 * multiplier);
            multiplier = multiplier * 2;
            ghost.changeState(new DeadGhostState(this));
            ghost.setSpeed(4);
        } else {
            subtractLifeCounter();
            resetCharacters();
            playerIsDead = true;
            multiplier = 1;
        }
    }

    /**
     * Resets all character's positions, and returns ghosts to their normal state.
     */
    public void resetCharacters() {
        player.setPos(14, 21);
        player.switchDirection(Direction.west);
        for (Ghost ghost: ghosts) {
            ghost.setPos(ghost.getHomeX(), ghost.getHomeY());
            ghost.sleep();
            ghost.changeState(new NormalGhostState(this));
            ghost.setSpeed(12);
            ghost.switchDirection(Direction.north);
        }
    } 

    /**
     * Returns true if player is dead.
     * @return True if player is dead.
     */
    public boolean isPlayerDead() {  
        return playerIsDead;
    }

    /**
     * Sets spawns the player.
     */
    public void spawnPlayer() {
        playerIsDead = false;
    }

    /**
     * Returns the current amount of extra lives the player has.
     * @return Life count.
     */
    public int getLifeCounter() {
        return lifeCounter;
    }

    /**
     * Returns the current score.
     * @return Score count.
     */
    public int getScore(){
        
        return score;
    }

    /*
     * Removes a single life from the player.
     */
    private void subtractLifeCounter(){
        lifeCounter--;
    }
    
    /**
     * Adds to the current score.
     * @param n Amount of points added to score
     */
    public void addScore(int n){   
        score = score + n;
    }

    /**
     * Returns true if there are no more pellets to be eaten, or if the player has no remaining lives.
     * @return True if there are no pellets or life counter is under 0.
     */
    public boolean isGameOver() {
        return pellets == 0 || lifeCounter < 0;
    }
}
