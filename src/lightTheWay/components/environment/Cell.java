package lightTheWay.components.environment;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

public class Cell extends GameComponent {

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
    protected void draw() {
        switch (type) {
            case EMPTY:
                break;
            case WALL:
                drawRock();
                break;
            case LADDER:
                drawLadder();
                break;
            case ROPE:
                drawRope();
                break;
            case WATER:
                drawWater();
                break;
            case CHEST:
                drawChest();
                break;
            case ENEMYSPAWN:
                spawnType = 1;
                setSpawnType(spawnType);
                break;
            case PLAYERSPAWN:
                spawnType = 2;
                setSpawnType(spawnType);
                break;
        }
    }


    @Override
    protected void update() {
        // Update logic for MapSquare goes here
    }

    @Override
    public boolean intersection(GameComponent ge) {
        // Intersection logic for MapSquare goes here
        return false; // Placeholder, implement actual logic as needed
    }

    public CellType getType() {
        return type;
    }

    public void setType(int type) {
        this.type = CellType.fromInt(type);
    }

    private void drawLadder() {
        LadderCell ladder = new LadderCell(new PVector(p.x, p.y), width, height);
        ladder.draw();
    }

    private void drawRope() {
        RopeCell rope = new RopeCell(new PVector(p.x, p.y), width, height);
        rope.draw();
    }


    private void drawWater() {
        WaterCell water = new WaterCell(new PVector(p.x, p.y), width, height);
        water.draw();
    }

    private void drawChest() {
        // Create and draw a ChestCell instance
        ChestCell chest = new ChestCell(new PVector(p.x, p.y), width, height, false);
        chest.draw();
    }

    private void drawRock(){
        WallCell wall = new WallCell(new PVector(p.x, p.y), width, height);
        wall.draw();
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
}
