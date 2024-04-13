package lightTheWay.components;

import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PApplet;

public class CampComponent extends LightComponent {

    public CampComponent(float x, float y, float w, float h, float l) {
        super(x, y, w, h, l);
    }

    @Override
    protected void draw() {
        drawCamp();

        drawLogs();

        super.draw();
    }

    protected void drawLogs() {
        PApplet app = Instance.getApp();
        app.fill(205,133,63);
        float diameter = this.getLightSize() / 10;
        
        app.quad(p.x - diameter / 2 - 5, p.y + diameter / 2 - 5, 
                 p.x - diameter / 2, p.y + diameter / 2 - 10, 
                 p.x + diameter / 2 + 5, p.y + diameter / 2 + 5, 
                 p.x + diameter / 2, p.y + diameter / 2 + 10);

        app.quad(p.x + diameter / 2 + 5, p.y + diameter / 2 - 5, 
                 p.x + diameter / 2, p.y + diameter / 2 - 10, 
                 p.x - diameter / 2 - 5, p.y + diameter / 2 + 5, 
                 p.x - diameter / 2, p.y + diameter / 2 + 10);
    }

    protected void drawCamp() {
        float diameter = this.getLightSize() / 10;

        float yPosition = p.y + diameter / 2 + 5;

        drawTent(p.x - 3 * diameter / 2, yPosition - 5, true, 1);

        drawTent(p.x + diameter, yPosition, true, 0);

        drawTent(p.x - diameter, yPosition, false, 2);

    }

    protected void drawTent(float xPosition, float yPosition, boolean faceLeft, int colorChoice) {
        PApplet app = Instance.getApp();

        int directionMultiplier = faceLeft ? 1 : -1;

        switch (colorChoice) {
            case 0: // blue
                app.fill(28, 69, 135);
                break;
            case 1: // red
                app.fill(102, 0, 0);
                break;
            case 2: // green
                app.fill(39, 78, 19);
                break;
            default: // white (fallback)
                app.fill(150, 150, 150);
        }
        app.triangle(xPosition, yPosition, xPosition + (50 * directionMultiplier), yPosition, xPosition + (25 * directionMultiplier), yPosition - 80);

        app.fill(205,133,63);
        app.rect(xPosition + (25 * directionMultiplier), yPosition - 80, (5 * directionMultiplier), 80);

        switch (colorChoice) {
            case 0: // blue
                app.fill(17, 85, 204);
                break;
            case 1: // red
                app.fill(153, 0, 0);
                break;
            case 2: // green
                app.fill(56, 118, 29);
                break;
            default: // white (fallback)
                app.fill(200, 200, 200);
        }
        app.quad(xPosition + (50 * directionMultiplier), yPosition, 
                 xPosition + (25 * directionMultiplier), yPosition - 80, 
                 xPosition + (105 * directionMultiplier), yPosition - 80,
                 xPosition + (130 * directionMultiplier), yPosition);
    }

    @Override
    protected void update() {
        super.update();


    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }


}