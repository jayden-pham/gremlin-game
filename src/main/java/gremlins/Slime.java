package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Slime class extending Projectile class, moves at a constant velocity and disappear upon collision.
 */
public class Slime extends Projectile {

    /**
    * Constructor for slime */
    public Slime (int x, int y, PImage sprite, String direction) {
        super(x, y, sprite);
        this.direction = direction;
    }

    /**
    * Handles logic for slimes
     */
    public void tick() {
        this.move();
    }

    /**
    * Check for collision with walls or fireballs. 
    * If collision is detected, set destroyed flag to true
    * @param stonewallList list of stonewalls
    * @param brickwallList list of brickwalls
    * @param fireballList list of fireballs
     */
    public void checkOverlapAll(
        ArrayList<Stonewall> stonewallList, ArrayList<Brickwall> brickwallList,
        ArrayList<Fireball> fireballList) {

        if (this.checkOverlap(stonewallList) || this.checkOverlap(brickwallList)
            || this.checkOverlap(fireballList)) {
                this.destroyed = true;
        }
    }
}