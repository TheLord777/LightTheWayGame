package gamecore.components;

import gamecore.Config;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Dynamic Component abstract class to implement required methods for dynamic game components.
 * A dynamic component is a game component that moves and is affected by forces.
 */
public abstract class DynamicComponent extends GameComponent {

    protected PVector v, f; // velocity and force vectors
    protected float invm; // inverse mass

    // damping factor to simulate drag, as per
    // Millington
    protected final static float DAMPING = .995f;

    DynamicComponent(PApplet a, PVector p, float width, float height) {
        super(a, p, width, height);
        this.v = new PVector(0, 0);
        this.f = new PVector(0, 0);
        this.invm = 1 / mass(); // inverse mass. Store to avoid repeated calculation.
    }

    protected DynamicComponent(PApplet a, PVector p, float width) {
        super(a, p, width);
        this.v = new PVector(0, 0);
        this.f = new PVector(0, 0);
        this.invm = 1 / mass(); // inverse mass. Store to avoid repeated calculation.
    }


    /**
     * Each dynamic component must implement a method to return its mass.
     *
     * @return The mass of the dynamic component.
     */
    protected abstract float mass();

    /**
     * Apply a force to the dynamic component.
     *
     * @param force The force to apply to the dynamic component.
     */
    public void applyForce(PVector force) {
        f.add(force);
    }

    public void applyVelocity(PVector veloctiy){
        v.add(veloctiy);
    }

    protected void setVelocity(PVector v) {
        this.v = new PVector(v.x, v.y);
    }

    public void clearVelocity() {
        v.mult(0);
    }

    public void clearForce() {
        f.mult(0);
    }

    public void setPosition(PVector p) {
        this.p = new PVector(p.x, p.y);
    }


    /**
     * Perform the integration step to update the dynamic component's position and velocity using laws of projectile motion.
     */
    public void update() {
        PVector a = f.copy().mult(invm); // a = f * 1/m (Newton's second law)
        v.add(a); // v = v + a
        v.mult((DAMPING)); // v = v * damping (drag)
        p.add(v); // p = p + v

        clearForce(); // clear the force
    }

    protected void setWidth(int width) {
        this.width = width;
        this.invm = 1 / mass(); // If the width changes, the inverse mass must be recalculated.
    }

    protected void setHeight(int height) {
        this.height = height;
        this.invm = 1 / mass(); // If the height changes, the inverse mass must be recalculated.
    }

    public PVector getV() {
        return v;
    }

    public void reset() {
        clearVelocity();
    }


    /**
     * @return Whether the dynamic component is out of bounds.
     */
    public boolean outOfBounds() {
        return p.x - Config.BOUNDRY > app.displayWidth || p.x + Config.BOUNDRY < 0;
    }
}
