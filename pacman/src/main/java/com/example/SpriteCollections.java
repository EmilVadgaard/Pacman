package com.example;

import javafx.scene.canvas.GraphicsContext;

public interface SpriteCollections {
    public void getEntitySprite(GraphicsContext gc, String group, int type, int x, int y, int factor);

    public void getCharacterSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int factor, int offset);

    public void getSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int size);

    public void getSprite(GraphicsContext gc, String group, int type, int x, int y, int size);
}
