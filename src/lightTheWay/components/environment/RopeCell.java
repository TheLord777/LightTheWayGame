package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class RopeCell extends Cell{
    private boolean isSpawnCell;

    public RopeCell(PVector p, float width, float height, boolean isSpawnCell) {
        super(p, width, height);
        this.isSpawnCell = isSpawnCell;
    }

    @Override
    public void draw() {
        app.pushStyle();

        app.stroke(0); // Set stroke color to black
        app.strokeWeight(4); // Set stroke weight as needed

        // Calculate the x-coordinate of the center of the cell
        float centerX = getX() + getWidth() / 2.0f;

        // Calculate the y-coordinate of the top of the cell
        float topY = getY() - getHeight() / 2.0f;

        // Draw a vertical line from the top to the bottom of the cell
        app.line(centerX, topY, centerX, getY() + getHeight());

        app.popStyle();
    }

    // Example usage of isSpawnCell in RopeCell
    public void setToSpawn() {
        if (isSpawnCell()) {
            // Do something specific for spawn cells
            app.fill(0,0,0);
            System.out.println("set to spawn");
        } else {
            // Do something else
        }
    }

    public boolean isSpawnCell() {
        return isSpawnCell;
    }

    public void setSpawnCell(boolean spawnCell) {
        isSpawnCell = spawnCell;
    }
    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
