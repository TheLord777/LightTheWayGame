package gamecore.components;

import lightTheWay.components.environment.Background;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.Serializable;

/**
 * Base Game Component class for all game components.
 * These elements are static and are drawn and updated in the game loop.
 */
public abstract class GameComponent implements Serializable {

    protected static PApplet app; // app to draw on.
    private static boolean mutex = false;
    protected PVector p; // position of the component.
    protected float width;
    protected float height;
    protected CollisionShape collisionShape; // TODO implement collision shapes
    protected boolean illuminated = false; // whether or not the component is within proximity to a light source

    public static void setApp(PApplet app) {
        if (!mutex) GameComponent.app = app;
        mutex = true;

    }

    public GameComponent(PVector p, float width, float height) {
        this.p = p;
        this.width = width;
        this.height = height;
    }

    public GameComponent(PVector p, float width) {
        this.p = p;
        this.width = width;
        this.height = width; // for circle components
    }

    public GameComponent() {

    }

    /**
     * Draw and update the game component.
     */
    public void step() {
        if (illuminated)
            draw(); // draws if illuminated
        update();
        setIlluminated(false);
    }

    /**
     * Draw the game component.
     */
    public abstract void draw();

    /**
     * Update the game component.
     */
    protected abstract void update();


    /**
     * Each component must implement a method to check for intersection with another game component.
     *
     * @param ge The game component to check for intersection.
     * @return Whether this game component intersects with ge.
     */
    public abstract boolean intersection(GameComponent ge);

    // HELPER INTERSECTION METHODS

    /**
     * @param gc A circle intersection boundary game component to check for intersection.
     * @return Whether this game component intersects with gc.
     */
    protected boolean circleIntersectsRectangle(GameComponent gc) {
        // Find the closest point on the rectangle to the circle's center
        float cx = PApplet.constrain(gc.getX(), this.getX(), this.getX() + this.getWidth());
        float cy = PApplet.constrain(gc.getY(), this.getY(), this.getY() + this.getHeight());

        return PVector.dist(gc.getP(), new PVector(cx, cy)) <= gc.getWidth() / 2;

    }

    public boolean pointIntersectsRectangle(PVector p) {
        return p.x > this.p.x && p.x < this.p.x + width && p.y > this.p.y && p.y < this.p.y + height;
    }


    /**
     * @param gc A circle intersection boundary game component to check for intersection.
     * @return Whether this game component intersects with gc.
     */
    protected boolean circleIntersectsCircle(GameComponent gc) {
        float dx = getX() - gc.getX();
        float dy = getY() - gc.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (getWidth() / 2) + (gc.getWidth() / 2);
    }


    /**
     * @param gc A circle intersection boundary game component to check for intersection.
     * @return Whether this game component intersects with gc.
     */
    protected boolean circleIntersectsCircle(GameComponent gc, float width) {
        float dx = getX() - gc.getX();
        float dy = getY() - gc.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (width / 2) + (gc.getWidth() / 2);
    }


    // GETTERS AND SETTERS

    public float getX() {
        return p.x;
    }

    public float getY() {
        return p.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public PVector getP() {
        return p;
    }

    public CollisionShape getShape() {
        return collisionShape;
    }

    public void setShape(CollisionShape c) {
        collisionShape = c;
    }

    public boolean getIlluminated() {
        return illuminated;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public void setP(PVector v) {
        this.p = v;
    }

    public void setIlluminated(boolean b) {
        this.illuminated = b;
    }

}
