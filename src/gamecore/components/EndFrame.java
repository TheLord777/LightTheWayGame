package gamecore.components;

public class EndFrame extends GameComponent {
    public EndFrame() {
        super();
    }

    @Override
    public void draw() {
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
