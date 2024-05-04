package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import lightTheWay.components.characters.Character;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PConstants;
import processing.core.PVector;


public class ChestCell extends Cell{
    private float promptHeight = p.y - height * 1.5f;
    private boolean promptDirection = false; // true -> upwards, false -> downwards
    private boolean isOpen;
    private ItemType content = ItemType.NO_ITEM;
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
    public ItemType openChest() {
        isOpen = true;
        return content;
        // itemGridUI.draw();
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

    public void drawItemGridUI() {
        itemGridUI.draw();
    }

    public void drawPrompt() {
        app.fill(200, 200, 200);
        app.rect(p.x, promptHeight, width, height);
        app.fill(100, 100, 100);
        app.textSize(width);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text("F", p.x + width / 2, promptHeight + height / 2);
        updatePromptPosition();
    }

    public PVector getPromptPosition() {
        return new PVector(p.x, promptHeight);
    }

    public void updatePromptPosition() {
        if (promptDirection) {
            promptHeight -= height * 0.02f;
            if (promptHeight <= p.y - height * 1.5f) {
                promptDirection = false;
                promptHeight = p.y - height * 1.5f;
            }
        } else {
            promptHeight += height * 0.02f;
            if (promptHeight >= p.y - height * 1.1f) {
                promptDirection = true;
                promptHeight = p.y - height * 1.1f;
            }
        }
    }

    public void setContent(ItemType type) {
        content = type;
    }

    public ItemType getContent() {
        return content;
    }
}
