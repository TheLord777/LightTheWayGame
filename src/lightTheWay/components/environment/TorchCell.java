package lightTheWay.components.environment;

import gamecore.engine.AnimationEngine;
import lightTheWay.components.CampComponent;
import lightTheWay.components.LightComponent;
import lightTheWay.gameLogic.ComponentManager;
import processing.core.PApplet;
import processing.core.PVector;

public class TorchCell extends Cell {
    private LightComponent torchLight; // LightComponent for the torch flame
    private WallCell wallcell;

    public TorchCell(PVector p, float width, float height) {
        super(p, width, height);

        // Initialize the torch light at the center of the cell
        float lightX = p.x + width / 2;
        float lightY = p.y + height / 2;
        torchLight = new LightComponent(lightX, lightY, width * 5, 0);
        wallcell = new WallCell(p, width, height);

    }

    @Override
    public void draw() {
        app.pushStyle();
        // Draw the wall cell
        wallcell.draw();


        // Draw the torch handle
        float handleWidth = width / 4; // Adjust handle width as needed
        float handleHeight = height ; // Adjust handle height as needed
        float handleX = p.x + (width - handleWidth) / 2; // Center the handle horizontally
        float handleY = p.y + (height - handleHeight) / 2; // Center the handle vertically
        app.fill(139, 69, 19); // Brown color for torch handle
        app.rect(handleX, handleY, handleWidth, handleHeight);

        // Draw the torch light
        torchLight.draw();

        app.popStyle();
    }

    public LightComponent getLightComponent() {
        return torchLight;
    }
}
