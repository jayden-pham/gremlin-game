package gremlins;

import processing.core.PImage;
import java.util.*;

/**
 * Brickwall class, preventing entities from travelling through and can be destroyed
*/
public class Brickwall extends GameObject implements Destructible {
    protected boolean destroyed;
    protected int spriteTimer;

    /**
     * Constructor for a brickwall, requires the x and y coordinates and a sprite
     * @param x x coordinate
     * @param y y coordinate
     * @param sprite brickwall sprite
    */
    public Brickwall(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.destroyed = false;
        this.spriteTimer = 0;
    }

    /**
     * Handles the logic for brickwall
    */
    public void tick() {
        // Increment timer to keep track of when to change sprite
        if (this.destroyed) {
            this.spriteTimer++;
        }
    }

    /**
    * Check for collision with fireballs.
    * Loop through all fireballs in the list to check for overlap based on the coordinates of both
    * objects.
    * If collision is detected with any, initiate destruction sequence.
    * @param fireballList a list of fireball objects
     */
    public void checkOverlapAll(ArrayList<Fireball> fireballList) {
        if (this.checkOverlap(fireballList)) {
            this.destroyed = true;
        }
    }

    /**
    * Check if the brickwall has been destroyed.
    * This occurs on frame 17 when all 4 destruction sprites has ran its course.
    * @return whether the wall has been destroyed or not
     */
    @Override
    public boolean isDestroyed() {
        if (this.spriteTimer == 17) {
            return true;
        }
        return false;
    }

    /**
    * Return the counter for the destruction sequence.
    * @return what frame the destruction sequence is on
     */
    public int getSpriteTimer() {
        return this.spriteTimer;
    }
}