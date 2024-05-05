package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

public class Stalactite extends Cell {
    private float baseWidth;
    private float height;


    public Stalactite(PVector p, float baseWidth, float height) {
        super(p, baseWidth, height);
        this.baseWidth = baseWidth;
        this.height = height;
    }

    @Override
    public void draw() {

        app.pushStyle();
        // Draw triangle-shaped stalactite
        float x1 = getP().x;
        float y1 = getP().y;
        float x2 = getP().x + baseWidth / 2;
        float y2 = getP().y + height;
        float x3 = getP().x + baseWidth;
        float y3 = getP().y;

        // Draw the triangle
        app.fill(255); // White color, you can change this
        app.triangle(x1, y1, x2, y2, x3, y3);


        app.popStyle();
    }


    @Override
    public boolean intersection(GameComponent ge) {
        // Implement intersection logic if needed
        return super.intersection(ge);
    }

}
