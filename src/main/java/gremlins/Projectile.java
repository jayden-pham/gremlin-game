package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Projectile class extending Game Object and implementing the Destructible and Moving interfaces.
* Projectiles moves at a constant velocity and disappear upon collisions.
 */
public class Projectile extends GameObject implements Destructible, Moving {
    protected String direction;
    protected int velocity;
    protected boolean destroyed;

    /**
    * Constructor for projectiles.
    * All projectiles have a velocity of 4.
     */
    public Projectile(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.destroyed = false;
        this.velocity = 4;
    }

    /**
    * Move the projectile every frame by incrementing its coordinates.
     */
    @Override
    public void move() {
        if (this.direction == "up") {
            this.setY(this.getY() - this.velocity);
        } else if (this.direction == "down") {
            this.setY(this.getY() + this.velocity);
        } else if (this.direction == "left") {
            this.setX(this.getX() - this.velocity);
        } else if (this.direction == "right") {
            this.setX(this.getX() + this.velocity);
        }
    }

    /**
    * Check whether the projectile is destroyed or not.
    * @return destroyed or not
     */
    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }
}