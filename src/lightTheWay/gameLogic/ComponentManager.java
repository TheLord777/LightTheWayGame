package lightTheWay.gameLogic;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
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
        animationEngine.addComponent(new LightComponent(400, 400, 500));
    }

    @Override
    public void play() {
        super.play(); // use standard play() method
        // Lighting
        if (showEndScreen && gamePaused) {
            // Handle custom lighting effect
            lighting();
        } else if (!gamePaused) {
            // Handle custom lighting effect
            lighting();
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
                int sumVal = 0;
                float addVal = lc.getLightDisplayIncrement();
                for (int i=0; i <= lc.getLightDisplaySize(); i += (lc.getLightDisplaySize() / 25)) {
                    if (sumVal > 240) break;
                    lightMask.fill(addVal);
                    sumVal+= addVal;
                    lightMask.ellipse(xCenter, yCenter, lc.getLightDisplaySize() - i, lc.getLightDisplaySize() - i);
                }
                // for (int i=0; i <= lc.getLightSize(); i += 20) {
                //     lightMask.fill(255 / (lc.getLightSize() / 20));
                //     lightMask.ellipse(xCenter, yCenter, lc.getLightSize() - i, lc.getLightSize() - i);
                // }
            }
        }
        lightMask.endDraw();
        // Apply the mask to the rendered display
        screen.mask(lightMask);
        // Draw the masked image
        app.image(screen, 0, 0);
    }

}
