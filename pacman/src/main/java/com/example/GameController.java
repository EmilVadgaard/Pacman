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
        Timer spawnTimer = new Timer(120);
        Timer playerMoveTimer = new Timer(game.getPlayer().getSpeed());
        Timer characterAnimationTimer = new Timer(5);
        Timer ghostWakeTimer = new Timer(180);
        Timer powerUpTimer = new Timer(600);
        ArrayList<Timer> timers = new ArrayList<Timer>();
        timers.add(playerMoveTimer);
        for (Ghost ghost: game.getGhosts()) {
            timers.add(new Timer(ghost.getSpeed()));
        }
        //timers.add(ghostMoveTimer);
        timers.add(characterAnimationTimer);
        timers.add(ghostWakeTimer);
        timers.add(powerUpTimer);
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                display.update();
                // Timer so game does not start instantly
                if (!game.playerIsSpawned()) {
                    spawnTimer.decrementTime();
                    ghostWakeTimer.decrementTime();
                    if (spawnTimer.getTime() == 0) {
                        game.spawnPlayer();
                    } else {
                        display.showReady();
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
                                display.setPowerupRunningOut(false);
                            }
                            game.eat(game.getPlayer().getPosX(), game.getPlayer().getPosY());
                        }
                        playerMoveTimer.reset();
                    }

                    // Ghost movement events
                    int count = 1;
                    for (Ghost ghost: game.getGhosts()) {
                        if (timers.get(count).getTime() == 0) {
                            game.moveGhost(ghost);
                            timers.get(count).reset();
                        }
                        timers.get(count).setTime(ghost.getSpeed());
                        count++;
                        if (game.characterCollision(ghost, game.getPlayer())) {
                            game.handleCollision(ghost, game.getPlayer());
                        }
                    }

                    // Reset ghosts after a powerup has finished
                    if (powerUpTimer.getTime() <= 180) {
                        display.setPowerupRunningOut(true);
                    }
                    if (powerUpTimer.getTime() <= 0) {
                        game.endPowerUpTime();
                        display.setPowerupRunningOut(false);
                    }

                    // Character animation handling
                    if (characterAnimationTimer.getTime() == 0) {
                        display.incrementFrames();
                        characterAnimationTimer.reset();
                    }

                    if (ghostWakeTimer.getTime() <= 0) {
                        game.wakeNextGhost();
                        ghostWakeTimer.reset();
                    }

                    if (!game.playerIsSpawned()) {
                        spawnTimer.reset();
                    }

                    if (game.isGameOver()){
                        stop();
                        if (game.getLifeCounter() < 0) {
                            display.showResetMenu("Game Over.");
                        } else {
                            display.showResetMenu("YOU WIN!");
                        }
                        resetButton.setVisible(true);
                        resetButton.setDisable(false);
                    }
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

    public void processResetButton (ActionEvent event){
        resetButton.setDisable(true);
        resetButton.setVisible(false);
        game.resetGame("map.txt");
        run();
    }

    public Button getResetButton(){
        return resetButton;
    }
    
}