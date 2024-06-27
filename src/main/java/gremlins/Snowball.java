package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Snowball class extending GameObject and implementing Destructible. Freeze all gremlins upon
* contact with wizard and disappear.
 */
public class Snowball extends GameObject implements Destructible {
    protected boolean destroyed;

    /** 
    * Constructor for snowball
     */
    public Snowball(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.destroyed = false;
    }

    /**
    * Check for collision with wizard.
    * If collided, set destroyed flag to true
    @param wizardList list of wizards
    */
    public void checkOverlapAll(ArrayList<Wizard> wizardList) {
        if (this.checkOverlap(wizardList)) {
            this.destroyed = true;
        }
    }

    /**
    * Return destroyed flag
    * @return destroyed
     */
    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }
}