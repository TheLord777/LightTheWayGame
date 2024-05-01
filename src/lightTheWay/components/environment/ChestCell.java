package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import lightTheWay.components.characters.Character;
import processing.core.PVector;


public class ChestCell extends Cell{
    private boolean isOpen;
    public ChestCell(PVector p, float width, float height) {
        super(p, width, height);
        this.isOpen = false;
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
        if (ge instanceof Character) {
            Character character = (Character) ge;
            // Check if the character is within range of the chest
            boolean isWithinRange = isWithinRangeOfCharacter(character);

            // Open or close the chest based on character's proximity
            if (isWithinRange) {
                openChest();
            } else {
                closeChest();
            }
        }
        return false;
    }

    private boolean isWithinRangeOfCharacter(Character character) {
        // Get the position of the character
        PVector characterPosition = character.getP();

        // Calculate the distance between the character and the chest cell
        float distance = PVector.dist(characterPosition, p);

        // If the distance is less than or equal to the sum of half the character's width and half the chest cell's width,
        // the character is considered within range
        return distance <= (character.getWidth() / 2 + getWidth() / 2);
    }
}
