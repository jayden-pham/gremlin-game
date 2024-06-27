package gremlins;

/**
* Interface destructible containing the isDestroyed method.
* Used for removing destroyed objects from list in App.
 */
interface Destructible {

    /**
    * @return whether object has been destroyed or not
    */
    public boolean isDestroyed();
}