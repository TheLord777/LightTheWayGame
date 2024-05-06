package lightTheWay.components.characters;

import java.util.List;

import lightTheWay.Instance;
import lightTheWay.components.LightComponent;
import lightTheWay.components.environment.*;
import processing.core.PVector;

import static lightTheWay.GameConfig.MAX_SPEED;

public class PlayableCharacter extends Character {

    public ItemType[] inventory = {ItemType.NO_ITEM, ItemType.NO_ITEM, ItemType.NO_ITEM, ItemType.NO_ITEM, ItemType.NO_ITEM, ItemType.NO_ITEM};
    private LightComponent light;
    private int lastDamageTime = 0;
    private int minimumDamageBuffer = 1000;

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
        // Cell current = getEnvironment().getCellFromGCPosition(this);

        return super.standing() || onLadder();
    }

    @Override
    protected float getSpeed() {
        return 500;
    }

    @Override
    protected float getMaxSpeed() {
        return 5;
    }

    private boolean onLadder() {
        Cell current = getEnvironment().getCellFromGCPosition(this);

        return current instanceof LadderCell;
    }

    @Override
    public void move() {
        if (outOfLight()) {
            return;
        }
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
            if (!(neighbour instanceof TorchCell) && !(neighbour instanceof ChestCell) && !(neighbour instanceof LockCell)) continue;
            if (neighbour instanceof ChestCell) {
                ChestCell chest = (ChestCell) neighbour;
                if (chest.isOpen()) continue;
            }
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
            if (!chest.isOpen()) {
                chest.drawPrompt();
            }
        } else if (closest instanceof LockCell) {
            LockCell lock = (LockCell) closest;
            if (getNextKeySlot() >= 0) {
                lock.drawPrompt();
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
            int nextSlot = getNextEmptySlot();
            if (!chest.isOpen() && nextSlot >= 0) {
                inventory[nextSlot] = chest.openChest(); // Opens the chest
                System.out.println(inventory[nextSlot]);
            }
        } else if (closest instanceof LockCell) {
            int nextSlot = getNextKeySlot();
            if (nextSlot >= 0) {
                getEnvironment().edit((int) closest.getP().x, (int) closest.getP().y, 0);
                inventory[nextSlot] = ItemType.NO_ITEM;
            }
        }
    }

    public int getNextKeySlot() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == ItemType.KEY) {
                return i;
            }
        }
        return -1;
    }

    public int getNextEmptySlot() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == ItemType.NO_ITEM) {
                return i;
            }
        }
        return -1;
    }
    public boolean useItem(int slotNumber) {
        ItemType item = inventory[slotNumber];
        switch (item) {
            case SMALL_REFILL:
                light.restore(0.1f);
                inventory[slotNumber] = ItemType.NO_ITEM;
                return true;
            case MEDIUM_REFILL:
                light.restore(0.25f);
                inventory[slotNumber] = ItemType.NO_ITEM;
                return true;
            case LARGE_REFILL:
                light.restore(0.5f);
                inventory[slotNumber] = ItemType.NO_ITEM;
                return true;
            case FULL_REFILL:
                light.reignite();
                inventory[slotNumber] = ItemType.NO_ITEM;
                return true;
            case KEY:
                return false;
            case TORCH:
                if (getEnvironment().getCellFromGCPosition(this) instanceof EmptyCell) {
                    getEnvironment().edit((int) p.x, (int) p.y, 8);
                    inventory[slotNumber] = ItemType.NO_ITEM;
                    return true;
                } else {
                    return false;
                }
            case BONFIRE:
                if (getEnvironment().getCellFromGCPosition(this) instanceof EmptyCell && standing()) {
                    getEnvironment().edit((int) p.x, (int) p.y, 6);
                    inventory[slotNumber] = ItemType.NO_ITEM;
                    return true;
                } else {
                    return false;
                }
            case NO_ITEM:
                return true;
        }
        return false;
    }

    public void movePosition(PVector position) {
        setP(position);
        light.setP(position);
    }

    public boolean outOfLight() {
        return !light.isBurning();
    }

    /**
     * Decrements the size of the player light by a percentage (given in decimal) of its default size
     */
    public void damage(float f) {
        int currentTime = Instance.getApp().millis();
        if (Instance.getApp().millis() >= lastDamageTime + minimumDamageBuffer) {
            light.decrementLightPercentage(f);
            lastDamageTime = currentTime;
        }
    }

}
