package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PVector;

public class Item extends GameComponent {
    private ItemType type;

    // Add properties and methods for the item
    public Item(PVector p, float width, ItemType type){
        super(p, width);
    }

    @Override
    public void draw() {
        
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    public ItemType getType() {
        return type;
    }
}
