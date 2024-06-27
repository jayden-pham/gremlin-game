package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Class Gremlin extending Creature class, gremlins changes direction when colliding with
* walls and shoot slimes on an interval.
* Aside from shooting, gremlins respawn if killed and can be frozen by power up.
 */
public class Gremlin extends Creature {
    protected boolean start;
    protected boolean respawn;
    protected ArrayList<String> availableDirections;
    // For shooting slimes.
    protected int slimeTimer;
    protected int slimeTimerTarget;
    // For snowball powerup.
    protected boolean frozen;
    protected int frozenTimer;
    protected int frozenTimerTarget;

    /**
    * Gremlin constructor.
    * Includes a start flag as choosing directions at the start is different.
    * @param x x coordinate
    * @param y y coordinate
    * @param sprite sprite
    * @param enemyCooldown interval for shooting slims */
    public Gremlin(int x, int y, PImage sprite, double enemyCooldown) {
        super(x, y, sprite);
        this.velocity = 1;
        this.start = true;
        this.respawn = false;
        this.availableDirections = new ArrayList<String>();

        this.slimeTimer = 0;
        this.slimeTimerTarget = (int)(App.FPS * enemyCooldown);

        this.frozen = false;
        this.frozenTimer = 0;
        this.frozenTimerTarget = App.FPS * 10;
    }

    /**
    * Handles logic for gremlins
    * @param stonewallList list of stonewalls
    * @param brickwallList list of brickwalls
    * @param slimeList list of slimes
    * @param freeTileList list of free tiles
    * @param slimeSprite 
    * @param wizardX wizard x coordinate
    * @param wizardY wizard y coordinate
     */
    public void tick(
        ArrayList<Stonewall> stonewallList, ArrayList<Brickwall> brickwallList,
        ArrayList<Slime> slimeList, PImage slimeSprite, ArrayList<FreeTile> freeTileList,
        int wizardX, int wizardY
        ) {

        if (this.start) {
            // Get available directions to move in.
            this.availableDirections =
                this.getDirections(stonewallList, brickwallList, this.direction, this.start);
            int directionsNum = this.availableDirections.size();
            if (directionsNum == 0) {
                this.direction = "none";
            } else {
            // Choose random directions from array
                Random randomizer = new Random();
                this.direction = this.availableDirections.get(randomizer.nextInt(directionsNum));
            }
            this.start = false;
        } else {
            // If gremlin collided with a wall, get directions different than previously to move in.
            boolean collidedStone = this.checkCollisionWall(stonewallList, this.direction);
            boolean collidedBrick = this.checkCollisionWall(brickwallList, this.direction);
            this.collided = collidedStone || collidedBrick;

            if (this.collided) {
                this.availableDirections =
                    this.getDirections(stonewallList, brickwallList, this.direction, this.start);
                int directionsNum = this.availableDirections.size();
                if (directionsNum == 0) {
                    this.direction = "none";
                } else {
                    Random randomizer = new Random();
                    this.direction = this.availableDirections.get(randomizer.nextInt(directionsNum));
                }
            }
        }

        // Respawn on a new tile at least 10 blocks away if shot.
        if (this.respawn) {
            this.respawn(freeTileList, wizardX, wizardY);
        }

        // Move and shoot if not frozen
        if (!this.frozen) {
            if (this.slimeTimer < this.slimeTimerTarget) {
                this.slimeTimer++;
            } else if (this.slimeTimer == this.slimeTimerTarget) {
                this.shoot(slimeList, slimeSprite);
                this.slimeTimer++;
            } else {
                this.slimeTimer = 0;
            }
            this.move();
        } else {
            // Runs down frozen timer.
            this.frozenTimer++;
            if (this.frozenTimer == this.frozenTimerTarget) {
                this.unfreeze();
            }
        }
    }

    /**
    * Shoot slimes
    * @param slimeList list of slimes
    * @param sprite spritee
     */
    public void shoot(ArrayList<Slime> slimeList, PImage sprite) {
        slimeList.add(new Slime(this.getX(), this.getY(), sprite, this.direction));
    }

    /**
    * Check if gremlin has been shot, if so set respawn flag to true
     */
    public void checkOverlapAll(ArrayList<Fireball> fireballList) {
        if (this.checkOverlap(fireballList)) {
            this.respawn = true;
        }
    }

    /**
    * Generic method to remove any blocked directions from a list.
    * Take in a list of available directions to move in, then remove any blocked direction.
    * @param wallList list of walls (stone/brick)
    * @param directions list of available directions
     */
    public <T extends GameObject> void checkBlocked(
        ArrayList<T> wallList, ArrayList<String> directions) {

        int gremlinLeft = this.getX();
        int gremlinRight = this.getRight();
        int gremlinTop = this.getY();
        int gremlinBottom = this.getBottom();

        for (T wall: wallList) {
            int wallLeft = wall.getX();
            int wallRight = wall.getRight();
            int wallTop = wall.getY();
            int wallBottom = wall.getBottom();

            boolean blockedTop = gremlinLeft == wallLeft && gremlinTop - 1 == wallBottom;
            boolean blockedBottom = gremlinLeft == wallLeft && gremlinBottom + 1 == wallTop;
            boolean blockedLeft = gremlinTop == wallTop && gremlinLeft - 1 == wallRight;
            boolean blockedRight = gremlinTop == wallTop && gremlinRight + 1 == wallLeft;


            if (blockedTop) {
                directions.remove("up");
            }
            if (blockedBottom) {
                directions.remove("down");
            }
            if (blockedLeft) {
                directions.remove("left");
            }
            if (blockedRight) {
                directions.remove("right");
            }
        }
    }

    /**
    * Find a list of available directions to move in
    * @param stonewallList list of stonewalls
    * @param brickwallList list of brickwalls
    * @param start flag that's true at the start and at respawning
    * @return list of available directions */
    public ArrayList<String> getDirections(
        ArrayList<Stonewall> stonewallList, ArrayList<Brickwall> brickwallList,
        String currentDirection, boolean start) {

        ArrayList<String> availableDirections = new ArrayList<String>();
        availableDirections.add("up");
        availableDirections.add("down");
        availableDirections.add("left");
        availableDirections.add("right");
        
        // Remove any blocked directions
        this.checkBlocked(stonewallList, availableDirections);
        this.checkBlocked(brickwallList, availableDirections);

        // Remove the previous direction if more than 1 direction and gremlin collides with wall.
        if (!start) {
            String oppositeDirection = "none";
            if (availableDirections.size() > 1) {
                if (currentDirection == "up") {
                    oppositeDirection = "down";
                } else if (currentDirection == "down") {
                    oppositeDirection = "up";
                } else if (currentDirection == "left") {
                    oppositeDirection = "right";
                }else if (currentDirection == "right") {
                    oppositeDirection = "left";
                }
                availableDirections.remove(oppositeDirection);
            }
        }

        return availableDirections;
    }

    /**
    * Respawn gremlins.
    * If gremlin is shot, respawn on a free tile at a distance of at least 10 blocks away from
    * the wizard.
    * @param freeTileList list of free tiles
    * @param wizardX wizard's x coordinate
    * @param wizardY wizard's y coordinate
     */
    public void respawn(ArrayList<FreeTile> freeTileList, int wizardX, int wizardY) {
        // Create list of free tiles at least 10 blocks away.
        ArrayList<FreeTile> availableTiles = new ArrayList<FreeTile>();
        for (FreeTile freeTile: freeTileList) {
            int xDistance = freeTile.getX() - wizardX;
            int yDistance = freeTile.getY() - wizardY;
            if (xDistance * xDistance + yDistance * yDistance >= 220 * 220) {
                availableTiles.add(freeTile);
            }
        }

        // Choose random tile to respawn in
        Random randomizer = new Random();
        FreeTile spawnTile = availableTiles.get(randomizer.nextInt(availableTiles.size()));
        this.setX(spawnTile.getX());
        this.setY(spawnTile.getY());

        // Reset slime timer and find available directions again.
        this.slimeTimer = 0;
        this.start = true;
        this.respawn = false;
    }

    /**
    * Freeze gremlin adn start frozen timer.
     */
    public void freeze() {
        this.frozenTimer = 0;
        this.frozen = true;
    }

    /**
    * Unfreeze gremlin.
     */
    public void unfreeze() {
        this.frozen = false;
    }

    /**
    * Check if gremlin is frozen.
    * @return is frozen or not
     */
    public boolean isFrozen() {
        return this.frozen;
    }
}