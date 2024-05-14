package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group; //Gruppe
import javafx.scene.Scene;
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

    @Override
    public void start(Stage stage) {
        /*GridPane*/
        root = new Group();
        display = gameController.getDisplay();
        root.getChildren().add(display.getCanvas());
        Scene scene = new Scene(root, 600, 650, Color.BLACK);
        

        stage.setScene(scene);
        stage.setTitle("Pacman");
        
        stage.show();

        scene.setOnKeyPressed(gameController::handleKeyPress);
    }

    public static void main(String[] args) {
        launch();
    }

}