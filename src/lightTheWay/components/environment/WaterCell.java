package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class WaterCell extends Cell{

    public WaterCell(PVector p, float width, float height) {
        super(p, width, height );

    }

    @Override
    public void draw(){
        app.pushStyle();
        app.fill(0, 0, 255);
        app.rect(p.x, p.y, width, height);
        app.popStyle();
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
