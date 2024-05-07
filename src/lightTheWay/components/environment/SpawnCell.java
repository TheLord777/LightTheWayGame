package lightTheWay.components.environment;

import processing.core.PVector;

public class SpawnCell extends Cell {
    private static final long serialVersionUID = 1L;

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
