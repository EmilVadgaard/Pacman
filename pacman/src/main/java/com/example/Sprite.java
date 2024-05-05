package com.example;

public class Sprite {
    int x;
    int y;
    int size;

    public Sprite(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int[] get(){
        int[] res = {x,y, size};
        return res;
    }
}
