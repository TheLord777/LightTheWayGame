package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class RopeCell extends Cell{
    private boolean isWall;
    private WallCell wallcell;
    public RopeCell(PVector p, float width, float height, boolean isWall) {
        super(p, width, height);
        this.isWall = isWall;
    }

    @Override
    public void draw() {
        if (isWall) {
            // If the cell is a wall, draw the wall cell
            drawWallCell();
            // Draw the rope on top of the wall cell
            drawRope();
        } else {
            // If the cell is not a wall, just draw an empty cell
            drawEmptyCell();
        }
    }

    private void drawWallCell() {
        WallCell wallCell = new WallCell(p, getWidth(), getHeight());
        wallCell.draw();
    }

    private void drawEmptyCell() {
        // Draw an empty cell (e.g., a transparent rectangle)
        app.noFill(); // Set fill color to transparent
        app.rect(getX(), getY(), getWidth(), getHeight());
    }

    private void drawRope() {
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
