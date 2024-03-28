package gamecore.engine;


import gamecore.components.GameComponent;

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


}
