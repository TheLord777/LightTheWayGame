package lightTheWay.components.environment;

import lightTheWay.Instance;
import processing.core.PApplet;
import processing.core.PVector;

public class EmptyCell extends Cell{
    private static final long serialVersionUID = 1L;

    int random = (int) Math.floor(Math.random() * 3);
    float seed = (float) Math.random() / 2 + 0.25f;
    DecorType decor = DecorType.NO_DECOR;

    public EmptyCell(PVector p, float width, float height) {
        super(p, width, height);
    }

    public void setRandomFloorDecor() {
        if (decor != DecorType.NO_DECOR) return;
        int decorRandom = (int) Math.floor(Math.random() * 12);
        switch (decorRandom) {
            case 0:
                decor = DecorType.MUSHROOM;
                break;
            case 1:
                decor = DecorType.ROCK;
                break;
        }
    }

    public void setRandomCarving() {
        if (decor != DecorType.NO_DECOR) return;
        int decorRandom = (int) Math.floor(Math.random() * 24);
        if (decorRandom == 2) {
            decor = DecorType.CARVING;
        }
    }

    @Override
    public void draw() {
        PApplet app = Instance.getApp();
        float positionX = p.x + (width * seed);
        float positionY = p.y + (height * seed);
        switch (decor) {
            case MUSHROOM:
                app.pushStyle();
                app.fill(200, 200, 200);
                app.rect(positionX - width / 16, p.y + 3 * height / 4, width / 8, height / 4);
                switch (random) {
                    case 0:
                        app.fill(50, 200, 50);
                        break;
                    case 1:
                        app.fill(50, 50, 200);
                        break;
                    case 2:
                        app.fill(200, 50, 50);
                        break;
                    default:
                        app.fill(165, 132, 132);
                        break;
                }
                app.ellipse(positionX, p.y + 3 * height / 4, width / 3, height / 4);
                app.popStyle();
                break;
            case ROCK:
                app.pushStyle();
                app.fill(180, 180, 180);
                app.quad(positionX - width / 8, p.y + height, 
                        positionX + width / 8, p.y + height, 
                        positionX + width / 24, p.y + height - height / (3 * (random + 1)), 
                        positionX - width / 24, p.y + height - height / (3 * (random + 1)));
                app.popStyle();
                break;
            case CARVING:
                app.pushStyle();
                switch (random) {
                    case 0: // stickman
                        app.fill(100, 100, 100);
                        app.circle(positionX, p.y + height / 2, width / 3);
                        app.stroke(100, 100, 100);
                        app.strokeWeight(width / 16);
                        app.line(positionX, p.y + height / 2, positionX + width / 4, p.y + 3 * height / 4);
                        app.line(positionX + width / 8 + width / 8, p.y + height / 2 + width / 8, positionX + width / 8, p.y + height / 2 + width / 8 + height / 8);
                        app.line(positionX + width / 4, p.y + 3 * height / 4, positionX + width / 4, p.y + 3 * height / 4 + height / 8);
                        app.line(positionX + width / 4, p.y + 3 * height / 4, positionX + width / 4 + width / 8, p.y + 3 * height / 4);
                        break;
                    case 1: // sun
                        app.fill(100, 100, 100);
                        app.circle(positionX, positionY, width / 3);
                        app.stroke(100, 100, 100);
                        app.strokeWeight(width / 16);
                        app.line(positionX + width / 6, positionY + height / 6, positionX + width / 4, positionY + height / 4);
                        app.line(positionX + width / 6, positionY - height / 6, positionX + width / 4, positionY - height / 4);
                        app.line(positionX - width / 6, positionY - height / 6, positionX - width / 4, positionY - height / 4);
                        app.line(positionX - width / 6, positionY + height / 6, positionX - width / 4, positionY + height / 4);
                        app.line(positionX, positionY + height / 6, positionX, positionY + height / 4);
                        app.line(positionX, positionY - height / 6, positionX, positionY - height / 4);
                        app.line(positionX + width / 6, positionY, positionX + width / 4, positionY);
                        app.line(positionX - width / 6, positionY, positionX - width / 4, positionY);
                        break;
                    case 2: // tally marks
                        app.stroke(100, 100, 100);
                        app.strokeWeight(width / 16);
                        app.line(positionX - width / 4, positionY + height / 6, positionX + width / 4, positionY - height / 6);
                        app.line(positionX - width / 6, positionY - height / 6, positionX - width / 6, positionY + height / 6);
                        app.line(positionX - width / 16, positionY - height / 6, positionX - width / 16, positionY + height / 6);
                        app.line(positionX + width / 16, positionY - height / 6, positionX + width / 16, positionY + height / 6);
                        app.line(positionX + width / 6, positionY - height / 6, positionX + width / 6, positionY + height / 6);
                        break;
                    default: // nothing
                        break;
                }
                app.popStyle();
                break;
            default:
                break;
        }
        if (isGoalCell) {
            app.pushStyle();
            app.fill(0, 255, 0);
            app.triangle(p.x + width / 2, p.y, p.x + width, p.y + height / 4, p.x + width / 2, p.y + height / 2);
            app.stroke(200, 200, 200);
            app.strokeWeight(width / 8);
            app.line(p.x + width / 2, p.y + height / 16, p.x + width / 2, p.y + 15 * height / 16);
            app.popStyle();
        }
    }
}
