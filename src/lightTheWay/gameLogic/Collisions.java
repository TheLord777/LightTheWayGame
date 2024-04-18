package lightTheWay.gameLogic;

import java.util.ArrayList;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;
import lightTheWay.components.LightComponent;

public class Collisions extends CollisionEngine {

    private static final CollisionEngine instance = new Collisions();

    public static CollisionEngine getInstance() {
        return instance;
    }

    private Collisions() {
        super();
    }

    @Override
    public void collisions() {
        lightCollisions();
    }

    public void lightCollisions() {
        // Get all light sources
        ArrayList<LightComponent> lights = new ArrayList<>();
        ArrayList<GameComponent> nonLights = new ArrayList<>();
        for (GameComponent gc : animationEngine.getComponents()) {
            if (gc instanceof LightComponent) {
                lights.add((LightComponent) gc);
            } else {
                nonLights.add(gc);
            }
        }
        for (GameComponent gc : nonLights) {
            for (LightComponent lc : lights) {
                if (checkCollision(gc, lc)) {
                    gc.setIlluminated(true);
                } else {
                    gc.setIlluminated(false);
                }
            }
        }
    }

    @Override
    protected boolean checkOutOfBounds(GameComponent p) {
        return false;
    }
}
