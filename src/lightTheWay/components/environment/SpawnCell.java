package lightTheWay.components.environment;

import processing.core.PVector;

public class SpawnCell extends Cell {
    public SpawnCell(PVector p, float width, float height) {
        super(p, width, height); // Set isSpawnCell to true for SpawnCell
        setSpawnCell(true);
    }

    @Override
    public void draw() {
        // Implement drawing logic for SpawnCell if needed
    }
}
