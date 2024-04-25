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
        //create frame boarder
        app.fill(25);
        app.stroke(0, 0, 50);
        app.strokeWeight(10);
        app.rect(0, DISPLACEMENT - 7, app.width, app.height - Config.SCREEN_DISPLACEMENT - 10);

        app.pushMatrix();
        app.translate(DISPLACEMENT / 2, DISPLACEMENT);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
