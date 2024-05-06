package gamecore;

import processing.core.PVector;

public abstract class Config {


    public static final float BOUNDRY = 50; // default value for out of bounds.
    public static final int TRAIL_COLOUR = 128;
    public static int EXPLOSION_RADIUS;
    public static final float EXPLOSION_GROWTH_RATE = 0.1f;
    public static PVector GRAVITY;
    public static int SCREEN_DISPLACEMENT = 50;


    public static void setGravity(float gravity) {
        GRAVITY = new PVector(0, gravity);
    }

}
