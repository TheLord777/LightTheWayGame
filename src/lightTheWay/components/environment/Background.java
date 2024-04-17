package lightTheWay.components.environment;

import gamecore.components.GameComponent;

import java.io.Serializable;

public class Background extends GameComponent implements Serializable {

    @Override
    protected void draw() {
        app.fill(55, 44, 44);
        app.rect(0, 0, app.width,app.height);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
