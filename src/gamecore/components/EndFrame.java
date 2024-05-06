package gamecore.components;

public class EndFrame extends GameComponent {
    public EndFrame() {
        super();
    }

    @Override
    public void draw() {

    }

    @Override
    protected void update() {
        app.popMatrix();
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
