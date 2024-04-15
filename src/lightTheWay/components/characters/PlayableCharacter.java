package lightTheWay.components.characters;

import lightTheWay.components.LightComponent;
import processing.core.PVector;

public class PlayableCharacter extends Character {

    private LightComponent light;


    public PlayableCharacter(PVector p, float width) {
        super(p, width);
    }


    @Override
    protected void draw() {
        super.draw();
    }

    public LightComponent createLight(float l) {
        light = new LightComponent(p, l, 5);
        return light;
    }


}
