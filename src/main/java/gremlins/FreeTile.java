package gremlins;

/** 
* FreeTile class, denoting any non significant tiles and has a pair of coordinates 
*/
public class FreeTile {
    protected int x;
    protected int y;

    /**
    * Constructor for a free tile
    * @param x x coordinate
    * @param y y coordinate
     */
    public FreeTile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}