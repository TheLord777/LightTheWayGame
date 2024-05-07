package lightTheWay.components.environment;

import processing.core.PVector;

public class DoorCell extends Cell {
    private static final long serialVersionUID = 1L;

    public DoorCell(PVector p, float width, float height) {
        super(p, width, height);
    }

    @Override
    public void draw() {
        app.pushStyle();

        // Draw the door frame
        app.fill(150, 75, 0); // Brown color for the door frame
        app.rect(p.x, p.y, width, height * 2); // Draw the main part of the door

        // Draw the door panels
        app.fill(120, 70, 0); // Darker brown color for the door panels
        float panelWidth = width / 6; // Width of each panel
        for (int i = 0; i < 3; i++) {
            app.rect(p.x + i * panelWidth * 2, p.y, panelWidth, height * 2); // Draw each panel
        }

        // Draw the door handle
        app.fill(200, 200, 200); // Silver color for the handle
        float handleSize = width / 20; // Size of the handle
        app.ellipse(p.x + width - handleSize * 2, p.y + height, handleSize*4, handleSize*4); // Draw the handle

        app.popStyle();
    }


}

