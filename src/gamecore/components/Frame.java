package gamecore.components;

import gamecore.Config;

public class Frame extends GameComponent {

    private final float DISPLACEMENT;

    public Frame(float displacement) {
        super();
        DISPLACEMENT = displacement;
    }

    @Override
    public void draw() {
    }

    @Override
    protected void update() {
        app.pushMatrix();
        app.translate(0, DISPLACEMENT);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
