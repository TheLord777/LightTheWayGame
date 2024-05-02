package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

import java.util.ArrayList;

public class ItemGridUI extends GameComponent {
    private ArrayList<Item> items;
    private PVector position;
    private float width;
    private float height;
    private float itemSize;

    public ItemGridUI(PVector position, float width, float height, float itemSize) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.itemSize = itemSize;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void draw() {
        // Draw the background for the item grid
        app.fill(255);
        app.rect(position.x, position.y, width, height);

        // Draw each item in the grid
        float x = position.x + itemSize / 2;
        float y = position.y + itemSize / 2;
        for (Item item : items) {
            item.draw(x, y, itemSize);
            x += itemSize;
            if (x >= position.x + width) {
                x = position.x + itemSize / 2;
                y += itemSize;
            }
        }
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    // Add methods for interaction with items
}



