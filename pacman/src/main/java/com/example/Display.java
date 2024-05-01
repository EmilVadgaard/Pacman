package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group; //Gruppe
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent; //Keyboard input
import javafx.scene.paint.Color; //Baggrund farve

public class Display extends GridPane{
    Game game;

    int factor = 20;

    // midlertidige
    

    public Display(Game game){
        this.game = game;
        addWalls();
    }

    private void addWalls(){
        Grid grid = game.getGrid();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    Rectangle wall = new Rectangle();
                    wall.setFill(Color.BLUE);
                    wall.setHeight(factor);
                    wall.setWidth(factor);
                    add(wall, x,y);
                }
            }
        }
    }

    public void update() {
        Grid grid = game.getGrid();
        Character[] characters = game.getCharacters();

        Rectangle player = new Rectangle();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                
                
                if (grid.getEntity(x, y) == Entity.pellet){
                    Rectangle pellet = new Rectangle();
                    pellet.setFill(Color.WHITE);
                    pellet.setHeight(factor/2);
                    pellet.setWidth(factor);
                    add(pellet, x,y);
                }
            }
        }
    }

    private void updatePellets() {
        
    }

    private void updatePlayer() {
        
    }

    private void updateGhosts() {
        
    }

}