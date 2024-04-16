package lightTheWay.gameLogic;

import gamecore.engine.GameEngine;
import lightTheWay.Instance;
import lightTheWay.components.environment.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static processing.core.PApplet.*;

public abstract class ComponentManager extends GameEngine {
    Level level;

    protected ComponentManager() {
        super(Instance.getApp(), Collisions.getInstance());
    }

    @Override
    public void setupGame() {
        // Initialize tileSize
        animationEngine.removeAllComponents();

        int tileSize = min(app.width, app.height) / 50; // Adjust as needed

        // Create a new instance of MapFormation
        getMapFormation("map.ser");
        animationEngine.addComponent(level);

    }


    public void getMapFormation(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            level = (Level) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("MapFormation class not found");
            c.printStackTrace();
            return;
        }
    }


}
