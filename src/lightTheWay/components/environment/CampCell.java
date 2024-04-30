package lightTheWay.components.environment;

import lightTheWay.components.CampComponent;
import lightTheWay.components.LightComponent;
import processing.core.PVector;

public class CampCell extends Cell {
    private CampComponent campComponent;
    private LightComponent lightComponent;

    public CampCell(PVector p, float width, float height) {
        super(p, width, height);
        // Initialize CampComponent with position adjusted to the center of the cell
        float componentX = p.x + width / 2;
        float componentY = p.y + height / 2;
        campComponent = new CampComponent(componentX, componentY, 250);
        lightComponent = new LightComponent(componentX, componentY, 250);
    }

    @Override
    public void draw() {
        // Draw other elements of the cell if needed

        // Draw the camp component
        campComponent.draw();
        lightComponent.draw();
    }
}
