package lightTheWay.gameLogic;

import processing.core.PConstants;

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
        // lighting();
        if (level.reachedGoal(hero)) {
            nextLevel();
        }
        if (hero.outOfLight()) {
            respawnCharacter();
        }
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
        hero.setDown(true);
    }

    public void eKeyDown() {
        hero.useItem(hud.useSelectedSlot());
    }

    public void fKeyDown() {
        hero.interact();
    }

    public void numKeyDown(char s) {
        int number = Character.getNumericValue(s);
        hud.setSelectedSlot(number - 1);
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
        hero.setDown(false);
    }


}
