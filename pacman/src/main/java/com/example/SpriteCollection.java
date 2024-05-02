package com.example;

import javafx.scene.image.Image;

public class SpriteCollection {
    Image sheet = new Image(getClass().getResource("/sprite-sheet.png").toString());
    private Sprite[][] pacman = new Sprite[4][4];
    
    private class Sprite{
        private int pivotX;
        private int pivotY;
        
        public Sprite(int pivotX, int pivotY){
            this.pivotX = pivotX;
            this.pivotY = pivotY;
        }

        public int[] getSprite(){
            int[] res = {pivotX, pivotY};
            return res;
        }
    }

    private class EntitySprite{
        private Sprite[][] spriteList;
        private String name;

        public EntitySprite(String name){
            this.name = name;
        }
    }

    public Image getSprite(String name, int type, int frame){

    }
}
