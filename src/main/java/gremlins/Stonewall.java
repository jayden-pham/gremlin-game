package gremlins;

import processing.core.PImage;

/**
* Stonewall class extending GameObject. Indestructible.
*/
public class Stonewall extends GameObject {

    /**
    * Constructor for stonewall
     */
    public Stonewall(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }
}