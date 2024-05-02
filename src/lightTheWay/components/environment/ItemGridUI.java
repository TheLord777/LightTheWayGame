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

        float horizontalSpacing = (width - 3 * itemSize) / 4;
        float verticalSpacing = (height - 2 * itemSize) / 3;

        // Add items in a 3x2 grid
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                float x = position.x + (itemSize + horizontalSpacing) * col + horizontalSpacing / 2;
                float y = position.y + (itemSize + verticalSpacing) * row + verticalSpacing / 2;
                PVector itemPosition = new PVector(x, y);
                Item circleItem = new Item(itemPosition, itemSize / 2);
                items.add(circleItem);
            }
        }
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
        app.rect(position.x, position.y, width* 2, height * 2);

        // Draw each item in the grid
        for (Item item : items) {
            item.draw();
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



