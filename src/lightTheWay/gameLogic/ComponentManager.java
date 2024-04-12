package lightTheWay.gameLogic;

import gamecore.Config;
import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.ExampleComponent;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PVector;

public abstract class ComponentManager extends GameEngine {

    PlayableCharacter hero;

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
        Config.setGravity(new PVector(0, .15f));
    }

    @Override
    public void setupGame() {
        animationEngine.removeAllComponents();
        hero = new PlayableCharacter(new PVector(50,400), 50);

        // Example of adding a component to the game
        animationEngine.addComponent(hero);
        animationEngine.addComponent(new ExampleComponent());
    }


}
