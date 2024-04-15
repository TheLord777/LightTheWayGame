package lightTheWay.gameLogic;

import lightTheWay.components.environment.MapFormation;
import processing.core.PVector;

import static processing.core.PApplet.min;
import static processing.core.PApplet.print;

public class LightTheWay extends ComponentManager {

    public LightTheWay() {
        super();
    }


    @Override
    protected void gameLoop() {
        app.fill(255);
        app.text("fps" + app.frameRate, 50, 10);
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

    }

    @Override
    public void leftKeyDown() {

    }

    @Override
    public void upKeyDown() {

    }

    @Override
    public void downKeyDown() {

    }

    @Override
    public void rightKeyUp() {

    }

    @Override
    public void leftKeyUp() {

    }

    @Override
    public void upKeyUp() {

    }

    @Override
    public void downKeyUp() {

    }
}
