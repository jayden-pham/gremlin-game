package gremlins;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.*;

/**
* Wizard class extending Creature. 
* Wizard is controlled by the player, and can move and fire fireballs using inputs.
* Wizard can use power ups and lose a life if hit by slime or gremlins.
*/
public class Wizard extends Creature {
    protected int currentKey;
    protected boolean isMoving;
    // When killed
    protected boolean reset;
    protected int lives;
    protected boolean nextLevel;

    protected int fireballTimer;
    protected int fireballTimerTarget;
    protected boolean loaded;

    protected boolean powerUp;
    protected int powerUpTimer;
    protected int powerUpTimerTarget;

    protected boolean inPortal;

    /**
    * Constructor for wizard class.
    * @param wizardCooldown cooldown for fireballs */
    public Wizard(int x, int y, PImage sprite, int lives, double wizardCooldown) {
        super(x, y, sprite);
        this.direction = "right";
        this.velocity = 2;
        
        this.isMoving = false;
        this.reset = false;
        this.nextLevel = false;
        this.lives = lives;

        this.fireballTimerTarget = (int) (App.FPS * wizardCooldown);
        this.fireballTimer = this.fireballTimerTarget;
        this.loaded = true;

        this.powerUpTimer = 0;
        this.powerUpTimerTarget = App.FPS * 10;
        
        this.inPortal = false;
    }

    /**
    * Handles logic for wizard
     */
    public void tick(ArrayList<Stonewall> stonewallList, ArrayList<Brickwall> brickwallList) {
        // If fireball isn't loaded, increment timer
        if (!this.loaded) {
            this.fireballTimer++;
            if (this.fireballTimer == this.fireballTimerTarget) {
                this.loaded = true;
            }
        }
        // Incrementing power up timer until time is up
        if (this.powerUp) {
            this.powerUpTimer++;
            if (this.powerUpTimer == this.powerUpTimerTarget) {
                this.powerUp = false;
                this.powerUpTimer = 0;
            }
        }
        // Change direction based on current key
        if (currentKey == 38) {
            this.direction = "up"; 
        } else if (currentKey == 40) {
            this.direction = "down"; 
        } else if (currentKey == 37) {
            this.direction = "left"; 
        } else if (currentKey == 39) {
            this.direction = "right"; 
        }
        // If wizard is not in a whole tile, move to a whole tile
        if (!this.inWholeTile()) {
            this.finishMovement(this.direction);
            return;
        }

        // Check for collisions in the direction of movement.
        boolean collidedStone = this.checkCollisionWall(stonewallList, this.direction);
        boolean collidedBrick = this.checkCollisionWall(brickwallList, this.direction);
        this.collided = collidedStone || collidedBrick;
        if (!this.collided && this.isMoving) {
            this.move();
        }
    }

    /**
    * Check if a fireball shot is loaded
     */
    public boolean isLoaded() {
        return this.loaded;
    }

    /**
    * Shoot fireball. Once shot, loaded is set to false and the timer resets.
     */
    public void shoot(ArrayList<Fireball> fireballList, PImage sprite) {
        fireballList.add(new Fireball(this.getX(), this.getY(), sprite, this.direction));
        this.loaded = false;
        this.fireballTimer = 0;
    }

    /**
    * Check if the game needs to be reset upon losing a life
    * @return reset value
     */
    public boolean isReset() {
        return this.reset;
    }

    public int getLives() {
        return this.lives;
    }

    /**
    * Check if wizard is in a whole tile
    * @return boolean on if in whole tile or not
     */
    public boolean inWholeTile() {
        if (this.getX() % 20 == 0 && this.getY() % 20 == 0) {
            return true;
        }
        return false;
    }

    /**
    * Move in the current direction until a whole tile is reached
     */
    public void finishMovement(String direction) {
        if (direction == "left" && this.getX() % 20 != 0) {
            this.setX(this.getX() - 2);
        } else if (direction == "right" && this.getX() % 20 != 0) {
            this.setX(this.getX() + 2);
        } else if (direction == "up" && this.getY() % 20 != 0) {
            this.setY(this.getY() - 2);
        } else if (direction == "down" && this.getY() % 20 != 0) {
            this.setY(this.getY() + 2);
        }
    }

    /**
    * Get current key input
     */
    public int getCurrentKey() {
        return this.currentKey;
    }

    public void setCurrentKey(int currentKey) {
        this.currentKey = currentKey;
    }

    /**
    * Set whether wizard can move or not
     */
    public void setMovement(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
    * Check if a new level needs to be loaded
     */
    public boolean getNextLevel() {
        return this.nextLevel;
    }

    /**
    * Check for collisions with significant objects.
    * If collides with gremlin or slime, lose a life and reset the map.
    * If collides with the exit, set nextLevel flag to true to load next level.
    * If collides with snowball, freeze all gremlins and starts power up timer.
    * If collides with a portal, teleport to another random portal.
    * @param gremlinList list of gremlins
    * @param slimeList list of slimes
    * @param exitList list of exits
    * @param snowballList list of snowballs
    * @param portalList list of portals
     */
    public void checkOverlapAll(ArrayList<Gremlin> gremlinList, ArrayList<Slime> slimeList,
                                ArrayList<Exit> exitList, ArrayList<Snowball> snowballList,
                                ArrayList<Portal> portalList) {
        if (this.checkOverlap(gremlinList) || this.checkOverlap(slimeList)) {
            this.reset = true;
            this.lives--;
        } 
        if (this.checkOverlap(exitList)) {
            this.nextLevel = true;
        }
        if (this.checkOverlap(snowballList)) {
            this.powerUp = true;
            this.powerUpTimer = 0;
            for (Gremlin gremlin: gremlinList) {
                gremlin.freeze();
            }
        }
        /* 
        * A list of portals other than the current one is created by specifying a distance of
        * at least 2 blocks away from the wizard. While this might not work if there are portals
        * very close to the current one, this is a pragmatic solution with map design in mind as
        * maps will logically place portals away from each other.
        *
        * The inPortal flag is used to ensure that wizard has to step out of portal then enter
        * again in order to teleport.
        */
        if (this.checkOverlap(portalList) && !this.inPortal) {
            ArrayList<Portal> otherPortals = new ArrayList<Portal>();
            for (Portal portal: portalList) {
                int xDistance = portal.getX() - this.getX();
                int yDistance = portal.getY() - this.getY();
                if (xDistance * xDistance + yDistance * yDistance >= 40 * 40) {
                    otherPortals.add(portal);
                }
            }
            // Randomly teleports to new portal, then stops movement until new key is input
            Random randomizer = new Random();
            Portal spawnTile = otherPortals.get(randomizer.nextInt(otherPortals.size()));
            this.setX(spawnTile.getX());
            this.setY(spawnTile.getY());
            this.isMoving = false;
            this.inPortal = true;
        } else if (!this.checkOverlap(portalList) && this.inPortal){
            this.inPortal = false;
        }
    }

    public int getFireballCooldown() {
        return this.fireballTimerTarget;
    }

    public int getFireballTimer() {
        return this.fireballTimer;
    }

    public int getSnowballTimer() {
        return this.powerUpTimer;
    }

    public int getSnowballCooldown() {
        return this.powerUpTimerTarget;
    }

    public boolean isPoweredUp() {
        return this.powerUp;
    }

    public String getDirection() {
        return this.direction;
    }
}