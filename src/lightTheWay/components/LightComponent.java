package lightTheWay.components;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PVector;

public class LightComponent extends GameComponent {

    protected float lightSize; // 0 for no light, else creates a light with the given size
    protected float lightIncrement; // value at which the light brightens/dims from one ring to the next
    protected int flickerState = 0; // 0 - random jump, 1 - rising, 2 - falling
    protected float flickerSize = 0; // starts at 0
    protected float flickerIncrement = 0; // starts at 0
    protected int frames = 0;

    public LightComponent(float x, float y, float l) {
        super(new PVector(x, y), 0, 0);
        this.setShape(CollisionShape.POINT);
        this.setLightSize(l);
        this.setLightIncrement(20);
    }

    @Override
    protected void draw() {
        app.fill(255, 165, 0);
        drawFire(this.getLightDisplaySize() / 10);

        app.fill(255, 0, 0);
        drawFire(2 * this.getLightDisplaySize() / 30);
    }

    protected void drawFire(float circleRadius) {
        app.circle(p.x, p.y, circleRadius);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x, p.y - circleRadius);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x - circleRadius / 2, p.y - circleRadius / 2);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x + circleRadius / 2, p.y - 2 * circleRadius / 3);
    }

    @Override
    protected void update() {
        flicker();
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    public float getLightSize() {
        return lightSize;
    }

    public void setLightSize(float l) {
        lightSize = l;
    }

    public float getLightIncrement() {
        return lightIncrement;
    }

    public void setLightIncrement(float li) {
        lightIncrement = li;
    }

    /**
     * Flickers the light to gradually dim, brighten, or jump around in brightness
     */
    public void flicker() {
        // Flicker 20 times per second
        if (frames > Instance.getApp().frameRate / 20) {
            this.frames = 0;
            double random = Math.random();
            // 10% chance when not in random jump state to flicker, or if exceeds 10% either way of standard increment then guaranteed
            if ((flickerState != 0 && random < 0.1) || (Math.abs(flickerIncrement) >= lightIncrement * 0.1)) {
                flickerState = 0;
                flickerIncrement = 0;
            } else { // Else perform continuous change
                // If just flickered, select a random flicker direction, 70% rising, 30% falling
                if (flickerState == 0) {
                    flickerState = (Math.random() <= 0.7) ? 1 : 2;
                }
                // Perform change to flickerIncrement, between 0%-2% of base lightIncrement
                if (flickerState == 1) { // If rising
                    flickerIncrement += lightIncrement * Math.random() / 50;
                    flickerIncrement = (float) Math.min(lightIncrement * 0.1, flickerIncrement);
                } else { // If falling
                    flickerIncrement -= Math.random() / 50 * lightIncrement;
                    flickerIncrement = (float) Math.max(- lightIncrement * 0.1, flickerIncrement);
                }
            }
        } else {
            frames++;
        }
        // Adjust flickerSize based on flickerIncrement
        flickerSize = lightSize * (flickerIncrement / lightIncrement);
    }

    /**
     * Get the display size of the light, such that it accounts for flickering
     * @return Display size of the light
     */
    public float getLightDisplaySize() {
        return lightSize + flickerSize;
    }

    /**
     * Get the displayed brightness increment of the light, such that it accounts for flickering
     * @return Increment value of the light
     */
    public float getLightDisplayIncrement() {
        return lightIncrement + flickerIncrement;
    }
}
