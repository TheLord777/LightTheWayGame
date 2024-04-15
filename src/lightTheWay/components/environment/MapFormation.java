package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;
import static processing.core.PApplet.println;

public class MapFormation extends GameComponent {

    private int tileSize;
    private MapSquare[][] map;
    private double chanceToStartAlive = 0.55;
    private int generations = 5;
    private Background b;

    public MapFormation(PVector p, float width, float height, int tileSize) {
        super(p, width, height);
        this.b = new Background();
        this.tileSize = tileSize;

        generateMap();
        paintRandomPlatforms();
    }

    private void generateMap() {
        int rows = (int) (height / tileSize);
        int cols = (int) (width / tileSize);
        map = new MapSquare[rows][cols];

        // Initialize the map with random values
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int t = Math.random() < chanceToStartAlive? 1: 0;

                map[i][j] = new MapSquare(new PVector(j * tileSize, i * tileSize),tileSize, tileSize, t);

            }
        }

        // Apply cellular automata rules to smooth the map
        for (int i = 0; i < generations; i++) {
            applyAutomataRules();
        }
    }

    private void paintRandomPlatform(int x, int y, int platformSize) {
        int rows = map.length;
        int cols = map[0].length;

        // Define the boundaries of the platform within the radius
        int startX = Math.max(0, x - platformSize);
        int endX = Math.min(rows - 1, x + platformSize);
        int startY = Math.max(0, y - platformSize);
        int endY = Math.min(cols - 1, y + platformSize);

        // Generate the platform within the defined boundaries
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (Math.random() < 0.5) {
                    map[i][j].setType(0); // Set the cell as a wall with a 50% chance
                }
            }
        }
    }

    private void paintRandomPlatforms() {
        int rows = map.length;
        int cols = map[0].length;

        // Loop through each cell in the map
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if the cell is not empty and surrounded by all space cells
                if (map[i][j].getType() != 0 && allSurroundingCellsAreSpace(i, j)) {
                    //println("Cell (" + i + ", " + j + ") is surrounded by all space cells.");

                    // Paint a random platform at the current cell
                    paintRandomPlatform(i, j, 2); // Adjust the platform size as needed

                    // Return to the beginning of the map to restart the search
                    i = 0;
                    j = 0;
                }
            }
        }
    }

    private boolean allSurroundingCellsAreSpace(int x, int y) {
        int rows = map.length;
        int cols = map[0].length;

        for (int i = x - 10; i <= x + 5; i++) {
            for (int j = y - 3; j <= y + 3; j++) {
                // Ensure the coordinates are within bounds
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    // Skip the center cell
                    if (i == x && j == y) {
                        continue;
                    }
                    // Skip if the cell is already a platform
                    if (map[i][j].getType() == 0) {
                        // Debug
                        //println("Checking cell (" + i + ", " + j + ") - Value: " + map[i][j].getType());

                        // If any cell in the 3x3 area is not empty, return false
                        if (map[i][j].getType() != 1) {
                            //println("Cell (" + i + ", " + j + ") is not empty.");
                            return false;
                        }
                    }
                } else {
                    // Debug
                    //println("Cell (" + i + ", " + j + ") is out of bounds.");
                    // If any cell is out of bounds, return false
                    return false;
                }
            }
        }
        // If all cells in the 3x3 area are empty or platforms, return true
        return true;
    }

    private void applyAutomataRules() {
        int rows = map.length;
        int cols = map[0].length;
        MapSquare[][] newMap = new MapSquare[rows][cols];

        // Apply rules to each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbors = countAliveNeighbors(i, j);

                if (map[i][j].getType() == 1) {
                    if (aliveNeighbors < 4) {
                        newMap[i][j] = new MapSquare(new PVector(j * tileSize, i * tileSize), tileSize, tileSize, 0); // Wall dies if it has fewer than 4 neighbors
                    } else {
                        newMap[i][j] = new MapSquare(new PVector(j * tileSize, i * tileSize), tileSize, tileSize, 1); // Wall remains alive
                    }
                } else {
                    if (aliveNeighbors > 4) {
                        newMap[i][j] = new MapSquare(new PVector(j * tileSize, i * tileSize), tileSize, tileSize, 1); // Space becomes wall if it has more than 4 neighbors
                    } else {
                        newMap[i][j] = new MapSquare(new PVector(j * tileSize, i * tileSize), tileSize, tileSize, 0); // Space remains empty
                    }
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
                int neighborX = x + i;
                int neighborY = y + j;

                if (neighborX >= 0 && neighborX < map.length && neighborY >= 0 && neighborY < map[0].length) {
                    count += map[neighborX][neighborY].getType();
                }
            }
        }
        count -= map[x][y].getType(); // Exclude the cell itself from the count
        return count;
    }




    @Override
    public void draw() {
        b.draw();
        for (MapSquare[] squares : map) {
            for (MapSquare square : squares) {
                square.draw(); // Draw each MapSquare
            }
        }
    }

    // Function to map Perlin noise to a color gradient from light to dark

    @Override
    public void update() {
        // No specific update logic needed for the map formation
    }

    @Override
    public boolean intersection(GameComponent ge) {
        return false; // No collision detection for the map
    }
}
