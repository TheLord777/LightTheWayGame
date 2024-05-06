import gamecore.components.GameComponent;
import lightTheWay.Instance;
import lightTheWay.components.environment.ItemType;
import lightTheWay.components.environment.Level;
import processing.core.PApplet;
import processing.core.PConstants;

import java.io.*;


/**
 * Main game class
 */
public class LevelGenerator extends PApplet {

    static Level map;

    int type = 0;
    int itemType = 0;
    float typeAlphaValue = 254;

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

        if (type == 5 && typeAlphaValue > 0) {
            PApplet app = Instance.getApp();
            app.textSize(24);
            app.fill(255, typeAlphaValue);
            typeAlphaValue -= (255 - typeAlphaValue) / 10;
            app.textAlign(PConstants.LEFT, PConstants.TOP);
            String typeString;
            switch (itemType) {
                case 0:
                    typeString = "Small Refills";
                    break;
                case 1:
                    typeString = "Medium Refills";
                    break;
                case 2:
                    typeString = "Large Refills";
                    break;
                case 3:
                    typeString = "Full Refills";
                    break;
                case 4:
                    typeString = "Keys";
                    break;
                case 5:
                    typeString = "Torches";
                    break;
                case 6:
                    typeString = "Bonfires";
                    break;
                default:
                    typeString = "Nothing";
                    return;
            }
            app.text("Placing chests containing: " + typeString, 10, 10);
        }
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
        if (key == '5') {  // chest
            if (type == 5) {
                itemType++;
                if (itemType >= 7) itemType = 0;
            } else {
                type = 5;
            }
            typeAlphaValue = 254;
        }
        if (key == '6') type = 6;
        if (key == '7') type = 7;
        if (key == 's') type = 12; // spawn
        if (key == 'g') type = 20; // goal
        if (key == '8') type = 8;
        if (key == '9') type = 9;
        if (key == 'c') type = 10;
        if (key == ' ') saveMap();
        if (key == 'o') type = 12;
        if (key == 'n') {
            int tileSize = min(width, height) / 50; // Adjust as needed
            map = new Level(width, height, tileSize);
        }

    }

    public void saveMap() {
        try {
            map.setDev(false);
            FileOutputStream fileOut = new FileOutputStream("levels/map.ser");
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
        // Cell cell = map.edit(mouseX, mouseY, type);
        if (type == 5) {
            switch (itemType) {
                case 0:
                    // chest.setContent(ItemType.SMALL_REFILL);
                    map.edit(mouseX, mouseY, type, ItemType.SMALL_REFILL);
                    break;
                case 1:
                    // chest.setContent(ItemType.MEDIUM_REFILL);
                    map.edit(mouseX, mouseY, type, ItemType.MEDIUM_REFILL);
                    break;
                case 2:
                    // chest.setContent(ItemType.LARGE_REFILL);
                    map.edit(mouseX, mouseY, type, ItemType.LARGE_REFILL);
                    break;
                case 3:
                    // chest.setContent(ItemType.FULL_REFILL);
                    map.edit(mouseX, mouseY, type, ItemType.FULL_REFILL);
                    break;
                case 4:
                    // chest.setContent(ItemType.KEY);
                    map.edit(mouseX, mouseY, type, ItemType.KEY);
                    break;
                case 5:
                    // chest.setContent(ItemType.TORCH);
                    map.edit(mouseX, mouseY, type, ItemType.TORCH);
                    break;
                case 6:
                    // chest.setContent(ItemType.BONFIRE);
                    map.edit(mouseX, mouseY, type, ItemType.BONFIRE);
                    break;
                default:
                    return;
            }
        } else {
            map.edit(mouseX, mouseY, type);
        }
    }


    public void mouseDragged() {
        mousePressed();
        // map.edit(mouseX, mouseY, type);
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
