package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;


public class ChestCell extends Cell{
    private boolean isOpen;
    public ChestCell(PVector p, float width, float height, boolean isOpen) {
        super(p, width, height, CellType.CHEST.getValue());
        this.isOpen = isOpen;
    }

    @Override
    public void draw() {
        app.pushStyle();
        app.fill(200);
        app.strokeWeight(2);
        app.stroke(generateChestColour());

        float chestWidth = getWidth();
        float chestHeight = getHeight();

        // Draw a simple chest-like pattern
        app.rect(getX(), getY(), chestWidth, chestHeight);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.3f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.3f);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.5f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.5f);
        app.line(getX() + chestWidth * 0.3f, getY() + chestHeight * 0.7f, getX() + chestWidth * 0.7f, getY() + chestHeight * 0.7f);

        app.popStyle();
    }

    private int generateChestColour() {
        float noiseValue = app.noise(getX() * 0.1f, getY() * 0.1f);
        return app.color(255 * noiseValue, 150 * noiseValue, 100 * noiseValue);
    }
    public void openChest() {
        isOpen = true;
        // Add logic to handle what happens when the chest is opened
    }

    public void closeChest() {
        isOpen = false;
        // Add logic to handle what happens when the chest is closed
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
