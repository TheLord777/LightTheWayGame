import lightTheWay.gameLogic.LightTheWay;
import processing.core.PApplet;


/**
 * Main game class
 */
public class Game extends PApplet {

    // Game Engine to run
    LightTheWay ge;

    public void settings() {
        fullScreen();

    }

    public void setup() {
        background(0);
        noStroke();

        // Use processing built in crosshair cursor.
        cursor(CROSS);

        // create and setup new game engine.
        ge = new LightTheWay(this);
        ge.setupGame();
    }


    public void draw() {
        background(0);

        // Run game engine specific game loop
        ge.play();
    }


//    GAME INPUT

    /**
     * Handle key presses.
     */
    public void keyPressed() {
        // space to fire
        key = Character.toLowerCase(key);
        if (key == 'a') ge.leftKeyDown();
        if (key == 'd') ge.rightKeyDown();
        if (key == 'w') ge.upKeyDown();
        if (key == 's') ge.downKeyDown();
        if (key == ' ') ge.spaceKey();
        if (key == '\n') ge.enterKey();

    }

    /**
     * Handle key presses.
     */
    public void keyReleased() {
        // space to fire
        key = Character.toLowerCase(key);
        if (key == 'a') ge.leftKeyUp();
        if (key == 'd') ge.rightKeyUp();
        if (key == 'w') ge.upKeyUp();
        if (key == 's') ge.downKeyUp();
    }


    /**
     * Handle mouse presses.
     */
    public void mousePressed() {
        ge.mousePressed();
    }


    public static void main(String... args) {
        PApplet.main("Game");
    }
}