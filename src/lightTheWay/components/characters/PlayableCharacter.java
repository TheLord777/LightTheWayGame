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

    @Override
    public boolean standing() {
        Cell current = getEnvironment().getCellFromGCPosition(this);

        return super.standing() || onLadder();
    }

    private boolean onLadder() {
        Cell current = getEnvironment().getCellFromGCPosition(this);

        return current instanceof LadderCell;
    }

    @Override
    public void move() {
        if (onLadder()) {
            if (Math.abs(v.x) >= MAX_SPEED) {
                v.x = MAX_SPEED * Math.signum(v.x);
                return;
            }

            float speed = 500;
            if (left) applyForce(new PVector(-speed, 0));
            if (right) applyForce(new PVector(speed, 0));
            if (up) applyForce(new PVector(0, -speed *30));
            if (down) {
                applyForce(new PVector(0, +speed *30));
            }
        } else {
            super.move();
        }
    }

    public Cell findInteractionTarget() {
        Cell closest = null;
        float minDist = Float.MAX_VALUE;

        Cell current = getEnvironment().getCellFromGCPosition(this);

        List<Cell> neighbours = getEnvironment().getNeighbours(current);
        neighbours.add(current);
        for (Cell neighbour : neighbours) {
            if (!(neighbour instanceof TorchCell) && !(neighbour instanceof ChestCell)) continue;
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
            }
        } else if (closest instanceof ChestCell) {
            ChestCell chest = (ChestCell) closest;
            if (chest.isOpen()) {
                chest.drawItemGridUI();
            } else {
                chest.drawPrompt();
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

    public void useItem(boolean item) {
        if (item) {
            light.restore(0.5f);
        }
    }

    public void movePosition(PVector position) {
        setP(position);
    }

}
