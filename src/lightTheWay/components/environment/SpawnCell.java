package lightTheWay.components.environment;

import processing.core.PVector;

public class SpawnCell extends Cell {


    public SpawnCell(PVector p, float width, float height) {
        super(p, width, height);
        setSpawnCell(true);
    }

    @Override
    public void draw() {
        app.fill(0,255,255);
        app.rect(p.x, p.y, width, height);
    }
}
