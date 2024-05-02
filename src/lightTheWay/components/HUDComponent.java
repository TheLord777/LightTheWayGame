package lightTheWay.components;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import lightTheWay.Instance;
import lightTheWay.components.characters.PlayableCharacter;
import processing.core.PConstants;
import processing.core.PVector;

public class HUDComponent extends GameComponent {

    PlayableCharacter hero;

    int selectedSlot = -1;
    // boolean[] inventory = new boolean[6];
    boolean[] inventory = {true, true, false, false, false, false};

    public HUDComponent(PlayableCharacter pc) {
        super();
        this.hero = pc;
        float width = Math.min(Math.max(Instance.getApp().width / 2, 200), 800);
        float height = width / 8;
        PVector topLeft = new PVector((Instance.getApp().width - width) / 2, Instance.getApp().height - height);
        this.width = width;
        this.height = height;
        this.p = topLeft;
        this.setShape(CollisionShape.POINT);
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
        // app.rect(xPosition, yPosition, width / 6, width / 10);
        xPosition += width / 6;
        xPosition += padding;
        app.rect(xPosition, yPosition - 10, height, height);
        xPosition += height;
        xPosition += padding;
        for (int i = 0; i < 6; i++) {
            drawInventorySlot(xPosition, yPosition, i, selectedSlot == i);
            // app.rect(xPosition, yPosition, width / 10, width / 10);
            xPosition += width / 10;
            xPosition += padding;
        }
        app.strokeWeight(0);

        drawFire();
    }

    protected void drawAltimeter(float x, float y) {
        app.fill(183, 183, 183);
        app.rect(x, y, width / 6, width / 10);
    }

    protected void drawInventorySlot(float x, float y, int slotNumber, boolean selected) {
        if (inventory[slotNumber]) {
            app.fill(183, 255, 183);
        } else {
            app.fill(255, 183, 183);
        }
        if (selected) {
            app.stroke(255, 100, 100);
            app.strokeWeight(2);
        } else {
            app.strokeWeight(0);
        }
        app.rect(x, y, width / 10, width / 10);
        app.fill(102, 102, 102);
        app.textAlign(PConstants.CENTER, PConstants.BOTTOM);
        app.textSize(width / 10);
        app.text(slotNumber + 1, x + width / 20, y + width / 10);
        app.textSize(16);
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

    public boolean useSelectedSlot() {
        if (selectedSlot >= 0) {
            boolean slotContent = inventory[selectedSlot];
            inventory[selectedSlot] = false;
            return slotContent;
        } else {
            return false;
        }
    }

    @Override
    public void setIlluminated(boolean b) {
        this.illuminated = true; // can never not be illuminated
    }
    
}
