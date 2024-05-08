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
        this.display = new Display(this.game,game.getScoreboard());
        this.desiredDirection = Direction.north;
        run();
    }

    public void run(){
        Timer playerMoveTimer = new Timer(15);
        Timer playerAnimationTimer = new Timer(5);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        timers.add(playerAnimationTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                for (Timer t : timers) {
                    t.decrementTime();
                }
                display.setOffset(playerMoveTimer.getTime());

                if (playerMoveTimer.getTime() == 0) {
                    if (game.isLegal(game.getCharacters()[0], desiredDirection)) {
                        game.switchDirection(game.getCharacters()[0], desiredDirection);
                    }
                    if (game.isLegal(game.getCharacters()[0], game.getCharacterDirection(game.getCharacters()[0]))) {
                        game.moveCharacter(game.getCharacters()[0]);
                        // conditional collision checks after player moves (pellets now, ghosts later)
                        if (game.isEatable(game.getCharacters()[0].getPosX(),game.getCharacters()[0].getPosY())) {
                            game.eat(game.getCharacters()[0].getPosX(),game.getCharacters()[0].getPosY());
                        }
                    }
                    playerMoveTimer.reset();
                }
                if (playerAnimationTimer.getTime() == 0) {
                    display.incrementPlayerFrame();
                    playerAnimationTimer.reset();
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
                // if (game.isLegal(game.getCharacters()[0], desiredDirection) && desiredDirection != game.getCharacters()[0].getDirection()) {
                //     playerMoveTimer.reset();
                // }
                break;
            case DOWN:
                this.desiredDirection = Direction.south;
                // if (game.isLegal(game.getCharacters()[0], desiredDirection) && desiredDirection != game.getCharacters()[0].getDirection()) {
                //     playerMoveTimer.reset();
                // }                
                break;
            case RIGHT:
                this.desiredDirection = Direction.east;
                // if (game.isLegal(game.getCharacters()[0], desiredDirection) && desiredDirection != game.getCharacters()[0].getDirection()) {
                //     playerMoveTimer.reset();
                // }                
                break;
            case LEFT:
                this.desiredDirection = Direction.west;
                // if (game.isLegal(game.getCharacters()[0], desiredDirection) && desiredDirection != game.getCharacters()[0].getDirection()) {
                //     playerMoveTimer.reset();
                // }
                break;
            default:
                break;
        }
    }
    
    
}