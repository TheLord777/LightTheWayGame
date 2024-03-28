package gamecore.components;

import processing.core.PApplet;

public class EndFrame extends GameComponent {
    public EndFrame(PApplet app) {
        super(app);
    }

    @Override
    protected void draw() {
        app.popMatrix();
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
