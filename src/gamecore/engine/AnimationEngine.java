package gamecore.engine;


import gamecore.components.CollisionShape;
import gamecore.components.Explosion;
import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is responsible for managing the animation of all the game components, updating and then rendering each .
 */
final public class AnimationEngine {

    private static final AnimationEngine instance = new AnimationEngine();

    public static AnimationEngine getInstance() {
        return instance;
    }

    private final ArrayList<GameComponent> components; // Collection of all active components to render.

    private AnimationEngine() {
        components = new ArrayList<>();
    }

    public void addComponent(GameComponent p) {
        components.add(p);
    }

    public void addComponentOnTopOfComponent(GameComponent toAdd, GameComponent toAddOnTopOf) {
        components.add(components.indexOf(toAddOnTopOf) + 1, toAdd);
    }

    public void addComponentsOnTopOfComponent(Collection<GameComponent> toAdd, GameComponent toAddOnTopOf) {
        for (GameComponent gameComponent : toAdd) {
            addComponentOnTopOfComponent(gameComponent, toAddOnTopOf);
        }
    }


    public void removeComponent(Explosion p) {
        p.reset(); // reset an explosion component before removing it from the game for reuse.
        components.remove(p);
    }

    public void removeComponent(GameComponent p) {
        components.remove(p);
    }


    /**
     * This method will render all the game components in the game, i.e. update each object and then draw.
     */
    public void step() {
        // Render Components
        for (GameComponent gc : components) gc.step();
        // Render Lighting
        lighting();
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
        lightMask.blendMode(PApplet.SCREEN);
        lightMask.noStroke();
        // Draw lights for each component based on light size
        for (GameComponent gc : components) {
            float xCenter, yCenter;
            if (gc.getShape() == CollisionShape.RECTANGLE) {
                xCenter = gc.getP().x + gc.getWidth() / 2;
                yCenter = gc.getP().y + gc.getHeight() / 2;
            } else {
                xCenter = gc.getP().x;
                yCenter = gc.getP().y;
            }
            for (int i=0; i <= gc.getLightSize(); i += 20) {
                lightMask.fill(255 / (gc.getLightSize() / 20));
                lightMask.ellipse(xCenter, yCenter, gc.getLightSize() - i, gc.getLightSize() - i);
            }
        }
        lightMask.endDraw();
        // Apply the mask to the rendered display
        screen.mask(lightMask);
        // Draw the masked image
        app.image(screen, 0, 0);
    }

    public ArrayList<GameComponent> getComponents() {
        return components;
    }

    public void removeAllComponents() {
        components.clear();
    }


    public void removeComponents(List<GameComponent> toRemove) {
        components.removeAll(toRemove);
    }

    public void addComponents(List<GameComponent> toAdd) {
        components.addAll(toAdd);
    }

    public void removeAllComponents(List<GameComponent> toRetain) {
        components.clear();
        components.addAll(toRetain);
    }

    public void removeAllComponents(GameComponent toRetain) {
        components.clear();
        components.add(toRetain);
    }
    private void processFrame(){

    }
}
