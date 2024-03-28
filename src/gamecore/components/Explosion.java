package gamecore.components;

import gamecore.Config;
import gamecore.progression.ExplosionProgression;
import gamecore.progression.Progression;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Explosion extends Projectile {

    private float explosionRadius;
    private Progression explosionProg;

    protected Explosion(PApplet a, PVector p, float width, float height) {
        super(a, p, width, height);
        this.explosionProg = ExplosionProgression.PREEXPLOSION;
        this.explosionRadius = width;
    }


    public boolean isExploding() {
        return this.explosionProg == ExplosionProgression.EXPLOSION;
    }

    public boolean isPreExplosion() {
        return this.explosionProg == ExplosionProgression.PREEXPLOSION;
    }

    public boolean exploaded() {
        return this.explosionProg == ExplosionProgression.POSTEXPLOSION;
    }

    public final void draw() {
        if (isExploding()) {
            drawExplosion();
        } else {
            if (drawTrail) drawTrail();
            drawProjectile();
        }
    }


    protected void drawExplosion() {
        if (app.frameCount % 2 == 0) {
            app.fill(220, 20, 60);
        } else {
            app.fill(255, 69, 0);
        }

        app.circle(p.x, p.y, explosionRadius);

        app.fill(128, 0, 0);
        app.circle(p.x, p.y, app.random(0, explosionRadius));
    }


    public void explode() {
        clearForce();
        clearVelocity();
        this.explosionProg = ExplosionProgression.EXPLOSION;
    }


    /**
     * @return Set the width to be the explosion radius for accurate collision detection.
     */
    public float getWidth() {
        return explosionRadius;
    }

    public float getHeight() {
        return explosionRadius;
    }

    public void update() {
        super.update();
        updateExplosion();
    }

    protected void updateExplosion() {
        if (explosionRadius > Config.EXPLOSION_RADIUS)
            explosionProg = ExplosionProgression.POSTEXPLOSION;
        else if (isExploding())
            explosionRadius += Config.EXPLOSION_GROWTH_RATE;
    }

    public void reset() {
        super.reset();
        this.explosionProg = ExplosionProgression.PREEXPLOSION;
        this.explosionRadius = width;
    }

    public void fire(PVector ip, PVector id) {
        setPosition(ip);
        launch(id);
    }


}
