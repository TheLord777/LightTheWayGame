package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PConstants;
import processing.core.PVector;

public class LockCell extends WallCell{

    private float promptHeight = p.y - height * 1.5f;
    private boolean promptDirection = false; // true -> upwards, false -> downwards

    public LockCell(PVector p, float width, float height) {
        super(p, width, height);
    }

    @Override
    public void draw() {
        super.draw();

        app.pushStyle();

        app.fill(0, 0);
        app.stroke(255, 198, 55);
        app.strokeWeight(width / 10);
        app.circle(p.x + width / 2, p.y + 3 * width / 10, width / 3);
        app.fill(255, 198, 55);
        app.rect(p.x + width / 5, p.y + 3 * width / 10, 3 * width / 5, 3 * width / 5);
        app.fill(20, 20, 20);
        app.stroke(20, 20, 20);
        app.strokeWeight(0);
        app.rect(p.x + width / 2 - width / 12, p.y + width / 2, width / 6, width / 3);
        app.circle(p.x + width / 2, p.y + width / 2, width / 3);

        app.popStyle();
    }

    public boolean isSpawnCell() {
        return isSpawnCell;
    }

    public void setSpawnCell(boolean spawnCell) {
        isSpawnCell = spawnCell;
    }
    @Override
    public boolean intersection(GameComponent ge) {
        return super.intersection(ge);
    }

    public void drawPrompt() {
        app.fill(200, 200, 200);
        app.rect(p.x, promptHeight, width, height);
        app.fill(100, 100, 100);
        app.textSize(width);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text("F", p.x + width / 2, promptHeight + height / 2);
        updatePromptPosition();
    }

    public PVector getPromptPosition() {
        return new PVector(p.x, promptHeight);
    }

    public void updatePromptPosition() {
        if (promptDirection) {
            promptHeight -= height * 0.02f;
            if (promptHeight <= p.y - height * 1.5f) {
                promptDirection = false;
                promptHeight = p.y - height * 1.5f;
            }
        } else {
            promptHeight += height * 0.02f;
            if (promptHeight >= p.y - height * 1.1f) {
                promptDirection = true;
                promptHeight = p.y - height * 1.1f;
            }
        }
    }
}
