package lightTheWay.gameLogic;

import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.*;

import static processing.core.PApplet.min;
import static processing.core.PApplet.print;

public class LightTheWay extends ComponentManager {

    public LightTheWay() {
        super();
    }

    @Override
    public void play() {
        if (showEndScreen && gamePaused || !gamePaused) {
            pushCameraPosition();
        }
        super.play();
        if (showEndScreen && gamePaused || !gamePaused) {
            popCameraPosition();
            this.hud.step();
        }
        app.fill(255);
        app.textAlign(PConstants.CENTER, PConstants.TOP);
        app.text("Frame Rate: " + app.frameRate, 100, 10);
    }


    @Override
    protected void gameLoop() {
        lighting();
    }

    @Override
    public void mousePressed() {
    }

    @Override
    public void spaceKey() {
    setupGame();
    }

    @Override
    public void rightKeyDown() {
        hero.setRight(true);
    }

    @Override
    public void leftKeyDown() {
        hero.setLeft(true);
    }

    @Override
    public void upKeyDown() {
        hero.setUp(true);
    }

    @Override
    public void downKeyDown() {

    }

    public void fKeyDown() {
        System.out.println("hallo");
        hero.interact();
    }

    @Override
    public void rightKeyUp() {
        hero.setRight(false);
    }

    @Override
    public void leftKeyUp() {
        hero.setLeft(false);

    }

    @Override
    public void upKeyUp() {
        hero.setUp(false
        );
    }

    @Override
    public void downKeyUp() {

    }


}
