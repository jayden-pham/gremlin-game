package gremlins;

import processing.core.PImage;

/**
* Class Exit designating the exit destination on the map */
public class Exit extends GameObject {
    
    /**
    * Constructor for exit, requires x and y coordinatese and a sprite
    * @param x x coordinate
    * @param y y coordinate
    * @param sprite image
     */
    public Exit(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }
}