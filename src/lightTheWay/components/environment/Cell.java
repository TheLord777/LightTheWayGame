package lightTheWay.components.environment;

import gamecore.components.CollisionShape;
import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Cell extends GameComponent {

    private int spawnType ;
    public Cell(PVector p, float width, float height) {
        super(p, width, height);
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



    private void setSpawnType(int spawnType){
        if(spawnType == 1){
            //spawn enemies
        }else if (spawnType == 2){
            //spawn Player
        }
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
        return this instanceof RopeCell;
    }

    public boolean isWater() {
        return this instanceof WaterCell;
    }

    public static Cell createIntialCell(PVector p, float width, float height){
        return Math.random() < .35 ? new EmptyCell(p, width, height) : new WallCell(p, width, height);
    }
}
