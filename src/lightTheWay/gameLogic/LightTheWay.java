package lightTheWay.gameLogic;

import java.io.File;
import java.util.ArrayList;

import lightTheWay.Instance;
import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.CampCell;
import lightTheWay.components.environment.ItemType;
import lightTheWay.components.environment.TorchCell;
import processing.core.PConstants;
import processing.core.PVector;

public class LightTheWay extends ComponentManager {

    float endScreenAlpha = 0;
    int nextGenNumber = 2;
    GameState state = GameState.START_MODE;
    LightComponent startScreenLight = new LightComponent(new PVector(app.width / 2, 3 * app.height / 4), app.width, 0);
    boolean win = false;
    String runTimeString = "";
    // ItemGridUI itemGridUI;

    public LightTheWay() {
        super();
        setUpStart();
    }

    @Override
    public void play() {
        switch (state) {
            case START_MODE:
                startMode();
                break;
            case TRANSITION_TO_PLAY:
                transitionToPlay();
                break;
            case PLAY_MODE:
                playMode();
                break;
        }
    }

    private void setUpStart() {
        win = false;
        endScreenAlpha = 0;
        nextGenNumber = 2;
        state = GameState.START_MODE;
        animationEngine.removeAllComponents();
        startScreenLight.reignite();
        startScreenLight.setDecrementRate(0);
        animationEngine.addComponent(startScreenLight);
    }

    private void startMode() {
        app.fill(55, 44, 44);
        app.rect(0, 0, app.width, app.height);
        startScreenLight.setIlluminated(true);
        app.fill(139, 69, 19);
        app.rect(app.width / 2 - startScreenLight.getDefaultSize() / 60, 3 * app.height / 4, startScreenLight.getDefaultSize() / 30, app.height / 4);

        app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
        app.fill(200, 200, 200);
        float fontSize = app.width / 10;
        app.textSize(fontSize);
        app.text("Light The Way", app.width / 2, app.height / 2);
        fontSize = app.width / 30;
        app.textSize(fontSize);
        app.text("Extinguish the light to begin", app.width / 2, app.height / 2 + app.width / 20);

        lighting();
        animationEngine.step();
    }

    private void transitionToPlay() {
        if (state == GameState.START_MODE) {
            state = GameState.TRANSITION_TO_PLAY;
            startScreenLight.setBurnTime(2);
        } else if (state == GameState.TRANSITION_TO_PLAY && !startScreenLight.isBurning()) {
            startGame();
        } else if (state == GameState.TRANSITION_TO_PLAY) {
            startMode();
        }
    }

    private void startGame() {
        File levelDirectory = new File("levels");
        ArrayList<String> levelfiles = new ArrayList<>();
        for (File file : levelDirectory.listFiles()) {
            levelfiles.add("levels/" + file.getName());
            System.out.println(file.getName());
        }
        setupGame(levelfiles);
        state = GameState.PLAY_MODE;
    }

    private void playMode() {
        if (showEndScreen && gamePaused || !gamePaused) {
            // pushCameraPosition();
        }
        super.play();

        if (showEndScreen && gamePaused || !gamePaused) {
            // popCameraPosition();
            this.hud.setAltitude(calculateAltitude());
            this.hud.step();
            if (progressSaved > 0){
                float opacity = app.bezierPoint(0, 255, 255, 0, progressSaved / 100f);
                progressSaved--;
                app.noFill();
                app.stroke(255, opacity /4);
                app.strokeWeight(3);
                app.rect(0,0, appWidth, appHeight);
                app.strokeWeight(1);
                app.noStroke();

                app.fill(255, opacity);
                app.textSize(16);
                app.textAlign(PConstants.LEFT, PConstants.TOP);
                app.text("Checkpoint Saved", 5, 5);
            }
        }

        if (win) {
            drawWinScreen();
        } else if (hero.outOfLight() || hero.killed() && endScreenAlpha < 255) {
            drawDeathScreen();
        }
    }

    private int calculateAltitude() {
        int climbedHeight = 0;
        for (int i = 0; i < Math.min(levelIndex, levels.size() - 1); i++) {
            climbedHeight += levels.get(i).getLevelHeight();
        }
        int currentHeight = (int) (level.getPlayerSpawn().getP().y  / level.getCellHeight()) - (int) (hero.getP().y / level.getCellHeight());
        return climbedHeight + currentHeight;
    }

    private void drawWinScreen() {
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
    }

    private void drawDeathScreen() {
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

    protected void drawPausedScreen() {
        app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
        float fontSize = Math.min(app.width / 20, 50);
        app.textSize(fontSize);

        app.background(0);
        app.fill(255);
        app.textAlign(PConstants.CENTER);
        app.text("Game Paused: \nPress 'Enter' to start", (float) app.width / 2, (float) app.height / 2);
    }

    @Override
    protected void gameLoop() {
        lighting();
        dropDroplets();
        if (!hero.killed()) checkPlayerForDanger();
        captureCheckpoint();
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
        if (hero.outOfLight() || hero.killed()) {
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
        if (state == GameState.START_MODE) {
            if (Collisions.checkCollisionCirclePoint(startScreenLight.getP(), startScreenLight.getLightDisplaySize() / 10, new PVector(app.mouseX, app.mouseY))) {
                transitionToPlay();
            }
        }

    }

    @Override
    public void spaceKey() {
        transitionToPlay();
    }

    @Override
    public void rightKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        hero.setRight(true);
    }

    @Override
    public void leftKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        hero.setLeft(true);
    }

    @Override
    public void upKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        hero.setUp(true);
    }

    @Override
    public void downKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        hero.setDown(true);
    }

    public void eKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        ItemType type = hud.getSelectedType();
        boolean success = hud.useSelectedSlot();
        if (success) {
            if (type == ItemType.TORCH) {
                TorchCell torch = (TorchCell) level.getCellFromGCPosition(hero);
                animationEngine.addComponent(torch.getLightComponent());
            } else if (type == ItemType.BONFIRE) {
                CampCell camp = (CampCell) level.getCellFromGCPosition(hero);
                animationEngine.addComponent(camp.getLightComponent());
            }
        }
    }

    public void fKeyDown() {
        if (state != GameState.PLAY_MODE) return;
        hero.interact();
    }

    public void qKeyDown() {
        switch (state) {
            case PLAY_MODE:
                if (win || gamePaused) {
                    setUpStart();
                }
                break;
            default:
                return;
        }
    }

    public void numKeyDown(char s) {
        if (state != GameState.PLAY_MODE) return;
        int number = Character.getNumericValue(s);
        hud.setSelectedSlot(number - 1);
    }

    @Override
    public void rightKeyUp() {
        if (state != GameState.PLAY_MODE) return;
        hero.setRight(false);
    }

    @Override
    public void leftKeyUp() {
        if (state != GameState.PLAY_MODE) return;
        hero.setLeft(false);
    }

    @Override
    public void upKeyUp() {
        if (state != GameState.PLAY_MODE) return;
        hero.setUp(false);
    }

    @Override
    public void downKeyUp() {
        if (state != GameState.PLAY_MODE) return;
        hero.setDown(false);
    }

    @Override
    public void enterKey() {
        if (state != GameState.PLAY_MODE) return;
        super.enterKey();
    }
}
