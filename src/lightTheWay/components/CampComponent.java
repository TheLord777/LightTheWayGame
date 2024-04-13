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
        drawLogs();

        super.draw();

        drawCamp();
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
        PApplet app = Instance.getApp();
        float diameter = this.getLightSize() / 10;

        app.fill(28, 69, 135);
        app.triangle(p.x + diameter, p.y + diameter / 2 + 5, p.x + diameter + 50, p.y + diameter / 2 + 5, p.x + diameter + 25, p.y + diameter / 2 - 75);

        app.fill(205,133,63);
        app.rect(p.x + diameter + 25, p.y + diameter / 2 - 75, 5, 80);

        app.fill(17, 85, 204);
        app.quad(p.x + diameter + 50, p.y + diameter / 2 + 5, 
                 p.x + diameter + 25, p.y + diameter / 2 - 75, 
                 p.x + diameter + 105, p.y + diameter / 2 - 75,
                 p.x + diameter + 130, p.y + diameter / 2 + 5);

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