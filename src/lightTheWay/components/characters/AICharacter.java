package lightTheWay.components.characters;

import lightTheWay.components.environment.Level;
import processing.core.PVector;

public class AICharacter extends Character {
    public AICharacter(PVector p, float width, Level l) {
        super(p, width, l);
        setLeft(true);
    }


    @Override
    public void move() {
        if (v.x == 0){
            left = !left;
            right = !right;
        }


        super.move();
    }

    @Override
    protected float getSpeed() {
        return .5f;
    }

    @Override
    protected float getMaxSpeed() {
        return .5f;
    }

    @Override
    public void draw() {
        app.fill(0,255,0);
        app.circle(p.x, p.y, width);
    }
}
