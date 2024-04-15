package lightTheWay.components.environment;

import gamecore.components.GameComponent;
import processing.core.PVector;

import java.util.ArrayList;

public class MapFormation extends GameComponent {

    private int tileSize;
    private MapSquare[][] map;
    private ArrayList<LadderComponent> ladders;
    private ArrayList<RopeComponent> ropes;
    private double chanceToStartAlive = 0.55;
    private int generations = 5;
    private Background b;

    public MapFormation(PVector p, float width, float height, int tileSize) {
        super(p, width, height);
        this.b = new Background();
        this.tileSize = tileSize;

        generateMap();

        paintRandomPlatforms();
        generateLadders();
        generateRope();
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

    private void generateLadders() {
        ladders = new ArrayList<>();
        for (int i = 0; i < map.length - 4; i++) { // Ensure there are enough rows below for the ladder
            for (int j = 0; j < map[0].length; j++) {
                // Check if the cell is empty and surrounded by walls on the sides
                if (map[i][j].getType() == 0 && surroundedByWalls(i, j)) {
                    // Check if there's already a ladder in this cell
                    if (Math.random() < 0.2) { // Adjust the probability as needed
                        // Create ladder components for the current cell and the 4 cells below it
                        for (int k = 0; k < 5; k++) {
                            LadderComponent ladder = new LadderComponent(new PVector(j * tileSize, (i + k) * tileSize), tileSize, tileSize);
                            ladders.add(ladder);
                        }
                        // Break out of the loop to avoid overlapping ladders in the same column
                        break;
                    }
                }
            }
        }
    }
    private boolean surroundedByWalls(int x, int y) {
        int rows = map.length;
        int cols = map[0].length;

        // Check if any of the neighboring cells are not walls
        boolean topWall = x - 1 < 0 || (map[x - 1][y].getType() != 1);
        boolean bottomWall = x + 1 >= rows || (map[x + 1][y].getType() != 1);
        boolean leftWall = y - 1 < 0 || (map[x][y - 1].getType() != 1);
        boolean rightWall = y + 1 >= cols || (map[x][y + 1].getType() != 1);

        return topWall && bottomWall || leftWall || rightWall;
    }

    private void generateRope() {
        ropes = new ArrayList<>();

        int topRow = 0; // Index of the top row

        // Iterate through the top row to find a space cell
        for (int j = 0; j < map[0].length; j++) {
            if (map[topRow][j].getType() == 1) { // Check if the cell is empty
                // Calculate the position of the rope in the center of the cell
                float x = j * tileSize + tileSize / 2.0f;
                float y = 0 + tileSize / 2.0f; // Start the rope at the top of the cell

                // Create a new RopeComponent at the calculated position
                RopeComponent rope = new RopeComponent(new PVector(x, y), tileSize, tileSize);
                // Add the rope to the list of game components
                ropes.add(rope);

                // Generate rope for 6 cells down
                for (int k = 1; k < 6; k++) {
                    float ropeY = 0 + k * tileSize + tileSize / 2.0f; // Calculate y-coordinate
                    RopeComponent nextRope = new RopeComponent(new PVector(x, ropeY), tileSize, tileSize);
                    ropes.add(nextRope);
                }

                break; // Stop searching once the rope is generated
            }
        }
    }



    private boolean spaceOnCeiling() {
        int ceilingRow = 0; // Index of the row below the ceiling

        // Iterate through the row below the ceiling
        for (int j = 0; j < map[0].length; j++) {
            // Check if the current cell is empty
            if (map[ceilingRow][j].getType() == 0) {
                // Check if there's enough space for the rope (at least 8 consecutive empty cells)
                boolean hasSpace = true;
                for (int k = j + 1; k < j + 8; k++) {
                    if (k >= map[0].length || map[ceilingRow][k].getType() != 0) {
                        // If any cell in the next 8 cells is not empty or out of bounds, there's not enough space
                        hasSpace = false;
                        break;
                    }
                }
                if (hasSpace) {
                    // If there's enough space, return true
                    return true;
                }
            }
        }
        // If no suitable opening is found, return false
        return false;
    }

    @Override
    public void draw() {
        b.draw();
        for (MapSquare[] squares : map) {
            for (MapSquare square : squares) {
                square.draw(); // Draw each MapSquare
            }
        }
        for(LadderComponent ladder: ladders){
            ladder.draw();
        }

        for(RopeComponent rope: ropes){
            rope.draw();
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
