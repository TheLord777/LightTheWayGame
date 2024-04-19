package lightTheWay.gameLogic;

import java.util.ArrayList;
import java.util.List;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;
import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.Cell;
import lightTheWay.components.environment.Level;
import processing.core.PVector;

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

                if (gc instanceof Level) {
                    gc.setIlluminated(true);
                    float ls = lc.getLightSize() / 2;
                    List<Cell> cs = ((Level) gc).getCellsWithinGrid(lc.getX() - ls, lc.getY() - ls, lc.getX() + ls, lc.getY() + ls);

                    for (Cell c : cs) {
                        c.setIlluminated(true);
                    }

                } else if (checkCollision(gc, lc)) {
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
