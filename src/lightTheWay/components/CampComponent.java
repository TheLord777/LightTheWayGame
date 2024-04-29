package lightTheWay.components;

import gamecore.components.GameComponent;
import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PVector;

public class CampComponent extends LightComponent {

    public CampComponent(PVector p, float l) {
        super(p, l, 0);
    }    

    public CampComponent(float x, float y, float l) {
        super(x, y, l);
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

        drawTent(p.x - 5 * diameter / 4, yPosition - 5, true, 1);

        drawTent(p.x + diameter, yPosition, true, 0);

        drawTent(p.x - diameter, yPosition, false, 2);

    }

    protected void drawTent(float xPosition, float yPosition, boolean faceLeft, int colorChoice) {
        PApplet app = Instance.getApp();

        float tentHeight = this.getLightSize() / 6.25f;
        float tentOpeningWidth = this.getLightSize() / 10;

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
        app.triangle(xPosition, yPosition, xPosition + (tentOpeningWidth * directionMultiplier), yPosition, xPosition + (tentOpeningWidth / 2 * directionMultiplier), yPosition - tentHeight);

        app.fill(205,133,63);
        app.rect(xPosition + (tentOpeningWidth / 2 * directionMultiplier), yPosition - tentHeight, (3 * directionMultiplier), tentHeight);

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
        app.quad(xPosition + (tentOpeningWidth * directionMultiplier), yPosition, 
                 xPosition + (tentOpeningWidth / 2 * directionMultiplier), yPosition - tentHeight, 
                 xPosition + ((tentHeight + tentOpeningWidth / 2) * directionMultiplier), yPosition - tentHeight,
                 xPosition + ((tentHeight + tentOpeningWidth) * directionMultiplier), yPosition);
    }

    @Override
    protected void update() {
        super.update();


    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }

    @Override
    public void setIlluminated(boolean b) {
        this.illuminated = true; // can never not be illuminated
    }

}