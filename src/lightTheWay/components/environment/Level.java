package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import gamecore.engine.CollisionEngine;
import processing.core.PVector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level extends GameComponent implements Serializable {

    private int tileSize;
    private Cell[][] map;

    private int generations = 5;
    private Background b;
    private int rows, cols;
    private boolean dev = false;


    public Level(float width, float height, int tileSize) {
        super(new PVector(0, 0), width, height);
        this.b = new Background();
        this.tileSize = tileSize;
        rows = (int) Math.ceil(height / tileSize);
        cols = (int) (width / tileSize);

        generateMap();
    }

    private void generateMap() {
        map = new Cell[cols][rows];

        // Initialize the map with random values
        for (int i = 0; i < cols; i++)
            for (int j = 0; j < rows; j++)
                map[i][j] = new Cell(new PVector(i * tileSize, j * tileSize), tileSize, tileSize);

        // Apply cellular automata rules to smooth the map
        for (int i = 0; i < generations; i++) applyAutomataRules();

    }


    private void applyAutomataRules() {
        Cell[][] newMap = new Cell[cols][rows];

        // Apply rules to each cell
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                int aliveNeighbors = countAliveNeighbors(i, j);

                int t;

                if (map[i][j].isEmpty()) t = aliveNeighbors >= 3 ? 1 : 0;
                else t = aliveNeighbors >= 4 ? 1 : 0;

                newMap[i][j] = new Cell(map[i][j].getP(), tileSize, tileSize, t); // Wall dies if it has fewer than 4 neighbors
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
                    count += map[neighborX][neighborY].isEmpty() ? 1 : 0;
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
               if (dev || square.getIlluminated())
                   square.draw(); // Draw each MapSquare

            }
        }
    }

    @Override
    public void update() {
        // No specific update logic needed for the map formation
    }

    @Override
    public boolean intersection(GameComponent ge) {
        Cell c = getCellFromGCPosition(ge);
        List<Cell> neighbours = getNeighbours(c);

        for (Cell neighbour : neighbours) {
            if (!neighbour.isEmpty() && CollisionEngine.checkCollision(ge, neighbour)) return true;
        }

        return !c.isEmpty() && CollisionEngine.checkCollision(ge, c);
    }


    public PVector getClosestPoint(GameComponent gc) {
        PVector res = null;
        float minDist = Float.MAX_VALUE;

        Cell current = getCellFromGCPosition(gc);

        List<Cell> neighbours = getNeighbours(current);
        for (Cell neighbour : neighbours) {
            if (neighbour.isEmpty()) continue;
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
        List<Cell> res = new ArrayList<>();
        int x1Index = (int) (x1 / tileSize);
        int y1Index = (int) (y1 / tileSize);
        int x2Index = (int) (x2 / tileSize);
        int y2Index = (int) (y2 / tileSize);

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

    public void edit(int x, int y, int t) {
        int xIndex = (x / tileSize);
        int yIndex = (y / tileSize);

        map[xIndex][yIndex].setType(t);
    }


    public Cell getCellFromGCPosition(GameComponent gc) {
        int x = (int) (gc.getP().x / tileSize);
        int y = (int) (gc.getP().y / tileSize);
        return map[x][y];
    }


    public Cell getCellFromPoint(PVector p) {
        int x = (int) (p.x / tileSize);
        int y = (int) (p.y / tileSize);
        return map[x][y];
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setDev(boolean b){
        this.dev = b;
    }
}
