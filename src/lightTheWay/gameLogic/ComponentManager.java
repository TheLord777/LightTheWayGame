package lightTheWay.gameLogic;

import gamecore.Config;
import gamecore.components.CollisionShape;
import gamecore.components.EndFrame;
import gamecore.components.Frame;
import gamecore.components.GameComponent;

import gamecore.engine.CollisionEngine;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.characters.AICharacter;
import lightTheWay.components.characters.PlayableCharacter;
import lightTheWay.components.environment.*;
import processing.core.PVector;
import lightTheWay.components.HUDComponent;
import lightTheWay.components.LightComponent;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public abstract class ComponentManager extends GameEngine {
    Level level;

    ArrayList<Level> levels = new ArrayList<>();
    int levelIndex = -1;
    int damageShake;

    PlayableCharacter hero;

    HUDComponent hud;

    int runStartTime = 0;

    ArrayList<Stalactite> stalactites = new ArrayList<>();

    Map<Stalactite, Integer> lastDropletTimes = new HashMap<>();

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());

    }

    @Override
    public void setupGame() {
    }

    public void setupGame(ArrayList<String> filenames) {
        runStartTime = 0;
        levelIndex = -1;
        damageShake = 0;
        levels = new ArrayList<>();
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

        Iterator<GameComponent> iterator = animationEngine.getComponents().iterator();
        while (iterator.hasNext()) {
            GameComponent gc = iterator.next();
            if (gc instanceof Droplet) {
                Droplet droplet = (Droplet) gc;
                if (CollisionEngine.checkCollision(droplet, hero)) {
                    hero.damage(0.20f);
                    damageShake =50;
                    iterator.remove(); // Remove the droplet using iterator
                }
            }

            if (gc instanceof AICharacter){
                if (Collisions.checkCollision(hero, gc)){
                    hero.damage(0.2f);
                    damageShake = 50;

                }
            }
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
                    if (damageShake == 0) lightMask.fill(addVal);
                    else {
                        lightMask.fill(app.random(25,30));
                    }

                    lightMask.ellipse(xCenter, yCenter, baseSize + i * sizeIncrement, baseSize + i * sizeIncrement);
                }

                if (damageShake != 0){
                    damageShake--;
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
        app.translate(app.width / 2, app.height / 2);
        app.translate(-hero.getX(), -hero.getY());
    }

    public void popCameraPosition() {
        PApplet app = Instance.getApp();

        app.popMatrix();
    }

    /**
     * Moves the game to the next level is available
     *
     * @return whether or not there is another level
     */
    public boolean nextLevel() {
        levelIndex++;

        if (levelIndex < levels.size()) {
            if (hud == null) {
                hud = new HUDComponent();
            }

            level = levels.get(levelIndex);
            level.updateMap(app.width, app.height - hud.getHeight());
            Config.setGravity(level.getHeight() / 10000);
            level.addDecor();
            animationEngine.removeAllComponents();
            animationEngine.addComponent(level);
            PVector spawnPosition = level.getPlayerSpawn().getP().copy();
            spawnPosition.add(level.getCellWidth() / 2, level.getCellHeight() / 2);

            if (hero == null) {
                hero = new PlayableCharacter(spawnPosition, level.getCellHeight(), level);
                hero.createLight(level.getWidth() / 6.9f);
            } else {
                hero.setEnvironment(level);
            }
            hud.setHero(hero);
            animationEngine.addComponent(hero);
            animationEngine.addComponent(hero.getLight());

            for (Cell spawnPoint : level.getSpawnPoints()) {
                animationEngine.addComponent(new AICharacter(spawnPoint.getP().copy().add(level.getCellWidth() / 2, level.getCellHeight() / 2), level.getCellHeight(), level, hero));
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

    public void dropDroplets() {
        int currentTime = Instance.getApp().millis();
        int dropletInterval = 2000; // Droplet drop interval: once every 2 seconds

        ArrayList<Droplet> dropletsToRemove = new ArrayList<>();
        for (Stalactite stalactite : stalactites) {
            int lastDropletTime = lastDropletTimes.getOrDefault(stalactite, 0);
            if (currentTime - lastDropletTime >= dropletInterval) {
                float dropletX = stalactite.getP().x + stalactite.getWidth() / 2;
                float dropletY = stalactite.getP().y;
                Droplet droplet = new Droplet(new PVector(dropletX, dropletY), 10);

                animationEngine.addComponent(droplet);
                lastDropletTimes.put(stalactite, currentTime);
            }
        }
        // Check for collision with wall cells and mark droplets for removal
        for (GameComponent gc : animationEngine.getComponents()) {
            if (gc instanceof Droplet droplet) {
                if (level.getCellFromGCPosition(droplet) instanceof WallCell) {
                    dropletsToRemove.add(droplet);
                }
            }
        }
        // Remove marked droplets
        for (Droplet droplet : dropletsToRemove) {
            animationEngine.removeComponent(droplet);
        }
    }


    public void respawnCharacter() {
        hero = null;
        hud = null;
        levelIndex = -1;
        nextLevel();
    }

}
