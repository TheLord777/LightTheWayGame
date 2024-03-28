package lightTheWay.gameLogic;

import gamecore.engine.GameEngine;
import processing.core.PApplet;

public abstract class ComponentManager extends GameEngine {

    protected ComponentManager(PApplet app) {
        super(app, Collisions.getInstance());
    }

    @Override
    public void setupGame() {

    }


}
