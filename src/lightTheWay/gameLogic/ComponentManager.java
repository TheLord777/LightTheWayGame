package lightTheWay.gameLogic;

import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.ExampleComponent;

public abstract class ComponentManager extends GameEngine {

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
    }

    @Override
    public void setupGame() {
        // Example of adding a component to the game
        animationEngine.addComponent(new ExampleComponent());
    }


}
