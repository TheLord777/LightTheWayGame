package lightTheWay.gameLogic;

import gamecore.engine.GameEngine;
import lightTheWay.Instance;

public abstract class ComponentManager extends GameEngine {

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
    }

    @Override
    public void setupGame() {

    }


}
