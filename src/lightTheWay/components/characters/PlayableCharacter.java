package lightTheWay.components.characters;

import java.util.List;

import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.*;
import processing.core.PConstants;
import processing.core.PVector;

import static lightTheWay.GameConfig.MAX_SPEED;

public class PlayableCharacter extends Character {

    private LightComponent light;


    public PlayableCharacter(PVector p, float width, Level l) {
        super(p, width, l);
    }

    @Override
    public void update() {
        super.update();

        // Handle state with special tiles
        Cell current = getEnvironment().getCellFromGCPosition(this);
        if (current instanceof CampCell) {
            light.reignite();
        }

        showInteractions();
    }

    public LightComponent createLight(float l) {
        // light = new LightComponent(p, l, 5);
        light = new LightComponent(p, l, 0);
        light.setBurnTime(60);
        return light;
    }

    public LightComponent getLight() {
        return light;
    }

    @Override
    public void draw() {
        super.draw();
    }


    public Cell findInteractionTarget() {
        Cell closest = null;
        float minDist = Float.MAX_VALUE;

        Cell current = getEnvironment().getCellFromGCPosition(this);

        List<Cell> neighbours = getEnvironment().getNeighbours(current);
        neighbours.add(current);
        for (Cell neighbour : neighbours) {
            if (!(neighbour instanceof TorchCell)) continue;
            PVector p = neighbour.getClosestPoint(this.getP());
            float d = PVector.dist(p, this.getP());
            if (d < minDist) {
                minDist = d;
                closest = neighbour;
            }
        }

        return closest;
    }

    public void showInteractions() {
        // Handle state with special tiles
        Cell closest = findInteractionTarget();

        if (closest == null) {
            return;
        } else if (closest instanceof TorchCell) {
            TorchCell torch = (TorchCell) closest;
            if (!torch.getIgnited()) {
                torch.drawPrompt();
            } else if (closest instanceof ChestCell) {
                ChestCell chest = (ChestCell) closest;
                if (!chest.isOpen()) {
                    // If chest is open, draw the item grid
                    chest.openChest();
                }
            }
        }
    }

    public void interact() {
        // Handle state with special tiles
        Cell closest = findInteractionTarget();

        if (closest == null) {
            return;
        } else if (closest instanceof TorchCell) {
            TorchCell torch = (TorchCell) closest;
            if (!torch.getIgnited()) torch.ignite();
        } else if (closest instanceof ChestCell) {
            ChestCell chest = (ChestCell) closest;
            if (!chest.isOpen()) {
                chest.openChest(); // Or any other action you want to perform when interacting with the chest
            } else {
                chest.closeChest(); // Or any other action for closing the chest
            }
        }
    }

}
