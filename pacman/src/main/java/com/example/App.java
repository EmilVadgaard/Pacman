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


/**
 * JavaFX App
 */
public class App extends Application {
    public Group root;
    private static Stage stage;
    GameController gameController = new GameController();

    @Override
    public void start(Stage stage) {
        /*GridPane*/
        root = new Group();

        Scene scene = new Scene(gameController.getDisplay(), 600, 500, Color.BLACK);

        stage.setScene(scene);
        stage.setTitle("Pacman");

        //System.out.println("Starter");
        //gameController.setStage(stage);
        //GameController.setRoot(root);
        //gameController.showDisplay();
        /*Display display = new Display(stage, root);
        display.display("test");*/
        stage.show();
    }

    public static Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}