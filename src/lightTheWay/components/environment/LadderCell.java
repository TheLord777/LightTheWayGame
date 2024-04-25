package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class LadderCell extends Cell{
    public LadderCell(PVector p, float width, float height) {
        super(p, width, height);
    }

    @Override
    public void draw() {

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
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
