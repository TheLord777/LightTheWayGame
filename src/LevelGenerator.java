import gamecore.components.GameComponent;
import lightTheWay.Instance;
import lightTheWay.components.environment.Level;
import processing.core.PApplet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Main game class
 */
public class LevelGenerator extends PApplet {

    Level map;

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


        int tileSize = min(width, height) / 50; // Adjust as needed
        map = new Level(width, height, tileSize);


    }


    public void draw() {
        background(0);

        map.draw();
    }


//    GAME INPUT

    /**
     * Handle key presses.
     */
    public void keyPressed() {
        // space to fire
        key = Character.toLowerCase(key);
//        if (key == 'a') ge.leftKeyDown();
//        if (key == 'd') ge.rightKeyDown();
//        if (key == 'w') ge.upKeyDown();
//        if (key == 's') ge.downKeyDown();
        if (key == '0') type = 0;
        if (key == '1') type = 1;
        if (key == '2') type = 2;
        if (key == '3') type = 3;
        if (key == '4') type = 4;
        if (key == ' ') saveMap();
        if (key == 'n') {
            int tileSize = min(width, height) / 50; // Adjust as needed
            map = new Level(width, height, tileSize);
        }

    }

    public void saveMap(){
        try {
            FileOutputStream fileOut = new FileOutputStream("map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
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





    public static void main(String... args) {
        PApplet.main("LevelGenerator");
    }
}