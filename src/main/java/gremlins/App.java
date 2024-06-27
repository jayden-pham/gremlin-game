package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;
import java.util.*;
import java.io.*;

/**
* App class responsible for running the game. 
* Objects are loaded in, the logics are handled then objects are drawn to the applicaition window.
 */
public class App extends PApplet {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;
    public static final int FPS = 60;
    public static final Random randomGenerator = new Random();
    public String configPath;

    // Game values
    protected int levelIndex;
    protected int levelNumber;
    protected int numberOfLevels;
    protected double wizardCooldown;
    protected double enemyCooldown;
    protected int remainingLives;

    // Current key input
    protected int currentKey; 

    // List of objects
    protected ArrayList<Stonewall> stonewallList;
    protected ArrayList<Brickwall> brickwallList;
    protected ArrayList<Fireball> fireballList;
    protected ArrayList<Gremlin> gremlinList;
    protected ArrayList<Slime> slimeList;
    protected ArrayList<FreeTile> freeTileList;
    protected ArrayList<Exit> exitList;
    protected ArrayList<Snowball> snowballList;
    protected ArrayList<Wizard> wizardList;
    protected ArrayList<Portal> portalList;

    /* 
    * Since snowballs appears later after the game starts, they can't be instantiated during setup.
    * As such, a hashmap with X and Y coordinates are kept, and an array of X coordinates is kept
    * to serve as keys.
    */
    protected HashMap<Integer, Integer> snowballCoordinates;
    protected ArrayList<Integer> snowballCoordX;
    
    // Player controlled
    protected Wizard wizard;

    // Boolean start denoting the start of a game. Used for differentiating between different setup.
    protected boolean start;

    // Flags used for snowballs creation
    protected int powerUpNum;
    protected boolean snowballRespawn;
    protected int snowballRespawnTimer;
    protected int snowballRespawnTimertarget;
    protected int snowballRespawnX;
    protected int snowballRespawnY;
    // Timer since the start of the game, used for snowball
    protected int startTimer;

    protected boolean keyReleased;
    protected boolean restart;

    // Images that needs to be referred back to 
    protected PImage wizardLeftPic;
    protected PImage wizardRightPic;
    protected PImage wizardUpPic;
    protected PImage wizardDownPic;
    protected PImage gremlinGreen;
    protected PImage gremlinBlue;
    protected PImage brick1;
    protected PImage brick2;
    protected PImage brick3;
    protected PImage brick4;
    protected PImage fireballPic;
    protected PImage snowballPic;
    protected PImage slimePic;
    
    /**
    * Constructor for App. Initialise arrays and set important values.
     */
    public App() {
        this.configPath = "config.json";
        this.stonewallList = new ArrayList<Stonewall>();
        this.brickwallList = new ArrayList<Brickwall>();
        this.fireballList = new ArrayList<Fireball>();
        this.gremlinList = new ArrayList<Gremlin>();
        this.slimeList = new ArrayList<Slime>();
        this.freeTileList = new ArrayList<FreeTile>();
        this.exitList = new ArrayList<Exit>();
        this.snowballList = new ArrayList<Snowball>();
        this.wizardList = new ArrayList<Wizard>();
        this.portalList = new ArrayList<Portal>();
        this.snowballCoordinates = new HashMap<Integer, Integer>();
        this.snowballCoordX = new ArrayList<Integer>();
        
        this.currentKey = 0;
        this.levelIndex = 0;
        this.start = true;
        this.snowballRespawn = false;
        this.startTimer = 0;
        this.keyPressed = false;
        this.keyReleased = true;
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies
     * and map elements.
     * Arrays are cleared first as setup is ran multiple times.
     * Load configs to get important values. Use configs to locate and initialise the map, then 
     * add objects to the relevant array.
    */
    public void setup() {

        frameRate(FPS);

        // Load config
        JSONObject json = loadJSONObject(this.configPath);
        JSONArray levels = json.getJSONArray("levels");
        this.numberOfLevels = levels.size();
        JSONObject currentLevel = levels.getJSONObject(this.levelIndex);
        String fileName = currentLevel.getString("layout");
        this.wizardCooldown = currentLevel.getDouble("wizard_cooldown");
        this.enemyCooldown = currentLevel.getDouble("enemy_cooldown");
        if (this.start) {
            this.remainingLives = json.getInt("lives");
            this.start = false;
        }
        
        // Load images during setup
        this.wizardLeftPic = this.loadImage("src/main/resources/gremlins/wizard0.png");
        this.wizardRightPic = this.loadImage("src/main/resources/gremlins/wizard1.png");
        this.wizardUpPic = this.loadImage("src/main/resources/gremlins/wizard2.png");
        this.wizardDownPic = this.loadImage("src/main/resources/gremlins/wizard3.png");
        this.gremlinGreen = this.loadImage("src/main/resources/gremlins/gremlin.png");
        this.gremlinBlue = this.loadImage("src/main/resources/gremlins/frozenGremlin.png");
        this.brick1 = this.loadImage("src/main/resources/gremlins/brickwall_destroyed0.png");
        this.brick2 = this.loadImage("src/main/resources/gremlins/brickwall_destroyed1.png");
        this.brick3 = this.loadImage("src/main/resources/gremlins/brickwall_destroyed2.png");
        this.brick4 = this.loadImage("src/main/resources/gremlins/brickwall_destroyed3.png");
        this.fireballPic = this.loadImage("src/main/resources/gremlins/fireball.png");
        this.snowballPic = this.loadImage("src/main/resources/gremlins/snowball.png");
        this.slimePic = this.loadImage("src/main/resources/gremlins/slime.png");
        // These only needs to be referred to once.
        PImage stonePic = this.loadImage("src/main/resources/gremlins/stonewall.png");
        PImage brickPic = this.loadImage("src/main/resources/gremlins/brickwall.png");
        PImage exitPic = this.loadImage("src/main/resources/gremlins/dungeon.png");
        PImage portalPic = this.loadImage("src/main/resources/gremlins/portal.png");
        
        // Clear relevant lists.
        this.stonewallList.clear();
        this.brickwallList.clear();
        this.fireballList.clear();
        this.gremlinList.clear();
        this.slimeList.clear();
        this.freeTileList.clear();
        this.exitList.clear();
        this.snowballList.clear();
        this.wizardList.clear();
        this.portalList.clear();
        this.snowballCoordinates.clear();
        this.snowballCoordX.clear();

        // Load map and initialise objects. A free tile is added where applicable.
        try {
            Scanner mapInput = new Scanner(new File(fileName));
            int positionY = 0;
            while (mapInput.hasNextLine()) {
                String line = mapInput.nextLine();
                for (int positionX = 0; positionX < line.length(); positionX++) {
                    if (line.charAt(positionX) == 'X') {
                        this.stonewallList.add(new Stonewall(positionX*20, positionY*20, stonePic));
                    } else if (line.charAt(positionX) == 'B') {
                        this.brickwallList.add(new Brickwall(positionX*20, positionY*20, brickPic));
                    } else if (line.charAt(positionX) == 'G') {
                        this.gremlinList.add(new Gremlin(positionX*20, positionY*20, gremlinGreen, this.enemyCooldown));
                        this.freeTileList.add(new FreeTile(positionX*20, positionY*20));
                    } else if (line.charAt(positionX) == 'W') {
                        this.wizard = new Wizard(positionX*20, positionY*20, wizardRightPic, this.remainingLives, this.wizardCooldown);
                        this.wizardList.add(this.wizard);
                        this.freeTileList.add(new FreeTile(positionX*20, positionY*20));
                    } else if (line.charAt(positionX) == 'E') {
                        this.exitList.add(new Exit(positionX*20, positionY*20, exitPic));
                    } else if (line.charAt(positionX) == 'S') {
                        this.freeTileList.add(new FreeTile(positionX*20, positionY*20));
                        this.snowballCoordinates.put(positionX*20, positionY*20);
                        this.snowballCoordX.add(positionX*20);
                    } else if (line.charAt(positionX) == 'P') {
                        this.portalList.add(new Portal(positionX*20, positionY*20, portalPic));
                    } else {
                        this.freeTileList.add(new FreeTile(positionX*20, positionY*20));
                    }
                }
                positionY++;
            }
            // Number of snowballs
            this.powerUpNum = this.snowballCoordX.size();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed() {
        this.keyReleased = false;
        // Receive movement inputs only when wizad is in a whole tile.
        if (this.wizard.inWholeTile()) {
            if (this.keyCode == 37 || this.keyCode == 38 || this.keyCode == 39 || this.keyCode == 40) {
                this.wizard.setMovement(true);
                this.wizard.setCurrentKey(this.keyCode);
            }
        }
        // Receive spacebar inputs
        if (this.keyCode == 32 && this.wizard.isLoaded()) {
            this.wizard.shoot(this.fireballList, this.fireballPic);
        }
    }
    
    /*
     * Receive key released signal from the keyboard.
    */
    public void keyReleased() {
        this.keyReleased = true;
        // If current directional key is released, stop movement.
        if (this.keyCode == this.wizard.getCurrentKey()) {
            this.wizard.setMovement(false);
        }
    }

    /**
     * Handles logic and remove relevant objects.
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(191, 153, 114);

        // If on one of the earlier levels, load next level. If on last level, display YOU WIN.
        if (this.wizard.getNextLevel() && this.levelIndex < this.numberOfLevels - 1) {
            this.levelIndex++;
            this.setup();
        } else if (this.wizard.getNextLevel() && this.levelIndex == this.numberOfLevels - 1) {
            // Only when the current key is released and another key is pressed will the level restart.
            if (this.keyReleased) {
                this.restart = true;
            }
            this.fill(255, 255, 255);
            this.textSize(50);
            this.text("YOU WIN", 255, 340);
            this.textSize(30);
            this.text("Press any key to continue", 180, 390);
            // Only restart if key has been released and a key is pressed.
            if (this.restart && !this.keyReleased) {
                this.restart = false;
                this.levelIndex = 0;
                this.start = true;
                this.startTimer = 0;
                this.setup();
            }
            return;
        }

        // If all lives are lost, display YOU LOSE.
        if (this.wizard.getLives() == 0) {
            if (this.keyReleased) {
                this.restart = true;
            }
            this.fill(255, 255, 255);
            this.textSize(50);
            this.text("YOU LOSE", 255, 340);
            this.textSize(30);
            this.text("Press any key to continue", 180, 390);
            if (this.restart && !this.keyReleased) {
                this.restart = false;
                this.levelIndex = 0;
                this.start = true;
                this.startTimer = 0;
                this.setup();
            }
            return;
        }

        // Initialise snowballs only after 5 seconds since game start.
        if (this.startTimer < FPS * 5) {
            this.startTimer++;
        } else if (this.startTimer == FPS * 5) {
            this.startTimer++;
            for (int x: this.snowballCoordX) {
                int y = this.snowballCoordinates.get(x);
                this.snowballList.add(new Snowball(x, y, this.snowballPic));
            }
        } else {
            /*
            * After 5 seconds, if a snowball is used up then wait a random intereval between 2 
            * and 10 seconds before respawning a snowball. This process is repeated until the
            * original number of snowball is reached.
            */
            if (this.snowballList.size() < this.powerUpNum && !this.snowballRespawn) {
                FreeTile spawnTile = this.freeTileList.get(randomGenerator.nextInt(freeTileList.size()));
                this.snowballRespawnX = spawnTile.getX();
                this.snowballRespawnY = spawnTile.getY();
                this.snowballRespawnTimer = 0;
                this.snowballRespawnTimertarget = FPS * (2 + randomGenerator.nextInt(8));
                this.snowballRespawn = true;
            } else if (this.snowballList.size() < this.powerUpNum && this.snowballRespawn) {
                this.snowballRespawnTimer++;
                if (this.snowballRespawnTimer == snowballRespawnTimertarget) {
                    snowballList.add(new Snowball(this.snowballRespawnX, this.snowballRespawnY, this.snowballPic));
                    this.snowballRespawn = false;
                }
            }
        }

        // Check how many lives wizard have left before running setup (if needed)
        this.remainingLives = this.wizard.getLives();
        // Wizard number of lives UI
        for (int i = 0; i < this.remainingLives; i++) {
            Wizard newWizard = new Wizard(80 + i*25, 678, this.wizardRightPic, 1, 1);
            newWizard.draw(this);
        }
        if (this.wizard.isReset()) {
            this.setup();
        }

        // Load UI
        this.textSize(20);
        this.fill(255, 255, 255);
        this.text("Lives", 25, 695);
        this.text("Level: ", 250, 695);
        this.levelNumber = this.levelIndex + 1;
        this.text(this.levelNumber + "/" + this.numberOfLevels, 310, 695);
        this.textSize(18);
        this.text("Fireball", 500, 685);
        this.text("Snowball", 500, 705);
        this.rect(580, 675, 100, 10, 4);
        this.rect(580, 695, 100, 10, 4);
        // Fireball timer bar
        if (!this.wizard.isLoaded()) {
            float timer = this.wizard.getFireballTimer();
            float cooldown = this.wizard.getFireballCooldown();
            float ratio = timer/cooldown;
            this.fill(0, 0, 0);
            this.rect(580, 675, ratio * 100, 10, 4);
        }
        // Snowball timer bar
        if (this.wizard.isPoweredUp()) {
            float timer = this.wizard.getSnowballTimer();
            float cooldown = this.wizard.getSnowballCooldown();
            float ratio = timer/cooldown;
            this.fill(30, 144, 255);
            this.rect(580, 695, ratio * 100, 10, 4);
        }

        // Handles all logic for relevant objects before drawing.
        for (Brickwall brickwall: brickwallList) {
            brickwall.tick();
        }
        for (Gremlin gremlin: gremlinList) {
            gremlin.tick(this.stonewallList, this.brickwallList, this.slimeList, this.slimePic,
                            this.freeTileList, this.wizard.getX(), this.wizard.getY());
        }
        for (Slime slime: slimeList) {
            slime.tick();
        }
        for (Fireball fireball: fireballList) {
            fireball.tick();
        }
        this.wizard.tick(this.stonewallList, this.brickwallList);
        
        // Check for overlap for any interactive object.
        for (Slime slime: slimeList) {
            slime.checkOverlapAll(this.stonewallList, this.brickwallList, this.fireballList);
        }
        for (Fireball fireball: fireballList) {
            fireball.checkOverlapAll(this.stonewallList, this.brickwallList,
                                     this.gremlinList, this.slimeList);
        }
        for (Gremlin gremlin: gremlinList) {
            gremlin.checkOverlapAll(this.fireballList);
        }
        for (Brickwall brickwall: brickwallList) {
            brickwall.checkOverlapAll(this.fireballList);
        }
        for (Snowball snowball: snowballList) {
            snowball.checkOverlapAll(this.wizardList);
        }
        this.wizard.checkOverlapAll(this.gremlinList, this.slimeList, this.exitList,
                                    this.snowballList, this.portalList);

        // Remove objects as necessary per logic. Free tiles are added where relevant.
        for(int i = 0; i < brickwallList.size(); i++) {
            Brickwall brickwall = brickwallList.get(i);
            if(brickwall.isDestroyed()){
                this.freeTileList.add(new FreeTile(brickwall.getX(), brickwall.getY()));
                brickwallList.remove(brickwall);
            }
        }
        for(int i = 0; fireballList.size() > i; i++){
            if(fireballList.get(i).isDestroyed()){
                fireballList.remove(fireballList.get(i));
            }
        }
        for(int i = 0; slimeList.size() > i; i++){
            if(slimeList.get(i).isDestroyed()){
                slimeList.remove(slimeList.get(i));
            }
        }
        for(int i = 0; snowballList.size() > i; i++){
            Snowball snowball = snowballList.get(i);
            if(snowball.isDestroyed()){
                this.freeTileList.add(new FreeTile(snowball.getX(), snowball.getY()));
                snowballList.remove(snowball);
            }
        }

        // Draw objects onto screen.
        for (Stonewall stonewall: stonewallList) {
            stonewall.draw(this);
        }
        for (Exit exit: exitList) {
            exit.draw(this);
        }
        for (Portal portal: portalList) {
            portal.draw(this);
        }
        for (Fireball fireball: fireballList) {
            fireball.draw(this);
        }
        for (Slime slime: slimeList) {
            slime.draw(this);
        }
        for (Snowball snowball: snowballList) {
            snowball.draw(this);
        }
        for (Brickwall brickwall: brickwallList) {
            // Change sprite during destruction process.
            if (brickwall.getSpriteTimer() > 0) {
                int spriteTimer = brickwall.getSpriteTimer();
                if (spriteTimer <= 4) {
                    brickwall.setSprite(this.brick1);
                } else if (spriteTimer <= 8) {
                    brickwall.setSprite(this.brick2);
                } else if (spriteTimer <= 12) {
                    brickwall.setSprite(this.brick3);
                } else if (spriteTimer <= 16) {
                    brickwall.setSprite(this.brick4);
                }
            }
            brickwall.draw(this);
        }
        for (Gremlin gremlin: gremlinList) {
            // Change sprite when frozen.
            if (gremlin.isFrozen()) {
                gremlin.setSprite(this.gremlinBlue);
            } else if (!gremlin.getSprite().equals(this.gremlinGreen)) {
                gremlin.setSprite(this.gremlinGreen);
            }
            gremlin.draw(this);
        }
        // Change wizard's sprite based on direction.
        String direction = this.wizard.getDirection();
        if (direction.equals("left")) {
            this.wizard.setSprite(this.wizardLeftPic);
        } else if (direction.equals("right")) {
            this.wizard.setSprite(this.wizardRightPic);
        } else if (direction.equals("up")) {
            this.wizard.setSprite(this.wizardUpPic);
        } else {
            this.wizard.setSprite(this.wizardDownPic);
        }
        this.wizard.draw(this);
    }

    /**
    * Main
     */
    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
