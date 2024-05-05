package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

public class WallCell extends Cell {

    public WallCell(PVector p, float width, float height) {
        super(p, width, height);
    }

    @Override
    public void draw() {

        float noiseVal = app.noise(p.x * 0.05f, p.y * 0.05f); // Adjust frequency as needed
        int rockColor = getColorFromNoise(noiseVal);

        if (noiseVal < 0.45) { // Adjust the threshold for moss application (lower values for more moss)
            // Calculate moss color with fading effect
            float mossNoise = app.noise(p.x * 0.05f, p.y * 0.05f);
            int mossBrightness = (int) PApplet.map(mossNoise, 0, 1, 100, 200); // Adjust brightness range as needed
            int mossSaturation = (int) PApplet.map(mossNoise, 0, 1, 150, 255); // Adjust saturation range as needed
            int mossHue = (int) PApplet.map(mossNoise, 0, 1, 80, 120); // Adjust hue range as needed
            app.fill(mossHue, mossSaturation, mossBrightness); // Moss color with fading effect
        } else {
            app.fill(rockColor);
        }
        app.rect(p.x, p.y, width, height);


    }

    private int getColorFromNoise(float noiseVal) {
        // Map noise value to a grayscale spectrum
        float brightness = PApplet.map(noiseVal, 0, 1, 30, 220);  // Adjust brightness for variation
        return app.color(brightness);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }
}
