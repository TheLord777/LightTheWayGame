package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import lightTheWay.components.characters.Character;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PVector;


public class ChestCell extends Cell{
    private boolean isOpen;
    private ItemGridUI itemGridUI;
    public ChestCell(PVector p, float width, float height) {
        super(p, width, height);
        this.isOpen = false;
        this.itemGridUI = new ItemGridUI(new PVector(p.x, p.y - height), width, height / 2, width / 3);
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
        itemGridUI.draw();
    }

    public void closeChest() {
        isOpen = false;
        // Add logic to handle what happens when the chest is closed
    }

    public boolean isOpen() {
        return isOpen;
    }

    public ItemGridUI getItemGridUI() {
        return itemGridUI;
    }

}
