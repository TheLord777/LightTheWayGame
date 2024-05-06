package lightTheWay.components.environment;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Cell extends GameComponent {

    protected boolean isSpawnCell;

    public Cell(PVector p, float width, float height) {
        super(p, width, height);
        this.collisionShape = CollisionShape.RECTANGLE;
        this.isSpawnCell = true;

    }

    @Override
    protected void update() {
        // Update logic for MapSquare goes here
    }

    public PVector getClosestPoint(PVector p) {
        float x = PApplet.constrain(p.x, this.p.x, this.p.x + width);
        float y = PApplet.constrain(p.y, this.p.y, this.p.y + height);
        return new PVector(x, y);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Intersection logic for MapSquare goes here
        return false; // Placeholder, implement actual logic as needed
    }

    public void setSize(float size) {
        this.setWidth(size);
        this.setHeight(size);
    }

    public boolean isEmpty() {
        return this instanceof EmptyCell;
    }

    public boolean isWall() {
        return this instanceof WallCell;
    }


    public boolean isLadder() {
        return this instanceof LadderCell;
    }


    public boolean isRope() {
        return this instanceof LockCell;
    }

    public boolean isWater() {
        return this instanceof WaterCell;
    }

    // Getter method for isSpawnCell
    public boolean isSpawnCell() {
        return isSpawnCell;
    }

    // Setter method for isSpawnCell
    public void setSpawnCell(boolean spawnCell) {
        isSpawnCell = spawnCell;
    }

    public static Cell createIntialCell(PVector p, float width, float height) {
        return Math.random() < .35 ? new EmptyCell(p, width, height) : new WallCell(p, width, height);
    }

    public static Cell cellFromType(Cell oldCell, int t, Level level, ItemType itemType) {
        return switch (t) {
            case 0 -> new EmptyCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 1 -> new WallCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 2 -> new WaterCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 3 -> new LockCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 4 -> new LadderCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 5 -> new ChestCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight(), itemType);
            case 6 -> new CampCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 7 -> new SpawnCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 8 -> new TorchCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 9 -> new Stalactite(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 20 -> new GoalCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            case 12 -> new DoorCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
            default -> new EmptyCell(oldCell.getP(), oldCell.getWidth(), oldCell.getHeight());
        };
    }

}
