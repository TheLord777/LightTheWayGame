package lightTheWay.components.characters;

import lightTheWay.components.environment.Cell;
import lightTheWay.components.environment.Level;
import lightTheWay.gameLogic.Collisions;
import processing.core.PVector;

public class AICharacter extends Character {
    private Character target;

    public AICharacter(PVector p, float width, Level l, Character hero) {
        super(p, width, l);
        setLeft(true);
        target = hero;
    }


    @Override
    public void move() {

         if (v.x == 0){
            left = !left;
            right = !right;
        } else if (onEdgeRight()){
            right = false;
            left = true;
        }else if (onEdgeleft()){
            right = true;
            left = false;
        }

         if (canSeeHero()){
            if (target.getX() < p.x){
                left = true;
                right = false;
            } else {
                right = true;
                left = false;
            }
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
    public void update() {
        super.update();
    }

    private boolean onEdge(){
        Cell c1 = environment.getCellFromPoint(new PVector(p.x + (width / 2), p.y + (width / 2)));
        Cell c2 = environment.getCellFromPoint(new PVector(p.x - (width / 2), p.y + (width / 2)));

        return c1.isEmpty() || c2.isEmpty();
    }

    private boolean onEdgeRight(){
       return environment.getCellFromPoint(new PVector(p.x + (width / 2), p.y + (width / 2))).isEmpty();
    }

    private boolean onEdgeleft(){
        return environment.getCellFromPoint(new PVector(p.x - (width / 2), p.y + (width / 2))).isEmpty();
    }

    @Override
    public void draw() {
        app.fill(0,255,0);
        app.circle(p.x, p.y, width);
    }

    private boolean canSeeHero(){
        if (p.y == target.getP().y){
            int inc = Integer.signum((int) (target.getP().x - p.x));
            int dif = (int) Math.abs(p.x - target.getP().x);
            int i = 0;
            while (Math.abs(i) < Math.abs(dif)){
                i += inc;
               if (environment.getCellFromPoint(new PVector(p.x + i, p.y)).isWall()) return false;
            }
            return true;

        }
        return false;
    }
}
