package lightTheWay.components.environment;

import gamecore.components.GameComponent;
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
                map[i][j] = new WallCell(new PVector(i * tileSize, j * tileSize), tileSize, tileSize);

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

                if (t == 0){
                    newMap[i][j] = new WallCell(map[i][j].getP(), tileSize, tileSize, t);
                } else {
                    newMap[i][j] = new Cell(map[i][j].getP(), tileSize, tileSize, t);
                }
                ; // Wall dies if it has fewer than 4 neighbors
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
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.intersection(ge)) return true;
            }
        }
        return false; // No collision detection for the map
    }


    public List<Cell> touch(GameComponent ge) {
        List<Cell> touched = new ArrayList<>();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell.intersection(ge)) touched.add(cell);
            }
        }
        return touched;
    }

    public void edit(int x, int y, int t) {
        int xIndex = (x / tileSize);
        int yIndex = (y / tileSize);


        map[xIndex][yIndex].setType(t);
    }
}
