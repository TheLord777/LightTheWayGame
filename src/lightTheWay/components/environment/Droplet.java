package lightTheWay.components.environment;

import gamecore.components.CollisionShape;
import gamecore.components.DynamicComponent;
import gamecore.components.GameComponent;
import processing.core.PVector;

public class Droplet extends DynamicComponent {
    private static final long serialVersionUID = 1L;

    public Droplet(PVector position, float diameter) {
        super(position, diameter); // Adjust size as needed

        this.collisionShape = CollisionShape.CIRCLE;
    }

    @Override
    protected float mass() {
        // Set mass based on the size of the droplet
        // For simplicity, let's assume the mass is proportional to the area of the circle
        return (float) (Math.PI * Math.pow(width / 2, 2));
    }

    @Override
    public void update() {
        applyGravity(); // Apply gravity force
        super.update(); // Update position and velocity using laws of projectile motion
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
}
