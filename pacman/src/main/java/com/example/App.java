package com.example;

import javafx.application.Application;
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color; 

/**
 * JavaFX App
 */
public class App extends Application {
    public Group root;
    private static Stage stage;
    private GameController gameController;
    Display display;


    @Override
    /**
     * Starts the game
     * @param stage The stage of the game.
     */
    public void start(Stage stage) {
        GameController gameController = new GameController();
        
        root = new Group();
        display = gameController.getDisplay();
        
        root.getChildren().addAll(display.getCanvas(), gameController.getResetButton());

        Scene scene = new Scene(root, 600, 650, Color.BLACK);

        scene.getStylesheets().add(getClass().getResource("/fontstyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Pacman");
        
        stage.show();
        
        scene.setOnKeyPressed(gameController::handleKeyPress);

        gameController.run();
    }

    /**
     * Launches the game.
     */
    public static void main(String[] args) {
        launch();
    }

}