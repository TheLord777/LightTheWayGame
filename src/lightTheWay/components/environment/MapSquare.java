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
        float noiseVal = app.noise(p.x*0.05f, p.y* 0.05f); // Adjust frequency as needed
        int rockColor = getColorFromNoise(noiseVal);

        // Draw logic for MapSquare goes here
        if (type == 1) {
            return;
        } else {
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
        }
        app.rect(p.x, p.y, width, height);
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
}
