package lightTheWay.gameLogic;

import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PConstants;

public class LightTheWay extends ComponentManager {

    float endScreenAlpha = 0;
    int nextGenNumber = 2;
    boolean win = false;
    String runTimeString = "";

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
        if (win) {
            PApplet app = Instance.getApp();
            if (endScreenAlpha < 255) {
                endScreenAlpha += 255 / (5 * app.frameRate);
                endScreenAlpha = Math.min(255, endScreenAlpha);
            }
            app.fill(255, 255, 255, endScreenAlpha);
            app.rect(0, 0, app.width, app.height);
            app.fill(0, 0, 0, endScreenAlpha);

            app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
            float fontSize = Math.min(app.width / 20, 50);
            app.textSize(fontSize);
            app.text("Light has returned to the world once more", app.width / 2, app.height / 2 - fontSize * 2f);
            app.text("The work of your predecessors ends with you", app.width / 2, app.height / 2 - fontSize);
            app.text("The journey took " + runTimeString + "ms", app.width / 2, app.height / 2 + fontSize);
            app.text("The " + ordinal(nextGenNumber) + " Generation has lit the path.", app.width / 2, app.height / 2 + fontSize * 2f);
        } else if (hero.outOfLight() && endScreenAlpha < 255) {
            PApplet app = Instance.getApp();
            endScreenAlpha += 255 / (5 * app.frameRate);
            app.fill(255, 255, 255, endScreenAlpha);
            app.rect(0, 0, app.width, app.height);
            app.fill(0, 0, 0, endScreenAlpha);

            app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
            float fontSize = Math.min(app.width / 20, 50);
            app.textSize(fontSize);
            app.text("Your time is up", app.width / 2, app.height / 2 - fontSize * 1.5f);
            app.text("You may return to the light", app.width / 2, app.height / 2);
            app.text("The " + ordinal(nextGenNumber) + " Generation will carry the torch in your place...", app.width / 2, app.height / 2 + fontSize * 1.5f);
        }
        app.fill(255);
        app.textSize(16);
        app.textAlign(PConstants.CENTER, PConstants.TOP);
        app.text("Frame Rate: " + app.frameRate, 100, 10);
    }


    @Override
    protected void gameLoop() {
        // lighting();
        checkPlayerForDanger();
        if (win) {
            return;
        }
        if (level.reachedGoal(hero)) {
            if (!nextLevel()) {
                nextGenNumber--;
                int runEndTime = Instance.getApp().millis();
                int runTime = runEndTime - runStartTime;
                int hours = runTime / (3600000);
                runTime = runTime % (3600000);
                int minutes = runTime / (60000);
                runTime = runTime % (60000);
                int seconds = runTime / (1000);
                runTime = runTime % (1000);
                if (hours > 0) {
                    runTimeString = hours + ":" + minutes + ":" + seconds + "." +  String.format("%03d", runTime);
                } else if (minutes > 0) {
                    runTimeString = minutes + ":" + seconds + "." +  String.format("%03d", runTime);
                } else {
                    runTimeString = seconds + "." + String.format("%03d", runTime);
                }
                win = true;
            }
        }
        if (hero.outOfLight()) {
            if (endScreenAlpha >= 255) {
                endScreenAlpha = 0;
                respawnCharacter();
                nextGenNumber++;
            }
        }
    }

    // https://stackoverflow.com/questions/6810336/is-there-a-way-in-java-to-convert-an-integer-to-its-ordinal-name
    public static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
        case 11:
        case 12:
        case 13:
            return i + "th";
        default:
            return i + suffixes[i % 10];
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
