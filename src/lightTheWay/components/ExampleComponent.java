package lightTheWay.components;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class ExampleComponent extends GameComponent {

    public ExampleComponent(PVector p ) {
        super(p, 100, 150);
    }

    @Override
    protected void draw() {
        app.fill(255, 0, 0);
        app.rect(p.x, p.y, width, height);
    }

    @Override
    protected void update() {
        p.x++;
        p.y++;
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
