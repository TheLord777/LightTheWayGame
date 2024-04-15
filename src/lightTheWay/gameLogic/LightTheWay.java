package lightTheWay.gameLogic;

import lightTheWay.components.environment.MapFormation;
import processing.core.PVector;

import java.io.*;

import static processing.core.PApplet.min;
import static processing.core.PApplet.print;

public class LightTheWay extends ComponentManager {

    public LightTheWay() {
        super();
    }


    @Override
    protected void gameLoop() {
        app.fill(255);
        app.text("fps" + app.frameRate, 50, 10);
    }

    @Override
    public void mousePressed() {
    }

    @Override
    public void spaceKey() {
        try {
            FileOutputStream fileOut = new FileOutputStream("map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mapFormation);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        setupGame();


    }

    @Override
    public void rightKeyDown() {


        animationEngine.removeAllComponents();
        animationEngine.addComponent(mapFormation);
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
