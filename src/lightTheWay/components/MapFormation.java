package lightTheWay.components;

import gamecore.components.GameComponent;
import processing.core.PVector;


public class MapFormation extends GameComponent {

    private int tileSize;
    private int[][] grid;
    private double chanceToStartAlive = 0.55;
    private int generations = 15;

    public MapFormation(PVector p, float width, float height, int tileSize) {
        super(p, width, height);
        this.tileSize = tileSize;
        generateMap(); // Generate the map upon initialization

    }

    private void generateMap() {
        int rows = (int) (height / tileSize);
        int cols = (int) (width / tileSize);
        grid = new int[rows][cols];

        // Initialize the grid with random values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.random() < chanceToStartAlive) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }

        // Apply cellular automata rules to smooth the map
        for (int i = 0; i < generations; i++) {
            applyAutomataRules();
        }
    }

    public void applyAutomataRules() {
        int rows = (int) (height / tileSize);
        int cols = (int) (width / tileSize);
        int[][] newGrid = new int[rows][cols];

        // Apply rules to each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbors = countAliveNeighbors(i, j);

                // Apply modified rules of cellular automata
                if (grid[i][j] == 1) {
                    if (aliveNeighbors < 4) {
                        newGrid[i][j] = 0; // Wall dies if it has fewer than 4 neighbors
                    } else {
                        newGrid[i][j] = 1; // Wall remains alive
                    }
                } else {
                    if (aliveNeighbors > 4) {
                        newGrid[i][j] = 1; // Space becomes wall if it has more than 4 neighbors
                    } else {
                        newGrid[i][j] = 0; // Space remains empty
                    }
                }
            }
        }

        // Update the grid
        grid = newGrid;
    }

    public int countAliveNeighbors(int x, int y) {
        int rows = (int) (height / tileSize);
        int cols = (int) (width / tileSize);
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < rows && neighborY >= 0 && neighborY < cols) {
                    count += grid[neighborX][neighborY];
                }
            }
        }
        count -= grid[x][y]; // Exclude the cell itself from the count
        return count;
    }

    @Override
    public void draw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    app.fill(255);   // Fill with white for empty space
                } else {
                    app.fill(0); // Fill with black for walls
                }
                app.noStroke();
                app.rect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }
    }

    @Override
    public void update() {
        // No specific update logic needed for the map formation
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false; // No collision detection for the map
    }
}
