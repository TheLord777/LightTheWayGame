package lightTheWay.components.characters;

import gamecore.components.CollisionShape;
import gamecore.components.DynamicComponent;
import gamecore.components.GameComponent;
import processing.core.PVector;

import static lightTheWay.GameConfig.MAX_SPEED;

public class Character extends DynamicComponent {

    boolean left = false, right = false, up = false, down = false, sprint = false;


    protected Character(PVector p, float width) {
        super(p, width);
        this.setShape(CollisionShape.CIRCLE);
    }

    @Override
    protected float mass() {
        return width;
    }

    @Override
    protected void draw() {
        app.fill(255, 0, 0);
        app.ellipse(p.x, p.y, width, width);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }


    @Override
    public void update() {
        if (p.y < (float) app.height /2) {
            applyGravity();
        } else {
            v.y =0;

        }
        move();

        super.update();
    }

    private void move() {
        if (Math.abs(v.x) >= MAX_SPEED * 2)
            return;

        float speed = 20;

        if (p.y < (float) app.height /2) speed = 5;

        if (left) applyForce(new PVector(-speed, 0));
        if (right)
            applyForce(new PVector(speed, 0));
        if (up && Math.abs(v.y) == 0) applyForce(new PVector(0, -speed * 20));
        if (down) applyForce(new PVector(0, 0.1f));
        if (!left && !right) {
            if (v.x < 0) applyForce(new PVector(speed, 0));
            else applyForce(new PVector(-speed, 0));
        }

        if (v.y < 0) applyGravity();
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }


}
