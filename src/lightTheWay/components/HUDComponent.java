package lightTheWay.components;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import lightTheWay.Instance;
import lightTheWay.components.characters.PlayableCharacter;
import lightTheWay.components.environment.ItemType;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class HUDComponent extends GameComponent {

    PlayableCharacter hero;

    int altitude = 0;
    int selectedSlot = -1;
    // boolean[] inventory = new boolean[6];
    // boolean[] inventory = {true, true, false, false, false, false};

    public HUDComponent() {
        super();
        float width = Math.min(Math.max(Instance.getApp().width / 2, 200), 800);
        float height = width / 8;
        PVector topLeft = new PVector((Instance.getApp().width - width) / 2, Instance.getApp().height - height);
        this.width = width;
        this.height = height;
        this.p = topLeft;
        this.setShape(CollisionShape.POINT);
    }

    public void setHero(PlayableCharacter hero) {
        this.hero = hero;
    }

    @Override
    public void draw() {
        app.fill(102, 102, 102);
        app.triangle(p.x, p.y - 20, p.x, p.y + height, p.x - width / 16, p.y + height);
        app.rect(p.x, p.y - 20, width, height + 20);
        app.triangle(p.x + width, p.y - 20, p.x + width, p.y + height, p.x  + 17 * width / 16, p.y + height);

        float xPosition = p.x + 10;
        float yPosition = p.y;
        float padding = width / 80;

        app.fill(183, 183, 183);
        drawAltimeter(xPosition, yPosition);
        xPosition += width / 6;
        xPosition += padding;
        app.rect(xPosition, yPosition - 10, height, height);
        xPosition += height;
        xPosition += padding;
        for (int i = 0; i < 6; i++) {
            drawInventorySlot(xPosition, yPosition, i, selectedSlot == i);
            xPosition += width / 10;
            xPosition += padding;
        }
        app.noStroke();

        drawFire();
    }

    protected void drawAltimeter(float x, float y) {
        app.fill(183, 183, 183);
        app.rect(x, y, width / 6, width / 10);
        app.fill(102, 102, 102);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.textSize(width / 16);
        app.text(altitude + "m", x + width / 12, y + width / 20);
    }

    protected void drawInventorySlot(float x, float y, int slotNumber, boolean selected) {
        if (hero.inventory[slotNumber] != ItemType.NO_ITEM) {
            app.fill(183, 255, 183);
        } else {
            app.fill(255, 183, 183);
        }
        app.pushStyle();
        if (selected) {
            app.stroke(255, 100, 100);
            app.strokeWeight(2);
        } else {
            app.strokeWeight(0);
        }
        app.rect(x, y, width / 10, width / 10);
        app.popStyle();
        drawItem(slotNumber, hero.inventory[slotNumber], new PVector(x, y), width / 10);
        // app.fill(102, 102, 102);
        // app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
        // app.textSize(width / 10);
        // app.text(slotNumber + 1, x + width / 20, y + width / 10);
        // app.textSize(16);
    }

    protected void drawItem(int slotNumber, ItemType type, PVector p, float slotWidth) {
        PApplet app = Instance.getApp();
        switch (type) {
            case SMALL_REFILL:
                drawLighter(p, slotWidth, 100, 150, 200);
                break;
            case MEDIUM_REFILL:
                drawLighter(p, slotWidth, 100, 200, 150);
                break;
            case LARGE_REFILL:
                drawLighter(p, slotWidth, 200, 100, 100);
                break;
            case FULL_REFILL:
                drawLighter(p, slotWidth, 255, 255, 255);
                break;
            case KEY:
                app.pushStyle();
                app.stroke(255, 198, 55);
                app.strokeWeight(slotWidth / 10);
                app.fill(183, 255, 183);
                app.line(p.x + slotWidth / 4, p.y + 3 * slotWidth / 4, p.x + 3 * slotWidth / 4, p.y + slotWidth / 4);
                app.line(p.x + 3 * slotWidth / 4, p.y + slotWidth / 4, p.x + 3 * slotWidth / 4 + slotWidth / 8, p.y + slotWidth / 4 + slotWidth / 8);
                app.line(p.x + 3 * slotWidth / 4 - slotWidth / 6, p.y + slotWidth / 4 + slotWidth / 6, p.x + 3 * slotWidth / 4 - slotWidth / 6 + slotWidth / 8, p.y + slotWidth / 4 + slotWidth / 6 + slotWidth / 8);
                app.circle(p.x + slotWidth / 4 + slotWidth / 10, p.y + 3 * slotWidth / 4 - slotWidth / 10, slotWidth / 3);
                app.popStyle();
                break;
            case TORCH:
                app.fill(139, 69, 19);
                app.rect(p.x + 2 * slotWidth / 5, p.y + slotWidth / 6, slotWidth / 5, 2 * slotWidth / 3);
                break;
            case BONFIRE:
                app.fill(139, 69, 19);
                app.quad(p.x + slotWidth / 6 - slotWidth / 15, p.y + slotWidth / 6 + slotWidth / 15,
                        p.x + slotWidth / 6 + slotWidth / 15, p.y + slotWidth / 6 - slotWidth / 15,
                        p.x + 5 * slotWidth / 6 + slotWidth / 15, p.y + 5 * slotWidth / 6 - slotWidth / 15,
                        p.x + 5 * slotWidth / 6 - slotWidth / 15, p.y + 5 * slotWidth / 6 + slotWidth / 15);

                app.quad(p.x + 5 * slotWidth / 6 - slotWidth / 15, p.y + slotWidth / 6 - slotWidth / 15,
                        p.x + 5 * slotWidth / 6 + slotWidth / 15, p.y + slotWidth / 6 + slotWidth / 15,
                        p.x + slotWidth / 6 + slotWidth / 15, p.y + 5 * slotWidth / 6 + slotWidth / 15,
                        p.x + slotWidth / 6 - slotWidth / 15, p.y + 5 * slotWidth / 6 - slotWidth / 15);
                break;
            case NO_ITEM:
                app.fill(102, 102, 102);
                app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
                app.textSize(width / 10);
                app.text(slotNumber + 1, p.x + width / 20, p.y + width / 10);
                break;
        }
    }

    private void drawLighter(PVector p, float slotWidth, float r, float g, float b) {
        PApplet app = Instance.getApp();
        app.fill(20, 20, 20);
        app.circle(p.x + slotWidth / 2 - slotWidth / 20, p.y + slotWidth / 4 + slotWidth / 20, slotWidth / 4);
        app.rect(p.x + slotWidth / 2, p.y + slotWidth / 2 - slotWidth / 8, slotWidth / 4, slotWidth / 16);
        app.fill(100, 100, 100);
        app.rect(p.x + slotWidth / 4, p.y + slotWidth / 4, slotWidth / 4, slotWidth / 4);
        app.rect(p.x + slotWidth / 4, p.y + slotWidth / 2 - slotWidth / 16, slotWidth / 2, slotWidth / 16);
        app.fill(r, g, b);
        app.rect(p.x + slotWidth / 4, p.y + slotWidth / 2, slotWidth / 2, 3 * slotWidth / 8);
    }

    protected void drawFire() {
        LightComponent light = hero.getLight();

       app.fill(255, 165, 0);
       drawFireSingleShade(light.getLightDisplaySize() / 10 * light.getDefaultSize() / height);

       app.fill(255, 0, 0);
       drawFireSingleShade(light.getLightDisplaySize() / 15 * light.getDefaultSize() / height);
    }

    protected void drawFireSingleShade(float circleRadius) {
        PVector indicatorPosition = new PVector(p.x + 10 + width / 6 + width / 80 + height / 2, p.y + (height / 2));
        app.circle(indicatorPosition.x, indicatorPosition.y, circleRadius);
        app.triangle(indicatorPosition.x - circleRadius / 2, indicatorPosition.y, indicatorPosition.x + circleRadius / 2, indicatorPosition.y, indicatorPosition.x, indicatorPosition.y - circleRadius);
        app.triangle(indicatorPosition.x - circleRadius / 2, indicatorPosition.y, indicatorPosition.x + circleRadius / 2, indicatorPosition.y, indicatorPosition.x - circleRadius / 2, indicatorPosition.y - circleRadius / 2);
        app.triangle(indicatorPosition.x - circleRadius / 2, indicatorPosition.y, indicatorPosition.x + circleRadius / 2, indicatorPosition.y, indicatorPosition.x + circleRadius / 2, indicatorPosition.y - 2 * circleRadius / 3);
    }

    @Override
    protected void update() {
        // p.x++;
        // p.y++;
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    public void setSelectedSlot(int slot) {
        selectedSlot = slot;
    }

    public ItemType getSelectedType() {
        return hero.inventory[selectedSlot];
    }

    public boolean useSelectedSlot() {
        if (selectedSlot >= 0) {
            return hero.useItem(selectedSlot);
        } else {
            return false;
        }
    }

    @Override
    public void setIlluminated(boolean b) {
        this.illuminated = true; // can never not be illuminated
    }

    public void setAltitude(int a) {
        altitude = a;
    }
    
}
