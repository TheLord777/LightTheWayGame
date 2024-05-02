import gamecore.components.GameComponent;
import lightTheWay.Instance;
import lightTheWay.components.environment.Level;
import processing.core.PApplet;

import java.io.*;


/**
 * Main game class
 */
public class LevelGenerator extends PApplet {

    static Level map;

    int type = 0;

    public void settings() {
        fullScreen();
    }


    public void setup() {
        background(0);
        noStroke();

        // Use processing built in crosshair cursor.
        cursor(CROSS);

        Instance.setApp(this);
        GameComponent.setApp(this);

        if (map == null) {
            int tileSize = min(width, height) / 50; // Adjust as needed
            map = new Level(width, height, tileSize);
        }

    }


    public void draw() {
        background(0);


        map.setDev(true);
        map.draw();
    }


//    GAME INPUT

    /**
     * Handle key presses.
     */
    public void keyPressed() {
        // space to fire
        key = Character.toLowerCase(key);
        if (key == '0') type = 0;
        if (key == '1') type = 1;
        if (key == '2') type = 2;
        if (key == '3') type = 3;
        if (key == '4') type = 4;
        if (key == '5') type = 5;
        if (key == '6') type = 6;
        if (key == 's') type = 7; // spawn
        if (key == 'g') type = 11; // goal
        if (key == '8') type = 8;
        if (key == '9') type = 9;
        if (key == 'c') type = 10;
        if (key == ' ') saveMap();
        if (key == 'n') {
            int tileSize = min(width, height) / 50; // Adjust as needed
            map = new Level(width, height, tileSize);
        }

    }

    public void saveMap() {
        try {
            map.setDev(false);
            FileOutputStream fileOut = new FileOutputStream("map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
            map.setDev(true);
        } catch (IOException i) {
            i.printStackTrace();
        }


    }

    /**
     * Handle key presses.
     */
    public void keyReleased() {
//        // space to fire
//        key = Character.toLowerCase(key);
//        if (key == 'a') ge.leftKeyUp();
//        if (key == 'd') ge.rightKeyUp();
//        if (key == 'w') ge.upKeyUp();
//        if (key == 's') ge.downKeyUp();
    }


    /**
     * Handle mouse presses.
     */
    public void mousePressed() {
        map.edit(mouseX, mouseY, type);
    }


    public void mouseDragged() {
        map.edit(mouseX, mouseY, type);
    }


    public static void main(String[] args) {
        if (args.length == 1) getMapFormation(args[0]);
        PApplet.main("LevelGenerator");
    }


    public static void getMapFormation(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (Level) in.readObject();
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