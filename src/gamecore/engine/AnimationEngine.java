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
        for (GameComponent gc : components) gc.step();
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
