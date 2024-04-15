package lightTheWay.components;

import gamecore.components.GameComponent;

public class MapSquare extends GameComponent {
    private int type;

    public MapSquare(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void draw() {

    }

    @Override
    protected void update() {

    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }
}