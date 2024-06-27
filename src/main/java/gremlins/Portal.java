package gremlins;

import processing.core.PImage;

/**
* Portal class extending GameObject, wizard teleports to another random portal when collided */
public class Portal extends GameObject {
    
    /**
    * Constructor for portals
     */
    public Portal(int x, int y, PImage sprite) {
        super(x, y, sprite);
    }
}