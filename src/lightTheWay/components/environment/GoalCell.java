package lightTheWay.components.environment;

import lightTheWay.components.LightComponent;
import processing.core.PVector;

public class GoalCell extends Cell {


    private LightComponent lightComponent;


    public GoalCell(PVector p, float width, float height) {
        super(p, width, height);
        lightComponent = new LightComponent(p.x + width /2, p.y / 2, width *5);
    }

    @Override
    public void setWidth(float f) {
        super.setWidth(f);
       lightComponent = new LightComponent(p.x + width /2, p.y + height /2, width *5);
    }

    @Override
    public void setHeight(float f) {
        super.setHeight(f);
        lightComponent = new LightComponent(p.x + width /2, p.y + height /2, width *5);
    }

    @Override
    public void setP(PVector v) {
        super.setP(v);
        lightComponent = new LightComponent(p.x + width /2, p.y + height /2, width *5);
    }

    @Override
    public void draw() {
        app.fill(255);
        app.rect(p.x + width / 2, p.y, 10, 10);
//        app.strokeWeight(10);
        app.stroke(255);
        app.line(p.x + width / 3, p.y + height, p.x + width / 3, p.y);
        app.noStroke();

//        lightComponent.draw();
    }

    @Override
    protected void update() {
        super.update();
        illuminated = true;
    }

    public LightComponent getLightComponent() {
        return lightComponent;
    }
}
