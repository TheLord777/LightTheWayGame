package lightTheWay.components.characters;

import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.CampCell;
import lightTheWay.components.environment.Cell;
import lightTheWay.components.environment.Level;
import processing.core.PVector;

public class PlayableCharacter extends Character {

    private LightComponent light;


    public PlayableCharacter(PVector p, float width, Level l) {
        super(p, width, l);
    }


    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void update() {
        super.update();

        // Handle state with special tiles
        Cell current = getEnvironment().getCellFromGCPosition(this);
        if (current instanceof CampCell) {
            light.reignite();
        }
    }

    public LightComponent createLight(float l) {
        // light = new LightComponent(p, l, 5);
        light = new LightComponent(p, l, 0);
        light.setBurnTime(60);
        return light;
    }

    public LightComponent getLight() {
        return light;
    }


}
