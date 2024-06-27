package gremlins;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.*;

/**
* GameObject class, the parent class for all objects in the game.
* Includes shared variables such as coordinates and sprite, as well as common methods such as
* draw and getters and setters. Also include a generic method used for checking collisions. */
public class GameObject {
    protected int x;
    protected int y;
    protected PImage sprite;

    /**
    * Basic constructor for a GameObject
    * @param x x coordinate
    * @param y y coordinate
    * @param sprite sprite
    */
    public GameObject(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /**
    * Draw method to draw objects onto screen.
    * Draw every objects using coordinates and sprites.
    * @param app the application window
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
    * Get rightmost coordinate of the object
     */
    public int getRight() {
        int spriteRight = this.x + this.sprite.width - 1;
        return spriteRight;
    }

    /**
    * Get bottommost coordinate of the object
     */
    public int getBottom() {
        int spriteBottom = this.y + this.sprite.height - 1;
        return spriteBottom;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PImage getSprite() {
        return this.sprite;
    }

    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
    * Generic method used for checking collisions between 'this' and any objects.
    * Loop through all objects in given list. List can be of any type of game objects.
    * Collision is checked by getting the edges of both objects then checks for overlap.
    * @param list list of objects to check
    * @return whether there is overlap or not
     */
    public <T extends GameObject> boolean checkOverlap(ArrayList<T> list) {
        int thisLeft = this.getX();
        int thisRight = this.getRight();
        int thisTop = this.getY();
        int thisBottom = this.getBottom();
        
        for (T object: list) {
            int objectLeft = object.getX();
            int objectRight = object.getRight();
            int objectTop = object.getY();
            int objectBottom = object.getBottom();

            // Overlap of 'this' in regards to the other object
            boolean overlapTop = thisTop <= objectTop && thisBottom >= objectTop;
            boolean overlapBottom = thisTop <= objectBottom && thisBottom >= objectBottom;
            boolean overlapLeft = thisLeft <= objectLeft && thisRight >= objectLeft;
            boolean overlapRight = thisLeft <= objectRight && thisRight >= objectRight;

            if (overlapTop && (overlapLeft || overlapRight)) {
                return true;
            }
            if (overlapBottom && (overlapLeft || overlapRight)) {
                return true;
            }
        }

        return false;
    }
}