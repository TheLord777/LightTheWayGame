package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class ChestComponent extends GameComponent {
    private float noiseOffsetX;
    private float noiseOffsetY;
    public ChestComponent(PVector p, float width, float height) {
        super(p, width, height);
        noiseOffsetX = p.x; // Use the x-coordinate as the noise offset
        noiseOffsetY = p.y; // Use the y-coordinate as the noise offset
    }
    @Override
    protected void draw() {
        app.pushStyle();
        app.fill(200);
        app.strokeWeight(2);
        app.stroke(generateColor());

        float chestWidth = getWidth();
        float chestHeight = getHeight();

        // Draw a simple chest-like pattern
        app.rect(getX(), getY(), chestWidth, chestHeight);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.3f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.3f);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.5f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.5f);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.7f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.7f);

        app.popStyle();
    }

    @Override
    protected void update() {

    }

    private int generateColor() {
        float noiseValue = app.noise(noiseOffsetX, noiseOffsetY); // Generate Perlin noise value
        // Map the noise value to a color range (you can adjust the range for different colors)
        return app.color(255 * noiseValue, 150 * noiseValue, 100 * noiseValue);
    }
    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
