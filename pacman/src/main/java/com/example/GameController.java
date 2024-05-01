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
        Timer playerMoveTimer = new Timer(15);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                for (Timer t : timers) {
                    t.decrementTime();
                }
                if (playerMoveTimer.getTime() == 0) {
                    if (game.isLegal(game.getCharacters()[0], desiredDirection)) {
                        game.switchDirection(game.getCharacters()[0], desiredDirection);
                    }
                    if (game.isLegal(game.getCharacters()[0], game.getCharacterDirection(game.getCharacters()[0]))) {
                        game.moveCharacter(game.getCharacters()[0]);
                    }
                    playerMoveTimer.reset();
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
                break;
            case DOWN:
                this.desiredDirection = Direction.south;
                break;
            case RIGHT:
                this.desiredDirection = Direction.east;
                break;
            case LEFT:
                this.desiredDirection = Direction.west;
                break;
            default:
                break;
        }
    }
    
    
}