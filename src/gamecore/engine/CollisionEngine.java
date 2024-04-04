package gamecore.engine;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Collision Engine abstract class to implement required methods for collision detection for an animation engine.
 */
public abstract class CollisionEngine {

    protected final AnimationEngine animationEngine; // The animation engine to manage the game components.

    protected CollisionEngine() {
        this.animationEngine = AnimationEngine.getInstance();
    }

    /**
     * Process all the collisions in the game.
     */
    public abstract void collisions();

    /**
     * @param p The game component to check for out of bounds.
     * @return Whether p is out of bounds.
     */
    protected abstract boolean checkOutOfBounds(GameComponent p);

    /**
     * @param c1 The first component in the collision pair
     * @param c2 The second component in the collison pair
     * @return Whether or not c1 is colliding with c2
     */
    protected boolean checkCollision(GameComponent c1, GameComponent c2) {
        if (c1.getShape() == CollisionShape.CIRCLE) {
            return checkCollisionCircleShape(c1, c2);
        } else if (c2.getShape() == CollisionShape.CIRCLE) {
            return checkCollisionCircleShape(c2, c1);
        }
        return false;
    }

    protected boolean checkCollisionCircleShape(GameComponent circle, GameComponent shape) {
        switch (shape.getShape()) {
            case CIRCLE:
                return checkCollisionCircleCircle(circle, shape);
            case RECTANGLE:
                return checkCollisionCircleRectangle(circle, shape);
            case LINE:
                return checkCollisionCircleLine(circle, shape);
            case POINT:
                return checkCollisionCirclePoint(circle, shape);
            default: // no collision method available
                return false;
        }
    }

    /**
     * @param x1 The x-coordinate of the first circle
     * @param y1 The y-coordinate of the first circle
     * @param r1 The radius of the first circle
     * @param x2 The x-coordinate of the second circle
     * @param y2 The y-coordinate of the second circle
     * @param r2 The radius of the second circle
     * @return Whether or not the first circle is colliding with the second circle
     */
    protected boolean checkCollisionCircleCircle(float x1, float y1, float r1, float x2, float y2, float r2) {
        return (PApplet.dist(x1, y1, x2, y2) <= r1 + r2);
    }

    /**
     * @param p1 The positional vector of the first circle
     * @param r1 The radius of the first circle
     * @param p2 The positional vector of the second circle
     * @param r2 The radius of the second circle
     * @return Whether or not the first circle is colliding with the second circle
     */
    protected boolean checkCollisionCircleCircle(PVector p1, float r1, PVector p2, float r2) {
        return checkCollisionCircleCircle(p1.x, p1.y, r1, p2.x, p2.y, r2);
    }

    /**
     * @param c1 The first circle game component
     * @param c2 The second circle game component
     * @return Whether or not the first circle is colliding with the second circle
     */
    protected boolean checkCollisionCircleCircle(GameComponent c1, GameComponent c2) {
        return checkCollisionCircleCircle(c1.getP(), c1.getWidth() / 2, c2.getP(), c2.getWidth() / 2);
    }

    /**
     * @param cx The x-coordinate of the circle
     * @param cy The y-coordinate of the circle
     * @param r The radius of the circle
     * @param rx1 The first x-coordinate of the rectangle (left edge)
     * @param ry1 The first y-coordinate of the rectangle (top edge)
     * @param rx2 The second x-coordinate of the rectangle (right edge)
     * @param ry2 THe second y-coordinate of the rectangle (bottom edge)
     * @return Whether or not the circle is colliding with the rectangle
     */
    protected boolean checkCollisionCircleRectangle(float cx, float cy, float r, float rx1, float ry1, float rx2, float ry2) {
        // Determine edges to check against
        float xToCheck = cx;
        float yToCheck = cy;
        // Check if left/right is closest
        if (cx < rx1) {
          xToCheck = rx1;
        } else if (cx > rx2) {
          xToCheck = rx2;
        }
        // Check if up/down is closest
        if (cy < ry1) {
          yToCheck = ry1;
        } else if (cy > ry2) {
          yToCheck = ry2;
        }
        if (PApplet.dist(xToCheck, yToCheck, cx, cy) <= r) {
          return true;
        }
        return false;
    }

    // TODO from Agung: Ask if GameComponent rectangles are positioned from center or top-left

    /**
     * @param cp The positional vector of the circle
     * @param r The radius of the circle
     * @param rp The positional vector of the rectangle
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     * @return Whether or not the circle is colliding with the rectangle
     */
    protected boolean checkCollisionCircleRectangle(PVector cp, float r, PVector rp, float width, float height) {
        float cx = PApplet.constrain(cp.x, rp.x, rp.x + width);
        float cy = PApplet.constrain(cp.y, rp.y, rp.y + height);

        return PVector.dist(cp, new PVector(cx, cy)) <= r;
    }
  
    /**
     * @param c The circle game component
     * @param r The rectangle game component
     * @return Whether or not the circle is colliding with the rectangle
     */
    protected boolean checkCollisionCircleRectangle(GameComponent c, GameComponent r) {
        return checkCollisionCircleRectangle(c.getP(), c.getWidth() / 2, r.getP(), r.getWidth(), r.getHeight());
    }

    /**
     * @param cx The x-coordinate of the circle
     * @param cy The y-coordinate of the circle
     * @param r The radius of the circle
     * @param x1 The first x-coordinate of the line
     * @param y1 The first y-coordinate of the line
     * @param x2 The second x-coordinate of the line
     * @param y2 The second y-coordinate of the line
     * @return Whether or not the circle is colliding with the line
     */
    public static boolean checkCollisionCircleLine(float cx, float cy, float r, float x1, float y1, float x2, float y2) {
        // Check either point inside circle already
        if (checkCollisionCirclePoint(cx, cy, r, x1, y1) || checkCollisionCirclePoint(cx, cy, r, x2, y2)) {
            return true;
        }
        
        // Find closest point along line vector
        float lineLength = PApplet.dist(x1, y1, x2, y2);
        float dotProduct = (((cx - x1) * (x2 - x1)) + ((cy - y1) * (y2 - y1))) / PApplet.sq(lineLength);
        float closestX = x1 + (dotProduct * (x2 - x1));
        float closestY = y1 + (dotProduct * (y2 - y1));
        
        // Check if closest point on line segment
        float distanceBetweenPoints = PApplet.dist(closestX, closestY, x1, y1) + PApplet.dist(closestX, closestY, x2, y2); // distance from 1 to closest to 2
        if (distanceBetweenPoints > lineLength + 0.1) { // buffer for calculation
            return false;
        }
        
        // Check Circle-Point collision on this point
        if (checkCollisionCirclePoint(cx, cy, r, closestX, closestY)) {
            return true;
        }
            return false;
        
    }

    /**
     * @param cp The positional vector of the circle
     * @param r The radius of the circle
     * @param l1 The positional vector of the first line endpoint
     * @param l2 The positional vector of the second line endpoint
     * @return Whether or not the circle is colliding with the line
     */
    public static boolean checkCollisionCircleLine(PVector cp, float r, PVector l1, PVector l2) {
        return checkCollisionCircleLine(cp.x, cp.y, r, l1.x, l1.y, l2.x, l2.y);
    }
  
    // TODO from Agung: Decide how we want GameComponent to represent lines – for now I've assumed the line is drawn by PVector(x,y) -> PVector(width,height)

    /**
     * @param c The circle game component
     * @param l The line game component
     * @return Whether or not the circle is colliding with the line
     */
    public static boolean checkCollisionCircleLine(GameComponent c, GameComponent l) {
        return checkCollisionCircleLine(c.getP(), c.getWidth() / 2, l.getP(), l.getP().copy().add(l.getWidth(), l.getHeight()));
    }

    /**
     * @param cx The x-coordinate of the circle
     * @param cy The y-coordinate of the circle
     * @param r The radius of the circle
     * @param px The x-coordinate of the point
     * @param py The y-coordinate of the point
     * @return Whether or not the circle is colliding with the point
     */
    public static boolean checkCollisionCirclePoint(float cx, float cy, float r, float px, float py) {
        return (PApplet.dist(cx, cy, px, py) <= r);
    }

    /**
     * @param cp The positional vector of the circle
     * @param r The radius of the circle
     * @param pp The positional vector of the point
     * @return Whether or not the circle is colliding with the point
     */
    public static boolean checkCollisionCirclePoint(PVector cp, float r, PVector pp) {
        return checkCollisionCirclePoint(cp.x, cp.y, r, pp.x, pp.y);
    }

    /**
     * @param c The circle game component
     * @param p The point game component
     * @return Whether or not the circle is colliding with the point
     */
    public static boolean checkCollisionCirclePoint(GameComponent c, GameComponent p) {
        return checkCollisionCirclePoint(c.getP(), c.getWidth() / 2, p.getP());
    }

}
