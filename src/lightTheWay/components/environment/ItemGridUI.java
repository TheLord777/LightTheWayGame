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

        float horizontalSpacing = (width - 3 * itemSize) / 4; // Adjusted for 3 columns
        float verticalSpacing = (height - itemSize) / 2; // Adjusted for 1 row

        // Add items in a 1x3 grid
        for (int col = 0; col < 3; col++) {
            float x = position.x + (itemSize + horizontalSpacing) * col + horizontalSpacing / 2;
            float y = position.y + verticalSpacing / 2;
            PVector itemPosition = new PVector(x, y);
            Item circleItem = new Item(itemPosition, itemSize / 2);
            items.add(circleItem);
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void draw() {
        app.fill(255); // Use the specified background color
        app.rect(position.x, position.y, this.width, this.height);
        // Draw each item in the grid
        for (Item item : items) {
            item.draw();
        }
    }
    public ArrayList<Item> getItems() {
        return this.items;
    }

    @Override
    protected void update() {
        // Update any necessary logic for the item grid
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    // Add methods for interaction with items
}
