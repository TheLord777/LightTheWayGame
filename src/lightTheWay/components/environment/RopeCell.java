package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class RopeCell extends Cell{
    public RopeCell(PVector p, float width, float height) {
        super(p, width, height, CellType.ROPE.getValue());
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

    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
