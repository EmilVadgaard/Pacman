package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

public class GameController {
    private final Display display;
    private final Game game;

    private Direction desiredDirection;
    
    

    public GameController() {
        this.game = new Game("map.txt");
        this.display = new Display(this.game);
        this.desiredDirection = Direction.north;
        run();
    }

    public void run(){
        Timer playerMoveTimer = new Timer(12);
        Timer ghostMoveTimer = new Timer(12);
        Timer characterAnimationTimer = new Timer(6);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        timers.add(ghostMoveTimer);
        timers.add(characterAnimationTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                for (Timer t : timers) {
                    t.decrementTime();
                }
                display.setOffest(playerMoveTimer.getTime());

                if (playerMoveTimer.getTime() == 0) {
                    if (game.isLegal(game.getPlayer(), desiredDirection)) {
                        game.switchDirection(game.getPlayer(), desiredDirection);
                    }
                    if (game.isLegal(game.getPlayer(), game.getCharacterDirection(game.getPlayer()))) {
                        game.moveCharacter(game.getPlayer());
                    }
                    for (Ghost ghost: game.ghosts) {
                        if (game.characterCollision(game.getPlayer(), ghost)) {
                            game.resetCharacters();
                        }
                    }
                    playerMoveTimer.reset();
                }

                if (ghostMoveTimer.getTime() == 0) {
                    game.moveGhosts();
                    for (Ghost ghost: game.ghosts) {
                        if (game.characterCollision(game.getPlayer(), ghost)) {
                            game.resetCharacters();
                        }
                    }
                    ghostMoveTimer.reset();
                }

                if (characterAnimationTimer.getTime() == 0) {
                    display.incrementFrames();
                    characterAnimationTimer.reset();
                }
                display.update();
            }
        }.start();
    }

    public Display getDisplay() {
        return this.display;
    }

    public void handleKeyPress(KeyEvent event) {
        switch(event.getCode()){
            case UP:
                this.desiredDirection = Direction.north;
                // if (game.isLegal(game.getPlayer(), desiredDirection) && desiredDirection != game.getPlayer().getDirection()) {
                //     playerMoveTimer.reset();
                // }
                break;
            case DOWN:
                this.desiredDirection = Direction.south;
                // if (game.isLegal(game.getPlayer(), desiredDirection) && desiredDirection != game.getPlayer().getDirection()) {
                //     playerMoveTimer.reset();
                // }                
                break;
            case RIGHT:
                this.desiredDirection = Direction.east;
                // if (game.isLegal(game.getPlayer(), desiredDirection) && desiredDirection != game.getPlayer().getDirection()) {
                //     playerMoveTimer.reset();
                // }                
                break;
            case LEFT:
                this.desiredDirection = Direction.west;
                // if (game.isLegal(game.getPlayer(), desiredDirection) && desiredDirection != game.getPlayer().getDirection()) {
                //     playerMoveTimer.reset();
                // }
                break;
            default:
                break;
        }
    }
    
    
}