package gremlins;

/**
* Interface for objects that can move.
* Including creatures and projectiles.
 */
interface Moving {

    /** 
    * Move object by incrementing its coordinates.
     */
    public void move();
}