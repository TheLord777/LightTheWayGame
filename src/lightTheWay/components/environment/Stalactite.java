package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

import java.util.ArrayList;

public class Stalactite extends Cell {
    private float baseWidth;
    private float height;
    private float dropletSize;
    private int dropletInterval; // Interval in milliseconds
    private int lastDropletTime; // Time of the last droplet drop

    private ArrayList<Droplet> droplets;
    private Level level;


    public Stalactite(Level level, PVector p, float baseWidth, float height, float dropletSize, int dropletInterval) {
        super(p, baseWidth, height);
        this.baseWidth = baseWidth;
        this.height = height;
        this.dropletSize = dropletSize;
        this.dropletInterval = dropletInterval;
        this.lastDropletTime = 0; // Initialize with 0 to drop first droplet immediately
        this.droplets = new ArrayList<>(); // Initialize the ArrayList
        this.level = level;

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

        // Drop droplets at regular intervals
        int currentTime = app.millis();
        if (currentTime - lastDropletTime >= dropletInterval) {
            // Create and add a new water droplet
            float dropletX = app.random(x1, x3);
            float dropletY = app.random(y1, y2);
            double gravity = 9.8;

            Droplet droplet = new Droplet(level,this,new PVector(dropletX, dropletY), gravity, dropletSize);
            droplets.add(droplet);

            // Update last droplet time
            lastDropletTime = currentTime;
        }

        // Update and draw existing droplets
        for (int i = droplets.size() - 1; i >= 0; i--) {
            Droplet droplet = droplets.get(i);
            droplet.update();
            droplet.draw();
        }

        app.popStyle();
    }

    public void removeDroplet(Droplet droplet) {
        droplets.remove(droplet);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Implement intersection logic if needed
        return super.intersection(ge);
    }

    public ArrayList<Droplet> getDroplets() {
        return droplets;
    }
}
