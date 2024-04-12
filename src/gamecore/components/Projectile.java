package gamecore.components;

import gamecore.Config;
import gamecore.progression.Progression;
import gamecore.progression.ProjectileProgression;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * A projectile is a game component that is launched and travels through the game world.
 */
public abstract class Projectile extends DynamicComponent {

    private final ArrayList<PVector> trail = new ArrayList<>(); // To create a trail of the projectile.
    protected Progression progression; // Track the state of the projectile.
    protected boolean drawTrail; // Whether to draw the trail of the projectile.


    Projectile(PVector p, float width, float height) {
        super(p, width, height);
        this.progression = ProjectileProgression.PRELAUNCH; // All projectiles start in the prelaunch state.
        drawTrail = true; // Default to drawing the trail with the projectile.
    }

    public boolean isPrelaunch() {
        return this.progression == ProjectileProgression.PRELAUNCH;
    }

    public boolean isLaunched() {
        return this.progression == ProjectileProgression.LAUNCHED;
    }

    protected void noTrail() {
        drawTrail = false;
    }

    /**
     * Method to draw sprite of the projectile.
     */
    protected abstract void drawProjectile();

    /**
     * Draw the projectile and its trail.
     */
    public void draw() {
        if (drawTrail) drawTrail();
        drawProjectile();
    }

    /**
     * @param f The initial force to launch the projectile with.
     */
    protected void launch(PVector f) {
        this.progression = ProjectileProgression.LAUNCHED;
        applyForce(f);
    }

    /**
     * track the past 10 positions of the projectile to create a trail.
     */
    protected void addTrail() {
        if (trail.size() == 4) trail.remove(0);
        trail.add(new PVector(p.x, p.y));
    }

    protected void drawTrail() {
        app.noStroke();
        trail.forEach(v -> {
            app.fill(Config.TRAIL_COLOUR, 0, 0, 40);
            app.circle(v.x, v.y, width / 1.1f);
        });
    }


    @Override
    public void update() {
        applyGravity(); // All projectiles are affected by gravity
        addTrail();
        super.update();
        // If the projectile is out of bounds reset it.
        if (outOfBounds()) reset();
    }

    public void reset() {
        super.reset();
        trail.clear();
        this.progression = ProjectileProgression.PRELAUNCH;
        clearForce();
    }




}
