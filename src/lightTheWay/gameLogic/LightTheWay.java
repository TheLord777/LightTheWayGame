package lightTheWay.gameLogic;

import lightTheWay.components.ExampleComponent;
import lightTheWay.components.MapFormation;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.min;
import static processing.core.PApplet.print;

public class LightTheWay extends ComponentManager {

    public LightTheWay() {
        super();
    }


    @Override
    protected void gameLoop() {

    }

    @Override
    public void mousePressed() {

    }

    @Override
    public void spaceKey() {
        int tileSize = min(app.width, app.height) / 50; // Adjust as needed

        // Create a new instance of MapFormation
        MapFormation mapFormation = new MapFormation(new PVector(0, 0), app.width, app.height, tileSize);
        animationEngine.addComponent(mapFormation);


    }

    @Override
    public void rightKeyDown() {

    }

    @Override
    public void leftKeyDown() {

    }

    @Override
    public void upKeyDown() {

    }

    @Override
    public void downKeyDown() {

    }

    @Override
    public void rightKeyUp() {

    }

    @Override
    public void leftKeyUp() {

    }

    @Override
    public void upKeyUp() {

    }

    @Override
    public void downKeyUp() {

    }
}
