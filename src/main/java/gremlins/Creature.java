package gremlins;

import processing.core.PImage;
import java.util.*;

/**
* Creature class extended from GameObject class and implemented the Moving interface,
* a superclass for wizard and gremlin objects.
* Aside from methods and variables introduced in GameObjects, introduces direction, velocity
* and collision variables as well as a generic method for checking wall collision.
*/
public class Creature extends GameObject implements Moving {
    protected String direction;
    protected boolean collided;
    protected int velocity;

    /**
    * Constructor for creature, requires coordinates and sprite. 
    * Set collision to false
    * @param x x coordinate
    * @param y y coordinate
    */
    public Creature(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.collided = false;
    }

    /**
    * Move the creature.
    * Done by taking the current direction and increment coordinates based on velocity
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
    * Generic class for detecting wall collision.
    * Loop through every walls to see if any is blocking the direction of movement.
    * Has GameObject as upperbound for parameter, but is mainly used for Stonewall and Brickwall.
    * @param list a generic list of objects (stone/brick)
    * @param direction the creature's current direction
    * @return whether the creature has collided with a wall or not
     */
    public <T extends GameObject> boolean checkCollisionWall(ArrayList<T> list, String direction) {
        int thisLeft = this.getX();
        int thisRight = this.getRight();
        int thisTop = this.getY();
        int thisBottom = this.getBottom();

        for (T wall: list) {
            int wallLeft = wall.getX();
            int wallRight = wall.getRight();
            int wallTop = wall.getY();
            int wallBottom = wall.getBottom();

            // Collision of 'this' in regards to wall
            boolean collisionTop = direction == "down"
                                    && thisLeft == wallLeft 
                                    && thisBottom + 1 == wallTop;
            boolean collisionBottom = direction == "up"
                                        && thisLeft == wallLeft
                                        && thisTop - 1 == wallBottom;
            boolean collisionLeft = direction == "right"
                                    && thisTop == wallTop
                                    && thisRight + 1 == wallLeft;
            boolean collisionRight = direction == "left"
                                    && thisTop == wallTop
                                    && thisLeft - 1 == wallRight;
            if (collisionTop || collisionBottom || collisionLeft || collisionRight) {
                return true;
            }
        }

        return false;
    }
}