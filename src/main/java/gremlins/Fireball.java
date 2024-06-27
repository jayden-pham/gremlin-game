package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Fireball class extending Projectile class. Fireballs travel in one direction and disappear
* upon collisions.
 */
public class Fireball extends Projectile {
    protected boolean start;

    /**
    * Constructor for fireball.
    * @param x x coordinate
    * @param y y coordinate
    * @param sprite image
    * @param direction direction of wizard, fireball travels in the same direction
     */
    public Fireball (int x, int y, PImage sprite, String direction) {
        super(x, y, sprite);
        this.direction = direction;
        this.start = true;
    }

    /**
    * Handles the logic for fireball.
    * Fireballs don't start moving until the second frame. This makes for a better looking
    * animation
     */
    public void tick() {
        if (!this.start) {
            this.move();
        } else {
            this.start = false;
        }
    }

    /**
    * Check for collisions with significant objects that cause fireballs to disappear.
    * Loop through objects then check for collisions with any, then set destroyed variable
    * accordingly.
    * @param stonewallList list of stonewalls
    * @param brickwallList list of brickwalls
    * @param gremlinList list of gremlins
    * @param slimeList list of slimes
     */
    public void checkOverlapAll(ArrayList<Stonewall> stonewallList,
                                ArrayList<Brickwall> brickwallList,
                                ArrayList<Gremlin> gremlinList,
                                ArrayList<Slime> slimeList) {
        
        if (this.checkOverlap(stonewallList)
            || this.checkOverlap(brickwallList)
            || this.checkOverlap(gremlinList)
            || this.checkOverlap(slimeList)) {
                this.destroyed = true;
                return;
            }
        this.destroyed = false;
    }
}