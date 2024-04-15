package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

import java.io.Serializable;

public class MapSquare extends GameComponent  {

    private int type;


    public MapSquare(PVector p, float width, float height, int type) {
        super(p, width, height);
        this.type = type;
    }

    @Override
    protected void draw() {
        if (type == 0){
         drawRock();
        }else if (type == 1) {
            return; // the background
        }else if (type == 2){
            drawLadder();
        } else if (type == 3){
            drawRope();
        } else if (type == 4){
            drawWater();
        }

    }


    @Override
    protected void update() {
        // Update logic for MapSquare goes here
    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Intersection logic for MapSquare goes here
        return false; // Placeholder, implement actual logic as needed
    }

    // Additional methods specific to MapSquare can be added here

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int getColorFromNoise(float noiseVal) {
        // Map noise value to a grayscale spectrum
        float brightness = app.map(noiseVal, 0, 1, 30, 220);  // Adjust brightness for variation
        return app.color(brightness);
    }


    private void drawLadder() {
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


    private void drawRope(){
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


    private void drawWater(){
        app.pushStyle();
        app.fill(0, 0, 255);
        app.rect(p.x, p.y, width, height);
        app.popStyle();
    }


    private void drawRock(){
        float noiseVal = app.noise(p.x*0.05f, p.y* 0.05f); // Adjust frequency as needed
        int rockColor = getColorFromNoise(noiseVal);

        if (noiseVal < 0.45) { // Adjust the threshold for moss application (lower values for more moss)
            // Calculate moss color with fading effect
            float mossNoise = app.noise(p.x * 0.05f, p.y * 0.05f);
            int mossBrightness = (int) app.map(mossNoise, 0, 1, 100, 200); // Adjust brightness range as needed
            int mossSaturation = (int) app.map(mossNoise, 0, 1, 150, 255); // Adjust saturation range as needed
            int mossHue = (int) app.map(mossNoise, 0, 1, 80, 120); // Adjust hue range as needed
            app.fill(mossHue, mossSaturation, mossBrightness); // Moss color with fading effect
        } else {
            app.fill(rockColor);
        }
        app.rect(p.x, p.y, width, height);


    }

}
