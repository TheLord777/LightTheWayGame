package lightTheWay.components.characters;

import gamecore.components.CollisionShape;
import gamecore.components.DynamicComponent;
import gamecore.components.GameComponent;
import lightTheWay.GameConfig;
import lightTheWay.components.environment.Level;
import lightTheWay.components.environment.Cell;
import processing.core.PVector;


import static lightTheWay.GameConfig.MAX_SPEED;

public abstract class Character extends DynamicComponent {

    protected boolean left = false, right = false, up = false, down = false, sprint = false, alive;
    protected Level environment;
    protected CharacterState state;


    protected Character(PVector p, float width, Level l) {
        super(p, width * .75f);
        this.setShape(CollisionShape.CIRCLE);
        this.environment = l;
        this.state = CharacterState.IDLE;
        this.alive = false;
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
        if (!standing() || climbing()) applyGravity();

        super.update();

        move();

        fixClipping();

    }

    protected void fixClipping() {
        if (jumping()) {
            if (headHit()) v.y = 0;
        } else if (standing()) {
            if (!environment.getCellFromGCPosition(this).isLadder()) v.y = 0;
            f.y = 0;
            p.y = getStandingCell().getY() - (width / 2);
        }

        if (leftHit()) {
            Cell lc = getLeftCell();
            p.x = lc.getX() + lc.getWidth() + (width / 2);

            if (left) {
                f.x = 0;
                v.x = 0;
            }
        }
        if (rightHit()) {
            p.x = getRightCell().getX() - (width / 2);
            if (right) {
                f.x = 0;
                v.x = 0;
            }
        }

        if (headHit()) {
            Cell tc = getTopCell();
            v.y = 0;
            f.y = 0;
            p.y = tc.getY() + tc.getHeight() + (width / 2);
        }


    }

    public void move() {
        float tileSize = environment.getCellHeight();
        float speed = getSpeed();

        if (tileSize != 0) {
            speed = 500 * (tileSize / 22.0f); // Adjust speed based on tileSize
        }
        if (Math.abs(v.x) >= getMaxSpeed()) {
            v.x = getMaxSpeed() * Math.signum(v.x);
            return;
        }

        if (!left && !right && (standing() || climbing())) {
            if (v.x < 0) applyForce(new PVector(speed, 0));
            else applyForce(new PVector(-speed, 0));

            if (Math.abs(v.x) < 1) v.x = 0;
        }

        if (left) {
            if (climbing() && !standing()) v.x = -1;
            else if (!jumping()) applyForce(new PVector(-speed, 0));
            else if (v.x >= -1) v.x = -1;
        }
        if (right) {
            if (climbing() && !standing()) v.x = 1;
            else if (!jumping()) applyForce(new PVector(speed, 0));
            else if (v.x <= 1) v.x = 1;
        }

        if (!up && climbing()) v.y = 0;
        if (down & climbing()) v.y = 2;

        if (up) {
            if (climbing()) climb();
            else if (standing()) jump();
        }


        if (jumping()) {
            if (Math.abs(v.x) < 1) v.x = 0;
        }

    }


    protected void jump() {
        if (v.y != 0) return;
        applyForce(new PVector(0, -jumpForce()));
        setState(CharacterState.JUMPING);
    }

    private float jumpForce() {
        return environment.getHeight() * 7;
    }

    protected void climb() {
        if (state != CharacterState.CLIMBING) v.x /= 4;
        v.y = -2;
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
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y + (width / 2)));
        if (c.isWall()) {
            state = CharacterState.Standing;
        } else {
            state = CharacterState.JUMPING;
        }
        return c.isWall();
    }

    protected boolean climbing() {
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y));
        return c.isLadder() || c.isRope();
    }

    protected boolean jumping() {
        return state == CharacterState.JUMPING;
    }

    protected boolean headHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x, p.y - width / 2));
        return c.isWall();
    }

    protected boolean leftHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x - width / 2, p.y));
        return c.isWall();
    }

    protected Cell getLeftCell() {
        return environment.getCellFromPoint(new PVector(p.x - width / 2, p.y));
    }

    protected Cell getTopCell() {
        return environment.getCellFromPoint(new PVector(p.x, p.y - width / 2));
    }

    protected Cell getRightCell() {
        return environment.getCellFromPoint(new PVector(p.x + width / 2, p.y));
    }

    protected Cell getStandingCell() {
        return environment.getCellFromPoint(new PVector(p.x, p.y + (width / 2)));
    }

    protected boolean rightHit() {
        Cell c = environment.getCellFromPoint(new PVector(p.x + width / 2, p.y));
        return c.isWall();
    }


    protected void setState(CharacterState state) {
        this.state = state;
    }

    public Level getEnvironment() {
        return environment;
    }

    public void setEnvironment(Level l) {
        environment = l;
    }

    protected abstract float getSpeed();

    protected abstract float getMaxSpeed();


    public boolean killed() {
        return !alive;
    }

    public void kill() {
        alive = false;
    }
}
