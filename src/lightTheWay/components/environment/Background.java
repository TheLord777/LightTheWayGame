package lightTheWay.components.environment;

import gamecore.components.GameComponent;

public class Background extends GameComponent {
    private static final long serialVersionUID = 1L;

    public Background(float height){
        setHeight(height);
    }

    @Override
    public void draw() {
//        app.fill(55, 44, 44);
//        app.rect(0, 0, app.width,height);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
