package gremlins;

import processing.core.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BrickwallTest {

    @Test
    public void constructor() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Brickwall brickwall = new Brickwall (0, 0, app.loadImage("src/main/resources/gremlins/brickwall.png"));
        assertNotNull(brickwall);
    }
}