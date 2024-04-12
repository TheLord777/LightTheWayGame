package lightTheWay.gameLogic;

import processing.core.PApplet;

public class LightTheWay extends ComponentManager {

    public LightTheWay() {
        super();
    }


    @Override
    protected void gameLoop() {

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
