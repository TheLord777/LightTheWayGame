package lightTheWay.components;

import gamecore.components.GameComponent;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.LinkedList;
import java.util.Queue;

import static processing.core.PApplet.floor;


public class MapFormation extends GameComponent {

    private int tileSize;
    private int[][] grid;
    private double chanceToStartAlive = 0.55;
    private int generations = 10;

    public MapFormation(PVector p, float width, float height, int tileSize) {
        super(p, width, height);
        this.tileSize = tileSize;
        generateMap();

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

        boolean[][] visited = new boolean[rows][cols];
        int numStartPoints = rows * cols / 150; // Adjust the number of start points as needed
        for (int i = 0; i < numStartPoints; i++) {
            int startX = floor(app.random(cols));
            int startY = floor(app.random(rows));
            floodFill(startX, startY, visited);
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


    public void floodFill(int startX, int startY, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;

        visited[startY][startX] = true;

        int[][] neighbors = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] neighbor : neighbors) {
            int newX = startX + neighbor[0];
            int newY = startY + neighbor[1];

            // Check if the neighboring cell is within bounds and is not visited
            if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && !visited[newY][newX] && grid[newY][newX] == 0) {
                floodFill(newX, newY, visited); // Recursively fill adjacent space
            }
        }
    }


    @Override
    public void draw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    app.fill(55, 44, 44);   // Fill with gray for walls
                } else {
                    // Determine color based on noise for variation
                    float noiseVal = app.noise(i * 0.05f, j * 0.05f); // Adjust frequency as needed
                    int rockColor = getColorFromNoise(noiseVal);
                    if (noiseVal < 0.45) { // Adjust the threshold for moss application (lower values for more moss)
                        // Calculate moss color with fading effect
                        float mossNoise = app.noise(i * 0.05f, j * 0.05f);
                        int mossBrightness = (int) app.map(mossNoise, 0, 1, 100, 200); // Adjust brightness range as needed
                        int mossSaturation = (int) app.map(mossNoise, 0, 1, 150, 255); // Adjust saturation range as needed
                        int mossHue = (int) app.map(mossNoise, 0, 1, 80, 120); // Adjust hue range as needed
                        app.fill(mossHue, mossSaturation, mossBrightness); // Moss color with fading effect
                    } else {
                        app.fill(rockColor);
                    }
                }
                app.noStroke();
                app.rect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }
    }



    // Function to map Perlin noise to a color gradient from light to dark
    private int getColorFromNoise(float noiseVal) {
        // Map noise value to a grayscale spectrum
        float brightness = app.map(noiseVal, 0, 1, 30, 220);  // Adjust brightness for variation
        return app.color(brightness);
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
