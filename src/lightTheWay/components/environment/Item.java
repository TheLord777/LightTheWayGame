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
        app.ellipse(p.x, p.y, width, width);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Check if the mouse click position intersects with the item
        return false;
    }
    public boolean clicked(float x, float y) {
        // Check if the given point (x, y) is within the circle
        float distanceSquared = (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
        float radiusSquared = (width / 2) * (width / 2);
        return distanceSquared <= radiusSquared;
    }
}
