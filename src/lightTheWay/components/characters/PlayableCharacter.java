package lightTheWay.components.characters;

import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.Level;
import processing.core.PVector;

public class PlayableCharacter extends Character {

    private LightComponent light;


    public PlayableCharacter(PVector p, float width, Level l) {
        super(p, width, l);
    }


    @Override
    protected void draw() {
        super.draw();
    }

    public LightComponent createLight(float l) {
        // light = new LightComponent(p, l, 5);
        light = new LightComponent(p, l, 0);
        return light;
    }


}
