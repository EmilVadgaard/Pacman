package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group; //Gruppe
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent; //Keyboard input
import javafx.scene.paint.Color; //Baggrund farve

import javafx.scene.Node;
import javafx.scene.Parent;



/**
 * JavaFX App
 */
public class App extends Application {
    public Group root;
    private static Stage stage;
    GameController gameController = new GameController();
    Display display;
    Button testButton = new Button("Test");


    @Override
    public void start(Stage stage) {
        /*GridPane*/
        root = new Group();
        display = gameController.getDisplay();
        
        root.getChildren().add(display.getCanvas());
        root.getChildren().add(gameController.getResetButton());
        root.getChildren().add(testButton);

        Scene scene = new Scene(root, 600, 650, Color.BLACK);
        
        scene.setOnKeyPressed(gameController::handleKeyPress);
        testButton.setOnAction(this::processTestButton);

        stage.setScene(scene);
        stage.setTitle("Pacman");
        
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    public void reset(Stage stage){
        root.getChildren().removeAll(display.getCanvas());
        root.getChildren().removeAll(gameController.getResetButton());
        
    }
    public void processTestButton (ActionEvent event){
        if(event.getSource() == testButton){
            System.out.print("Test3");
            reset(stage);
            System.out.print("Test2");
        }
    }

}