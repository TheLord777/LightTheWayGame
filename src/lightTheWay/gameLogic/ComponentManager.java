package lightTheWay.gameLogic;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.CampComponent;
import lightTheWay.components.ExampleComponent;
import lightTheWay.components.LightComponent;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class ComponentManager extends GameEngine {

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
    }

    @Override
    public void setupGame() {
        // Example of adding a component to the game
        animationEngine.addComponent(new ExampleComponent());
        animationEngine.addComponent(new LightComponent(400, 400, 400, 20));
        animationEngine.addComponent(new LightComponent(800, 800, 200, 20));
        animationEngine.addComponent(new LightComponent(1200, 400, 200));
        animationEngine.addComponent(new CampComponent(1200, 800, 400, 400, 400));
    }

    @Override
    public void play() {
        super.play(); // use standard play() method
        // Movable shape on mouse to test
        PApplet app = Instance.getApp();
        app.fill(0, 0, 255);
        app.circle(app.mouseX, app.mouseY, 50);
        // Lighting
        if (showEndScreen && gamePaused) {
            // Handle custom lighting effect
            lighting();
            System.out.println(app.frameRate);
        } else if (!gamePaused) {
            // Handle custom lighting effect
            lighting();
            System.out.println(app.frameRate);
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
                    lightMask.ellipse(xCenter, yCenter, baseSize + i * sizeIncrement, baseSize + i * sizeIncrement);
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
