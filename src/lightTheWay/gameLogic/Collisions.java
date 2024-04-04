package lightTheWay.gameLogic;

import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;

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

    }

    @Override
    protected boolean checkOutOfBounds(GameComponent p) {
        return false;
    }
}
