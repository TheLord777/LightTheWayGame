package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class RopeComponent extends GameComponent {

    public RopeComponent(PVector p, float width, float height) {
        super(p, width, height);
    }
    @Override
    protected void draw() {
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
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
