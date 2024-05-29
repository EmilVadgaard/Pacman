package com.example;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for drawing sprites to screen.
 */
public interface SpriteCollections {
    /**
     * Draws the related entity sprite to given GraphicsContext based on inputed keys, type, screen position and size.
     * @param gc GraphicsContext that is being drawn on.
     * @param group specifies which character the desired sprite is.
     * @param type the type of the desired sprite. 
     * @param frame specifies which frame of the animation.
     * @param factor sets the size of the sprite. Note it should correspond to factor size of display.
     */
    public void drawEntitySprite(GraphicsContext gc, String group, int type, int x, int y, int factor);

    /**
     * Draws the related character sprite to given GraphicsContext based on inputed keys, frame, screen position and size.
     * @param gc GraphicsContext that is being drawn on.
     * @param group specifies which character the desired sprite is.
     * @param type the direction of the desired sprite. 
     * @param frame specifies which frame of the animation.
     * @param factor sets the size of the sprite. Note it should correspond to factor size of display.
     */
    public void drawCharacterSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int factor);

    /**
     * Draws the related character sprite to given GraphicsContext based on inputed keys, frame, screen position and size.
     * @param gc GraphicsContext that is being drawn on.
     * @param group specifies which character the desired sprite is.
     * @param type the direction of the desired sprite. 
     * @param frame specifies which frame of the animation.
     * @param factor sets the size of the sprite. Note it should correspond to factor size of display.
     */
    public void drawSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int size);

    /**
     * Draws the related entity sprite to given GraphicsContext based on inputed keys, type, screen position and size.
     * @param gc GraphicsContext that is being drawn on.
     * @param group specifies which character the desired sprite is.
     * @param type the type of the desired sprite. 
     * @param frame specifies which frame of the animation.
     * @param factor sets the size of the sprite. Note it should correspond to factor size of display.
     */
    public void drawSprite(GraphicsContext gc, String group, int type, int x, int y, int size);
}
