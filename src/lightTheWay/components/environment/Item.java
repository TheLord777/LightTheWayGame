package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class Item extends GameComponent {
    // Add properties and methods for the item
    public Item(PVector p, float width){
        super(p, width);
    }
    @Override
    public void draw() {
        app.fill(100);
        app.stroke(0);
        app.ellipse(p.x, p.y, width*2, width*2);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}
