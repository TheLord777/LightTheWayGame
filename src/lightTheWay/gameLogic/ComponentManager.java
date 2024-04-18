package lightTheWay.gameLogic;

import gamecore.Config;
import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PVector;
import lightTheWay.components.LightComponent;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import lightTheWay.components.environment.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static processing.core.PApplet.*;

public abstract class ComponentManager extends GameEngine {
    Level level;

    PlayableCharacter hero;

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
        Config.setGravity(new PVector(0, .15f));
    }

    @Override
    public void setupGame() {
        animationEngine.removeAllComponents();
        getMapFormation("map.ser");
//        level = new Level(app.width, appHeight, 50);
        animationEngine.addComponent(level);


        hero = new PlayableCharacter(new PVector(150,app.height -250), level.getTileSize(), level);

        // Example of adding a component to the game
        animationEngine.addComponent(hero);
        animationEngine.addComponent(hero.createLight(250));
//        // Initialize tileSize
//        animationEngine.removeAllComponents();
//
//        int tileSize = min(app.width, app.height) / 50; // Adjust as needed

        // Create a new instance of MapFormation


    }


    public void getMapFormation(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            level = (Level) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("MapFormation class not found");
            c.printStackTrace();
            return;
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
        PImage screen = app.get(0, 0, app.width, app.height);
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

}
