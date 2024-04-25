package lightTheWay.components.environment;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Cell extends GameComponent {

    private CellType type;
    private int spawnType ;
    public Cell(PVector p, float width, float height) {
        super(p, width, height);
        this.type = Math.random() < .4 ? CellType.EMPTY : CellType.WALL;
        this.collisionShape = CollisionShape.RECTANGLE;
    }

    public Cell(PVector p, float width, float height, int type) {
        super(p, width, height);
        this.type = CellType.fromInt(type);
        this.collisionShape = CollisionShape.RECTANGLE;
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

    // Additional methods specific to MapSquare can be added here

    public CellType getType() {
        return type;
    }

    public void setType(int type) {
        this.type = CellType.fromInt(type);
    }


    private void setSpawnType(int spawnType){
        if(spawnType == 1){
            //spawn enemies
        }else if (spawnType == 2){
            //spawn Player
        }
    }

    public boolean isEmpty() {
        return type == CellType.EMPTY;
    }

    public boolean isWall() {
        return type == CellType.WALL;
    }


    public boolean isLadder() {
        return type == CellType.LADDER;
    }


    public boolean isRope() {
        return type == CellType.ROPE;
    }

    public boolean isWater() {
        return type == CellType.WATER;
    }

    public static Cell createIntialCell(PVector p, float width, float height){
        return Math.random() < .35 ? new EmptyCell(p, width, height) : new WallCell(p, width, height);
    }
}
