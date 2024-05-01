package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent; //Keyboard input
import javafx.scene.paint.Color; //Baggrund farve

public class Display {
    Game game;
    int factor = 20;

    Canvas canvas;
    GraphicsContext gc;

    Circle pellet;

    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,500);
        this.gc = canvas.getGraphicsContext2D();


        addWalls();
    }

    private void addWalls(){
        Grid grid = game.getGrid();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x*factor,y*factor,factor,factor);
                    // Rectangle wall = new Rectangle(x*factor, y*factor, factor, factor);
                    // wall.setFill(Color.BLUE);
                    //wall.setHeight(factor);
                    //wall.setWidth(factor);
                }
            }
        }
    }

    public void update() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,600,500);
        updatePellets();
        addWalls();

        Character[] characters = game.getCharacters();
        gc.setFill(Color.YELLOW);
        gc.fillRect(characters[0].getPosX()*factor,characters[0].getPosY()*factor,factor,factor);
        
        //add(player, characters[0].getPosX(), characters[0].getPosY());
        
    }

    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.pellet){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x*factor+factor/2-factor/6/2,y*factor+factor/2-factor/6/2,factor/6,factor/6);
                    //pellet.setRadius(factor/8);
                    
                }
            }
        }
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updatePlayer() {
        
    }

    private void updateGhosts() {
        
    }

}