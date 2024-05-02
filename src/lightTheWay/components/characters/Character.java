package lightTheWay.components.characters;

import gamecore.components.CollisionShape;
import gamecore.components.DynamicComponent;
import gamecore.components.GameComponent;
import lightTheWay.GameConfig;
import lightTheWay.components.environment.Level;
import lightTheWay.components.environment.Cell;
import processing.core.PVector;


import static lightTheWay.GameConfig.MAX_SPEED;

public class Character extends DynamicComponent {

    boolean left = false, right = false, up = false, down = false, sprint = false;
    private Level environment;
    private CharacterState state;


    protected Character(PVector p, float width, Level l) {
        super(p, width);
        this.setShape(CollisionShape.CIRCLE);
        this.environment = l;
        this.state = CharacterState.IDLE;
    }

    @Override
    protected float mass() {
        return width * 100;
    }

    @Override
    public void draw() {
        app.fill(255, 0, 0);
        app.ellipse(p.x, p.y, width, width);
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false;
    }


    @Override
    public void update() {
        if (!standing()) applyGravity();
        else v.y = 0;

        move();
        super.update();

        if (headHit()) v.y = 0;
        if (leftHit() || rightHit()) v.x = 0;

        fixClipping();

//        if (!environment.intersection(this)) return;
//
//
//        // If there was a collision, move the character back to the closest point on the map.
//        PVector cp = environment.getClosestPoint(this);
//
//        if (cp == null) return;
//
//        // Calculate the vector from the closest point to the character.
//        PVector v1 = PVector.sub(p, cp).setMag(width / 2);
//
//        // Set the position of the character to the closest point on the map without clipping.
//        p.set(cp.add(v1));
    }

    private void fixClipping() {

    }

    public void move() {
        int tileSize = environment.getTileSize();
        float speed = 500;

        if (tileSize != 0) {
            speed = 500*(tileSize / 22.0f); // Adjust speed based on tileSize
        }
        if (Math.abs(v.x) >= MAX_SPEED) {
            v.x = MAX_SPEED * Math.signum(v.x);
            return;
        }
        if (Math.abs(v.y) >= MAX_SPEED) {
            v.y = MAX_SPEED * Math.signum(v.y);
            return;
        }

//        if (!standing()) speed = 5;

        if (left) {
            if (climbing()) applyForce(new PVector(-speed / 4, 0));
            else applyForce(new PVector(-speed, 0));
        }
        if (right){
            if (climbing()) applyForce(new PVector(speed / 4, 0));
            else applyForce(new PVector(speed, 0));
        }

        if (up){
            if (climbing()) climb();
            else if (standing()) jump();
        }

//        if (down) applyForce(new PVector(0, 0.1f));
        if (!left && !right && standing()) {
            if (v.x < 0) applyForce(new PVector(speed, 0));
            else applyForce(new PVector(-speed, 0));

            if (Math.abs(v.x) < 1) v.x = 0;
        }

    }


    private void jump(){
        applyForce(new PVector(0, -GameConfig.JUMP_FORCE));
        setState(CharacterState.JUMPING);
    }

    private void climb(){
        if (state != CharacterState.CLIMBING) v.x /=4;
        v.y = -2;
//        applyForce(new PVector(0, -GameConfig.CHARACTER_A * 2));
        setState(CharacterState.CLIMBING);
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

    public boolean standing() {
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y + width / 2));
        return c.isWall();
    }

    private boolean climbing() {
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y));
        return c.isLadder() || c.isRope();
    }

    private boolean headHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y - width / 2));
        return c.isWall();
    }

    private boolean leftHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x - width / 2, p.y));
        return c.isWall();
    }

    private boolean rightHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x + width / 2, p.y));
        return c.isWall();
    }


    private void setState(CharacterState state){
        this.state = state;
    }

    public Level getEnvironment() {
        return environment;
    }
}
