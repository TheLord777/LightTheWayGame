package lightTheWay.components.environment;

import processing.core.PApplet;
import processing.core.PVector;
import gamecore.components.GameComponent;

public class LadderComponent extends GameComponent {

    public LadderComponent(PVector p, float width, float height) {
        super(p, width, height);
    }

    @Override
    protected void draw() {
        app.pushStyle(); // Save the current style settings

        app.stroke(0); // Black color for ladder strokes
        app.strokeWeight(3); // Adjust the stroke weight as needed

        // Calculate the dimensions for the ladder within the cell
        float cellCenterX = getX() + getWidth() / 2.0f; // Center of the cell horizontally
        float cellTopY = getY(); // Top of the cell
        float cellBottomY = getY() + getHeight(); // Bottom of the cell

        // Draw the left railing of the ladder
        app.line(cellCenterX - 0.25f * getWidth(), cellTopY, cellCenterX - 0.25f * getWidth(), cellBottomY);

        // Draw the right railing of the ladder
        app.line(cellCenterX + 0.25f * getWidth(), cellTopY, cellCenterX + 0.25f * getWidth(), cellBottomY);

        // Draw the horizontal step line in the middle of the cell
        float stepY = cellTopY + getHeight() / 2.0f;
        app.line(getX(), stepY, getX() + getWidth(), stepY);

        app.popStyle(); // Restore the previous style settings
    }

    @Override
    protected void update() {
        // Implement update logic if needed
    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Implement intersection logic if needed
        return false;
    }
}
