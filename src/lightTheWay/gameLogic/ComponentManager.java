package lightTheWay.gameLogic;

import gamecore.Config;
import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PVector;
import lightTheWay.components.HUDComponent;
import lightTheWay.components.LightComponent;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import lightTheWay.components.environment.Cell;
import lightTheWay.components.environment.Droplet;
import lightTheWay.components.environment.Level;
import lightTheWay.components.environment.Stalactite;
import lightTheWay.components.environment.WaterCell;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public abstract class ComponentManager extends GameEngine {
    Level level;

    ArrayList<Level> levels = new ArrayList<>();
    int levelIndex = -1;

    PlayableCharacter hero;

    HUDComponent hud;

    int runStartTime = 0;

    ArrayList<Stalactite> stalactites = new ArrayList<>();

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
        Config.setGravity(new PVector(0, .15f));
    }

    @Override
    public void setupGame() {}

    public void setupGame(ArrayList<String> filenames) {
        animationEngine.removeAllComponents();
        for (String filename : filenames) {
            Level newLevel = getMapFormation(filename);
            if (newLevel != null) {
                levels.add(newLevel);
            }
        }

        runStartTime = Instance.getApp().millis();

        nextLevel();

    }

    public void checkPlayerForDanger() {
        // If the player enters a water cell, the torch extinguishes entirely
        Cell current = level.getCellFromGCPosition(hero);
        if (current instanceof WaterCell) {
            // hero.getLight().decrementLight();
            // hero.getLight().decrementLight();
            // hero.getLight().decrementLight();
            hero.damage(0.99f);
        }
        // If the player is hit by a droplet, takes 20% of the default time away
        // ADD A CHECK FOR COOLDOWN!!!!!
        for (Stalactite stalactite : stalactites) {
            boolean hitDetected = false;
            ArrayList<Droplet> droplets = stalactite.getDroplets();
            for (Droplet droplet : droplets) {
                if (CollisionEngine.checkCollision(droplet, hero)) {
                    hero.damage(0.20f);
                    hitDetected = true;
                    break;
                }
            }
            if (hitDetected) break;
        }
    }

    public Level getMapFormation(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Level newLevel = (Level) in.readObject();
            // level = (Level) in.readObject();
            in.close();
            fileIn.close();
            return newLevel;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("MapFormation class not found");
            c.printStackTrace();
            return null;
        }
    }

    /**
     * This method handles the lighting for the game, by placing a shadow over the display and masking for light sources
     * Needs to run AFTER the latest round of rendering, as it takes a snapshot of the rendered elements
     */
    public void lighting() {
        // Apply Shadows and Lights
        PApplet app = Instance.getApp();
        // Get snapshot of current rendered display
        PImage screen = app.get((int) app.screenX(0, 0), (int) app.screenY(0, 0), app.width, app.height);
        // Cover screen in shadow
        app.pushStyle();
        app.noStroke();
        app.fill(0);
        app.rect(0, 0, app.width, app.height);
        app.popStyle();
        // Begin carving out light from shadows using mask
        PGraphics lightMask = app.createGraphics(app.width, app.height, PApplet.JAVA2D);
        lightMask.beginDraw();
        lightMask.background(0, 0);
        // lightMask.blendMode(PApplet.SCREEN);
        lightMask.blendMode(PApplet.ADD);
        lightMask.noStroke();
        // Draw lights for each component based on light size
        for (GameComponent gc : animationEngine.getComponents()) {
            if (gc instanceof LightComponent) {
                LightComponent lc = (LightComponent) gc;
                float xCenter, yCenter;
                if (lc.getShape() == CollisionShape.RECTANGLE) {
                    xCenter = lc.getP().x + lc.getWidth() / 2;
                    yCenter = lc.getP().y + lc.getHeight() / 2;
                } else {
                    xCenter = lc.getP().x;
                    yCenter = lc.getP().y;
                }
                float baseSize = lc.getLightSize() / 2;
                float addVal = lc.getLightDisplayIncrement();
                float sizeIncrement = (lc.getLightDisplaySize() - baseSize) / 6;

                for (int i = 0; i < 6; i++) {
                    lightMask.fill(addVal);
                    lightMask.ellipse(xCenter, yCenter,  baseSize + i * sizeIncrement, baseSize + i * sizeIncrement);
                }
            }
        }
        lightMask.endDraw();
        // Apply the mask to the rendered display
        screen.mask(lightMask);
        // Draw the masked image
        app.image(screen, 0, 0);
    }

    public void pushCameraPosition() {
        PApplet app = Instance.getApp();

        app.pushMatrix();
        app.translate(app.width/2, app.height/2);
        app.translate(-hero.getX(), -hero.getY());
    }

    public void popCameraPosition() {
        PApplet app = Instance.getApp();

        app.popMatrix();
    }
    
    /**
     * Moves the game to the next level is available
     * @return whether or not there is another level
     */
    public boolean nextLevel() {
        levelIndex++;
        if (levelIndex < levels.size()) {
            level = levels.get(levelIndex);
            level.updateMap(app.width, app.height);
            animationEngine.removeAllComponents();
            animationEngine.addComponent(level);
            PVector spawnPosition = level.getPlayerSpawn().getP().copy();
            spawnPosition.add(level.getTileSize() / 2, level.getTileSize() / 2);
            System.out.println(spawnPosition);
            if (hero == null) {
                hero = new PlayableCharacter(spawnPosition, level.getTileSize(), level);
                hero.createLight(level.getWidth() / 6.9f);
            } else {
                hero.movePosition(spawnPosition);
                hero.setEnvironment(level);
            }
            animationEngine.addComponent(hero);
            animationEngine.addComponent(hero.getLight());
            if (hud == null) {
                hud = new HUDComponent(hero);
            }
            ArrayList<LightComponent> lights = level.getLightComponents();
            for (LightComponent light : lights) {
                animationEngine.addComponent(light);
            }
            stalactites = level.getStalactites();
            return true;
        }
        return false;
    }

    public void respawnCharacter() {
        hero = null;
        hud = null;
        levelIndex = -1;
        nextLevel();
    }

}
