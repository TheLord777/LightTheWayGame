package lightTheWay.components.environment;

import lightTheWay.components.CampComponent;
import lightTheWay.components.LightComponent;
import processing.core.PConstants;
import processing.core.PVector;

public class TorchCell extends Cell {
    private static final long serialVersionUID = 1L;

    private float promptHeight = p.y - height * 1.5f;
    private boolean promptDirection = false; // true -> upwards, false -> downwards
    private boolean ignited = false;
    private LightComponent torchLight; // LightComponent for the torch flame
    // private WallCell wallcell;

    public TorchCell(PVector p, float width, float height) {
        super(p, width, height);

        // Initialize the torch light at the center of the cell
        float lightX = p.x + width / 2;
        float lightY = p.y + height / 2;
        torchLight = new LightComponent(lightX, lightY, 0, 0);
        // wallcell = new WallCell(p, width, height);
    }

    @Override
    public void draw() {
        app.pushStyle();
        // // Draw the wall cell
        // wallcell.draw();

        // Draw the torch handle
        float handleWidth = width / 4; // Adjust handle width as needed
        float handleHeight = height ; // Adjust handle height as needed
        float handleX = p.x + (width - handleWidth) / 2; // Center the handle horizontally
        float handleY = p.y + (height - handleHeight) / 2; // Center the handle vertically
        app.fill(139, 69, 19); // Brown color for torch handle
        app.rect(handleX, handleY, handleWidth, handleHeight);

        // Draw the torch light
        if (ignited) torchLight.draw(true);
        app.popStyle();
    }

    public boolean getIgnited() {
        return ignited;
    }

    public void ignite() {
        ignited = true;
        torchLight.setLightSize(width * 10);
    }

    public LightComponent getLightComponent() {
        return torchLight;
    }

    public void drawPrompt() {
        app.fill(200, 200, 200);
        app.rect(p.x, promptHeight, width, height);
        app.fill(100, 100, 100);
        app.textSize(width);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text("F", p.x + width / 2, promptHeight + height / 2);
        updatePromptPosition();
    }

    public PVector getPromptPosition() {
        return new PVector(p.x, promptHeight);
    }

    public void updatePromptPosition() {
        if (promptDirection) {
            promptHeight -= height * 0.02f;
            if (promptHeight <= p.y - height * 1.5f) {
                promptDirection = false;
                promptHeight = p.y - height * 1.5f;
            }
        } else {
            promptHeight += height * 0.02f;
            if (promptHeight >= p.y - height * 1.1f) {
                promptDirection = true;
                promptHeight = p.y - height * 1.1f;
            }
        }
    }

    @Override
    public void setP(PVector v) {
        super.setP(v);
        float lightX = p.x + width / 2;
        float lightY = p.y + height / 2;
        torchLight = new LightComponent(lightX, lightY, 0, 0);
    }

}
