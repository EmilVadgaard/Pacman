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
        Timer spawnTimer = new Timer(180);
        Timer playerMoveTimer = new Timer(game.getPlayer().getSpeed());
        Timer ghostMoveTimer = new Timer(game.getGhosts().get(0).getSpeed());
        Timer characterAnimationTimer = new Timer(12);
        Timer ghostWakeTimer = new Timer(300);
        Timer powerUpTimer = new Timer(600);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        timers.add(ghostMoveTimer);
        timers.add(characterAnimationTimer);
        timers.add(ghostWakeTimer);
        timers.add(powerUpTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                display.update();
                // Timer so game does not start instantly
                if (!game.playerIsSpawned()) {
                    spawnTimer.decrementTime();
                    if (spawnTimer.getTime() == 0) {
                        game.spawnPlayer();
                    } else if (spawnTimer.getTime() <= 60) {
                        display.showCount(1);
                    } else if (spawnTimer.getTime() <= 120) {
                        display.showCount(2);
                    } else if (spawnTimer.getTime() <= 180) {
                        display.showCount(3);
                    }
                } else {
                    for (Timer t : timers) {
                        t.decrementTime();
                    }
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
                    }

                    // Character animation handling
                    if (characterAnimationTimer.getTime() == 0) {
                        display.incrementFrames();
                        characterAnimationTimer.reset();
                    }

                    if (ghostWakeTimer.getTime() == 0) {
                        game.wakeNextGhost();
                        ghostWakeTimer.reset();
                    }

                    if (!game.hasSleepingGhosts()) {
                        ghostWakeTimer.reset();
                    }
                    if (!game.playerIsSpawned()) {
                        spawnTimer.reset();
                    }

                    if (game.isGameOver()){
                        stop();
                        display.showResetMenu();
                        resetButton.setVisible(true);
                        resetButton.setDisable(false);
                    }
                }
                //display.update();

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