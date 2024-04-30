package com.example;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;
import javafx.stage.Stage;

public class GameController {
    private final Display display;
    private final Game game;
    
    

    public GameController() {
        this.game = new Game("map.txt");
        this.display = new Display(this.game);
        run();
    }

    public void run(){
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                display.update();
            }
        }.start();
    }

    public Display getDisplay() {
        return this.display;
    }
    


    /*
    public void setStage(Stage stage){
        display.setStage(stage);
    }

    public void setRoot(Group root){
        display.setRoot(root);
    }

    public void showDisplay(){
        display.display("test");
    }
    */
    
    
}