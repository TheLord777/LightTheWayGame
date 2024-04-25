package lightTheWay.components;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PVector;

public class LightComponent extends GameComponent {

    protected float defaultSize; // the default size for this light, if reignited then is set tot his
    protected float lightSize; // 0 for no light, else creates a light with the given size
    protected float lightIncrement; // value at which the light brightens/dims from one ring to the next
    private int flickerState = 0; // 0 - random jump, 1 - rising, 2 - falling
    private float flickerRatio = 0; // starts at 0
    private int frames = 0;
    private float decrementRate = 0; // the amount that the size of a light should decrease per second

    public LightComponent(PVector p , float l, float d) {
        super(p, l, l);
        this.setShape(CollisionShape.CIRCLE);
        this.setDefaultSize(l);
        this.setLightSize(l);
        this.setLightIncrement(40);
        this.setDecrementRate(d);
        this.setIlluminated(true);
    }

    public LightComponent(float x, float y, float l) {
        super(new PVector(x, y), l, l);
        this.setShape(CollisionShape.CIRCLE);
        this.setDefaultSize(l);
        this.setLightSize(l);
        this.setLightIncrement(40);
        this.setIlluminated(true);
    }

    public LightComponent(float x, float y, float l, float d) {
        super(new PVector(x, y), l, l);
        this.setShape(CollisionShape.CIRCLE);
        this.setDefaultSize(l);
        this.setLightSize(l);
        this.setLightIncrement(40);
        this.setDecrementRate(d);
        this.setIlluminated(true);
    }

    @Override
    public void draw() {
//        app.fill(255, 165, 0);
//        drawFire(this.getLightDisplaySize() / 10);
//
//        app.fill(255, 0, 0);
//        drawFire(2 * this.getLightDisplaySize() / 30);
    }

    protected void drawFire(float circleRadius) {
        app.circle(p.x, p.y, circleRadius);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x, p.y - circleRadius);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x - circleRadius / 2, p.y - circleRadius / 2);
        app.triangle(p.x - circleRadius / 2, p.y, p.x + circleRadius / 2, p.y, p.x + circleRadius / 2, p.y - 2 * circleRadius / 3);
    }

    @Override
    protected void update() {
//        flicker();

        decrementLight();
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    @Override
    public float getWidth() {
        return lightSize;
    }

    @Override
    public float getHeight() {
        return lightSize;
    }

    public float getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(float d) {
        defaultSize = d;
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

    public void setDecrementRate(float d) {
        decrementRate = d;
    }

    /**
     * Flickers the light to gradually dim, brighten, or jump around in brightness
     */
    public void flicker() {
        // Flicker 20 times per second
        if (frames > Instance.getApp().frameRate / 60) {
            this.frames = 0;
            double random = Math.random();
            // 10% chance when not in random jump state to flicker, or if exceeds 10% either way of standard increment then guaranteed
            if ((flickerState != 0 && random < 0.1) || (Math.abs(flickerRatio) >= 0.1)) {
                flickerState = 0;
                flickerRatio = 0;
            } else { // Else perform continuous change
                // If just flickered, select a random flicker direction, 70% rising, 30% falling
                if (flickerState == 0) {
                    flickerState = (Math.random() <= 0.7) ? 1 : 2;
                }
                // Perform change to flickerIncrement, between 0%-2% of base lightIncrement
                if (flickerState == 1) { // If rising
                    flickerRatio += Math.random() / 50;
                    flickerRatio = (float) Math.min(0.1, flickerRatio);
                } else { // If falling
                    flickerRatio -= Math.random() / 50;
                    flickerRatio = (float) Math.max(-0.1, flickerRatio);
                }
            }
        } else {
            frames++;
        }
    }

    /**
     * Get the display size of the light, such that it accounts for flickering
     * @return Display size of the light
     */
    public float getLightDisplaySize() {
        // return lightSize + flickerSize;
        return lightSize * (1 + flickerRatio);
    }

    /**
     * Get the displayed brightness increment of the light, such that it accounts for flickering
     * @return Increment value of the light
     */
    public float getLightDisplayIncrement() {
        // return lightIncrement + flickerIncrement;
        return lightIncrement * (1 + flickerRatio);
    }

    /**
     * Decrements the size of this light
     */
    public void decrementLight() {
        if (isBurning()) {
            float decrement = decrementRate / Instance.getApp().frameRate;
            setLightSize(Math.max(0, lightSize - decrement));
        }
    }

    /**
     * Set the decrement rate of this light component using the expected time for it to burn
     * @param s Time in seconds that the light should burn for
     */
    public void setBurnTime(int s) {
        decrementRate = lightSize / s;
    }

    /**
     * Returns whether or not this light component is still burning, determined by its size
     */
    public boolean isBurning() {
        return lightSize > 0;
    }

    /**
     * Reignites a light and returns it to its default size
     */
    public void reignite() {
        setLightSize(defaultSize);
    }
}
