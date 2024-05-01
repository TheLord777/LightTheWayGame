package lightTheWay.components.environment;

import gamecore.components.GameComponent;

import processing.core.PVector;

public class Droplet extends GameComponent {

    private PVector velocity;
    private double gravity;
    private float diameter;
    private Level level;
    private Stalactite stalactite;

    public Droplet(Level level,Stalactite stalactite, PVector position, double gravity, float diameter) {
        super(position, diameter); // Adjust size as needed
        this.gravity = gravity;
        this.velocity = new PVector(0, 0);
        this.level = level;
        this.stalactite = stalactite;

    }

    @Override
    public void update() {
        // Apply gravity to the velocity
        velocity.y += gravity;

        // Update position based on velocity
        p.add(velocity);

        // Check for collisions with walls
        Cell cell = level.getCellFromPoint(p);
        if (cell instanceof WallCell) {
            // Droplet has hit a wall, remove it from the game
            stalactite.removeDroplet(this);
        }
    }


    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    @Override
    public void draw() {
        // Draw the water droplet
        app.fill(0, 0, 255); // Blue color
        app.ellipse(p.x, p.y, width, height);
    }

    // Method to reset the droplet position and velocity when needed
    public void reset(PVector position) {
        this.p = position.copy();
        this.velocity.set(0, 0);
    }


}
