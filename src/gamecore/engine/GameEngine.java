package gamecore.engine;

import processing.core.PApplet;

/**
 * This class is the base class for all game engines. It provides the basic game loop and input handling.
 */
public abstract class GameEngine {

    protected final PApplet app; // The app canvas/ game area to render game components.
    protected final AnimationEngine animationEngine; // The animation engine to manage the game components.
    protected final int appWidth, appHeight;
    protected boolean gamePaused, showEndScreen; // Whether the game is paused or the end screen is showing.
    protected CollisionEngine ce; // The collision engine to manage game collisions.

    protected GameEngine(PApplet app, CollisionEngine ce) {
        this.app = app;
        this.animationEngine = AnimationEngine.getInstance();
        this.ce = ce;
        appWidth = app.displayWidth;
        appHeight = app.displayHeight;
        gamePaused = true;
        showEndScreen = false;
    }

    /**
     * Method to setup all required game components and data structured to process the game
     */
    public abstract void setupGame();

    /**
     * Game loop.
     * If the game is paused or over the relevant screen is drawn.
     */
    public void play() {
        if (showEndScreen && gamePaused) {
            animationEngine.step();
            ce.collisions();
            gameLoop();
            drawEndScreen();
        } else if (gamePaused) {
            drawPausedScreen();
        } else {
            animationEngine.step();
            ce.collisions();

            gameLoop();
        }
    }

    /**
     * Main game loop.
     */
    protected abstract void gameLoop();

    // INPUT HANDLERS

    public abstract void mousePressed();

    public abstract void spaceKey();

    public abstract void rightKeyDown();

    public abstract void leftKeyDown();

    public abstract void upKeyDown();

    public abstract void downKeyDown();

    public abstract void rightKeyUp();

    public abstract void leftKeyUp();

    public abstract void upKeyUp();

    public abstract void downKeyUp();

    //    HELPER METHODS


    /**
     * Generic Paused screen.
     */
    protected void drawPausedScreen() {
        app.background(50);
        app.fill(255);
        app.textAlign(app.CENTER);
        app.text("Game Paused: \nPress 'Enter' to start", (float) app.width / 2, (float) app.height / 2);
    }

    /**
     * Generic End screen.
     */
    protected void drawEndScreen() {
        textOnScreen("Game Over: \nPress 'Enter' to restart");
    }


    protected void textOnScreen(String s) {
        // put rectangle behind as background
        app.fill(0, 50);
        app.rect(0, (float) app.height / 2 - 50, app.width, 100);

        app.fill(255);
        app.textAlign(app.CENTER, app.CENTER);
        app.text(s, (float) app.width / 2, (float) app.height / 2);
    }




    /**
     * Handle Enter key input.
     * Toggles game pause and restarts game if the end screen is showing.
     */
    public void enterKey() {
        gamePaused = !gamePaused;

        if (showEndScreen) {
            showEndScreen = false;
            setupGame();
        }
    }

    /**
     * Method to be called when the game is over.
     */
    protected void gameOver() {
        showEndScreen = true;
        gamePaused = true;
    }
}
