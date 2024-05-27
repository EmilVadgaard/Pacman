package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.stage.Stage;

public class GameController {
    private Display display;
    private Game game;
    private Button resetButton;
    private Direction desiredDirection;
    
    

    public GameController() {
        this.game = new Game("map.txt");
        this.display = new Display(this.game);
        this.desiredDirection = Direction.north;

        resetButton = new Button("Reset game");
        resetButton.setLayoutX(238);
        resetButton.setLayoutY(320);
        resetButton.setVisible(false);
        resetButton.setDisable(true);
        //resetButton.setCancelButton(true);
        Font buttonFont = Font.font("Courier New", FontWeight.BOLD, 17);
        resetButton.setFont(buttonFont);
        resetButton.setOnAction(this::processResetButton);

        run();
    }

    public void run(){
        Timer playerMoveTimer = new Timer(game.getPlayer().getSpeed());
        Timer ghostMoveTimer = new Timer(game.getGhosts().get(0).getSpeed());
        Timer characterAnimationTimer = new Timer(12);
        Timer ghostWakeTimer = new Timer(300);
        Timer powerUpTimer = new Timer(2000);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        timers.add(ghostMoveTimer);
        timers.add(characterAnimationTimer);
        timers.add(ghostWakeTimer);
        timers.add(powerUpTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                for (Timer t : timers) {
                    t.decrementTime();
                }
                display.setOffset(playerMoveTimer.getTime());

                // Player movement events
                if (playerMoveTimer.getTime() == 0) {
                    if (game.isLegal(game.getPlayer(), desiredDirection)) {
                        game.switchDirection(game.getPlayer(), desiredDirection);
                    }
                    if (game.isLegal(game.getPlayer(), game.getCharacterDirection(game.getPlayer()))) {
                        game.moveCharacter(game.getPlayer());
                    }
                    for (Ghost ghost: game.getGhosts()) {
                        if (game.characterCollision(ghost, game.getPlayer())) {
                            game.handleCollision(ghost, game.getPlayer());
                        }
                    }
                    if (game.isEatable(game.getPlayer().getPosX(), game.getPlayer().getPosY())) {
                        if (game.isBigPellet(game.getPlayer().getPosX(), game.getPlayer().getPosY())) {
                            powerUpTimer.reset();
                            // TYPECASTING !!!!!!!!!!!!!!!!!!
                            ghostMoveTimer.setTime((int)(game.getGhosts().get(0).getSpeed()*1.25));
                        }
                        game.eat(game.getPlayer().getPosX(), game.getPlayer().getPosY());
                    }
                    playerMoveTimer.reset();
                }

                // Ghost movement events
                if (ghostMoveTimer.getTime() == 0) {
                    game.moveGhosts();
                    for (Ghost ghost: game.getGhosts()) {
                        if (game.characterCollision(ghost, game.getPlayer())) {
                            game.handleCollision(ghost, game.getPlayer());
                        }
                    }
                    ghostMoveTimer.reset();
                }

                // Reset ghosts after a powerup has finished
                if (powerUpTimer.getTime() == 0) {
                    game.endPowerUpTime();
                    ghostMoveTimer.setTime(game.getGhosts().get(0).getSpeed());
                }

                // Character animation handling
                if (characterAnimationTimer.getTime() == 0) {
                    display.incrementFrames();
                    characterAnimationTimer.reset();
                }
                display.update();

                if (ghostWakeTimer.getTime() == 0) {
                    game.wakeNextGhost();
                    ghostWakeTimer.reset();
                }
                if (!game.hasSleepingGhosts()) {
                    ghostWakeTimer.reset();
                }
                if (game.isGameOver()){
                    stop();
                    display.showResetMenu();
                    resetButton.setVisible(true);
                    resetButton.setDisable(false);
                }
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

    public void processResetButton (ActionEvent event){
        //if(event.getSource() == resetButton){
            resetButton.setDisable(true);
            resetButton.setVisible(false);
            game.resetGame("map.txt");
            run();
        //}
    }

    public Button getResetButton(){
        return resetButton;
    }
    
}