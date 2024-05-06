package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;
import lightTheWay.components.LightComponent;
import lightTheWay.gameLogic.Collisions;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class Level extends GameComponent {

    private Cell[][] map;
    private Cell playerSpawn, goal;

    private int generations = 5;
    private Background b;
    private int rows, cols;
    private boolean dev = false;

    private List<Droplet> droplets;


    public Level(float width, float height, int tileSize) {
        super(new PVector(0, 0), width, height);
        this.b = new Background(height);
        rows = (int) Math.ceil(height / tileSize);
        cols = (int) (width / tileSize);
        droplets = new ArrayList<>();
        generateMap();

    }
    private void updateTileSize() {
        float cwidth = width / cols, cheight= height / rows;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                map[i][j].setWidth(cwidth);
                map[i][j].setHeight(cheight);
                map[i][j].setP(new PVector(i * cwidth, j * cheight));
            }
        }


    }

    public void updateMap(float width, float height) {
        this.width = width;
        this.height = height;
        updateTileSize();
    }

    private void generateMap() {
        map = new Cell[cols][rows];
        float cwidth = width / cols, cheight= height / rows;

        // Initialize the map with random values
        for (int i = 0; i < cols; i++)
            for (int j = 0; j < rows; j++)
                map[i][j] = Cell.createIntialCell(new PVector(i * cwidth, j * cheight), cwidth, cheight);

        // Apply cellular automata rules to smooth the map
        for (int i = 0; i < generations; i++) applyAutomataRules();

        playerSpawn = map[0][0]; // default
        goal = map[1][1];
    }

    public void addDecor() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                EmptyCell target;
                if (j < (rows - 1) && map[i][j] instanceof EmptyCell && map[i][j + 1] instanceof EmptyCell) {
                    target = (EmptyCell) map[i][j];
                    target.setRandomCarving();
                } else if (j > 0 && map[i][j] instanceof WallCell && map[i][j - 1] instanceof EmptyCell) {
                    target = (EmptyCell) map[i][j - 1];
                    target.setRandomFloorDecor();
                }
            }
        }
    }

    private void applyAutomataRules() {
        Cell[][] newMap = new Cell[cols][rows];
        float cwidth = width / cols, cheight= height / rows;

        // Apply rules to each cell
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                int aliveNeighbors = countAliveNeighbors(i, j);

                int t;

                if (map[i][j] instanceof EmptyCell) t = aliveNeighbors >= 3 ? 1 : 0;
                else t = aliveNeighbors >= 4 ? 1 : 0;

                if (t == 0) {
                    newMap[i][j] = new WallCell(map[i][j].getP(), cwidth, cheight);
                } else {
                    newMap[i][j] = new EmptyCell(map[i][j].getP(), cwidth, cheight);
                }
            }
        }

        // Update the map
        map = newMap;
    }


    private int countAliveNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < map.length && neighborY >= 0 && neighborY < map[0].length) {
                    count += map[neighborX][neighborY] instanceof EmptyCell ? 1 : 0;
                }
            }
        }

        return count;
    }


    @Override
    public void draw() {
        b.draw();
        for (Cell[] squares : map) {
            for (Cell square : squares) {
                if (dev || square.getIlluminated()) {
                    if (square instanceof SpawnCell && !dev) continue;
                    square.draw(); // Draw each MapSquare
                }


                square.setIlluminated(false);

            }
        }
        float cwidth = width / cols, cheight= height / rows;
        if (dev) {
            app.fill(255, 0 ,0);
            app.rect(playerSpawn.getP().x, playerSpawn.getP().y, cwidth, cheight);


        }

        for (Cell[] squares : map) {
            for (Cell square : squares) {
                if (square instanceof CampCell) {
                    square.draw(); // Draw each MapSquare
                }
                square.setIlluminated(false);
            }
        }

        goal.draw();
    }

    @Override
    public void update() {
        // No specific update logic needed for the map formation
    }

    @Override
    public boolean intersection(GameComponent ge) {
        Cell c = getCellFromGCPosition(ge);
        return !c.isEmpty() && CollisionEngine.checkCollision(ge, c);
    }


    public PVector getClosestPoint(GameComponent gc) {
        PVector res = null;
        float minDist = Float.MAX_VALUE;

        Cell current = getCellFromGCPosition(gc);

        List<Cell> neighbours = getNeighbours(current);
        for (Cell neighbour : neighbours) {
            if (!neighbour.isEmpty()) continue;
            PVector p = neighbour.getClosestPoint(gc.getP());
            float d = PVector.dist(p, gc.getP());
            if (d < minDist) {
                minDist = d;
                res = p;
            }
        }
        return res;
    }


    public List<Cell> touch(GameComponent gc) {
        Cell c = getCellFromGCPosition(gc);
        List<Cell> neighbours = getNeighbours(c);
        List<Cell> res = new ArrayList<>();

        for (Cell neighbour : neighbours) {
            if (!neighbour.isEmpty() && CollisionEngine.checkCollision(gc, neighbour)) res.add(neighbour);
        }

        res.add(c);
        return res;
    }

    public List<Cell> getCellsWithinGrid(float x1, float y1, float x2, float y2) {
        float cwidth = width / cols, cheight= height / rows;
        List<Cell> res = new ArrayList<>();
        int x1Index = (int) (x1 / cwidth);
        int y1Index = (int) (y1 / cheight);
        int x2Index = (int) (x2 / cwidth);
        int y2Index = (int) (y2 / cheight);

        for (int i = x1Index; i <= x2Index; i++) {
            for (int j = y1Index; j <= y2Index; j++) {
                if (i >= 0 && i < cols && j >= 0 && j < rows) res.add(map[i][j]);
            }
        }

        return res;
    }


    public List<Cell> getNeighbours(Cell c) {
        List<Cell> res = new ArrayList<>();
        int x = (int) (c.getP().x / c.getWidth());
        int y = (int) (c.getP().y / c.getHeight());
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int ni = x + i;
                int nj = y + j;
                if (ni >= 0 && ni < cols && nj >= 0 && nj < rows) {
                    res.add(map[ni][nj]);
                }
            }
        }
        return res;

    }

    public Cell edit(int x, int y, int t) {
        return edit(x, y, t, ItemType.NO_ITEM);
    }


    public Cell edit(int x, int y, int t, ItemType itemType) {
        float cwidth = width / cols, cheight= height / rows;
        int xIndex = (int) (x / cwidth);
        int yIndex = (int) (y / cheight);

        switch (t){
            case 21:
                if (!(map[xIndex][yIndex] instanceof EmptyCell) && !(map[xIndex][yIndex] instanceof CampCell)) return map[xIndex][yIndex];
                playerSpawn = map[xIndex][yIndex];
                return map[xIndex][yIndex];
            case 20:
                if (!(map[xIndex][yIndex] instanceof EmptyCell)) return map[xIndex][yIndex];

                removeCurrentGoal();

                goal = Cell.cellFromType(map[xIndex][yIndex], t, this, itemType);
                map[xIndex][yIndex] = goal;
                return goal;
        }


        map[xIndex][yIndex] = Cell.cellFromType(map[xIndex][yIndex], t, this, itemType);
        return map[xIndex][yIndex];
    }

    private void removeCurrentGoal(){
        float cwidth = width / cols, cheight= height / rows;
        int x = Math.round(goal.getX() / cwidth);
        int y = Math.round(goal.getY() / cheight);
        map[x][y] = Cell.cellFromType(map[x][y], 0, this, ItemType.NO_ITEM);
    }

    public Cell getCellFromGCPosition(GameComponent gc)  {
        float cwidth = width / cols, cheight= height / rows;
        int x = (int) (gc.getP().x / cwidth);
        int y = (int) (gc.getP().y / cheight);
        return map[x][y];
    }


    public Cell getCellFromPoint(PVector p) {
        float cwidth = width / cols, cheight= height / rows;
        int x = (int) (p.x / cwidth);
        int y = (int) (p.y / cheight);
        return map[x][y];
    }



    public void setDev(boolean b) {
        this.dev = b;
    }

    public ArrayList<LightComponent> getLightComponents() {
        ArrayList<LightComponent> lights = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (map[i][j] instanceof TorchCell) {
                    lights.add(((TorchCell) map[i][j]).getLightComponent());
                } else if (map[i][j] instanceof CampCell) {
                    lights.add(((CampCell) map[i][j]).getLightComponent());
                } else if (map[i][j] instanceof GoalCell){
                    lights.add(((GoalCell) map[i][j]).getLightComponent());
                }
            }
        }
        return lights;
    }

    public ArrayList<Stalactite> getStalactites() {
        ArrayList<Stalactite> stalactites = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (map[i][j] instanceof Stalactite) {
                    stalactites.add(((Stalactite) map[i][j]));
                }
            }
        }
        return stalactites;
    }
    public void addDroplet(Droplet droplet) {
        droplets.add(droplet);
    }

    public List<Droplet> getDroplets() {
        return droplets;
    }


    public Cell getPlayerSpawn() {
        return playerSpawn;
    }

    public boolean reachedGoal(GameComponent gc){
        return Collisions.checkCollision(goal, gc);
    }


    public List<Cell> getSpawnPoints(){
        List<Cell> cs = new ArrayList<>();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
            if (cell instanceof SpawnCell) cs.add(cell);
            }
        }
        return cs;
    }

    public float getCellWidth (){
        return width / cols;
    }

    public float getCellHeight(){
        return height / rows;
    }

    public int getLevelHeight() {
        return((int) (playerSpawn.getP().y  / getHeight()) - (int) (goal.getP().y / getHeight()));
    }
}
