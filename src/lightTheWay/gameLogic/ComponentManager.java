package lightTheWay.gameLogic;

import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.environment.MapFormation;
import processing.core.PVector;

import static processing.core.PApplet.*;

public abstract class ComponentManager extends GameEngine {

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
    }

    @Override
    public void setupGame() {
        // Initialize tileSize

        int tileSize = min(app.width, app.height) / 50; // Adjust as needed

        // Create a new instance of MapFormation
        MapFormation mapFormation = new MapFormation(new PVector(0, 0), app.width, app.height, tileSize);
        animationEngine.addComponent(mapFormation);

    }


}
