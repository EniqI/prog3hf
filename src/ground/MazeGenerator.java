package ground;

import main.GamePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGenerator {
    // Directions for moving in the grid
    private static final int[][] DIRECTIONS = {
            {0, -1}, // Up
            {1, 0},  // Right
            {0, 1},  // Down
            {-1, 0}  // Left
    };

    private int rows, cols;
    private Cell[][] maze;
    private GamePanel gp;
    // Inner class to represent a cell in the maze
    private static class Cell {
        boolean visited = false;
        boolean[] walls = {true, true, true, true}; // Top, Right, Bottom, Left
    }

    public MazeGenerator(GamePanel gp) {
        this.gp=gp;
        this.rows = gp.maxScreenRow;
        this.cols = gp.maxScreenCol;
        this.maze = new Cell[rows][cols];

        // Initialize the maze grid
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = new Cell();
            }
        }
    }

    // Generate the maze using recursive backtracking
    public void generateMaze(int startRow, int startCol) {
        generate(startRow, startCol);
    }

    private void generate(int row, int col) {
        maze[row][col].visited = true;

        // Randomly shuffle directions
        List<int[]> directions = new ArrayList<>();
        Collections.addAll(directions, DIRECTIONS);
        Collections.shuffle(directions);

        for (int[] direction : directions) {
            int newRow = row + direction[1];
            int newCol = col + direction[0];

            // Check bounds and if the neighbor has not been visited
            if (isInBounds(newRow, newCol) && !maze[newRow][newCol].visited) {
                // Remove the wall between current cell and neighbor
                removeWall(row, col, direction);
                removeWall(newRow, newCol, new int[]{-direction[0], -direction[1]});

                // Recursively visit the neighbor
                generate(newRow, newCol);
            }
        }
    }

    private void removeWall(int row, int col, int[] direction) {
        if (direction[1] == -1) {
            maze[row][col].walls[0] = false; // Remove top wall
        } else if (direction[0] == 1) {
            maze[row][col].walls[1] = false; // Remove right wall
        } else if (direction[1] == 1) {
            maze[row][col].walls[2] = false; // Remove bottom wall
        } else if (direction[0] == -1) {
            maze[row][col].walls[3] = false; // Remove left wall
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void transformMaze( int[][] map){
        int col= 0;
        int row= 0;
        while(col< cols && row< rows) {
            if (this.maze[col][row].walls[0]) {
                map[col][row] = 2;
            } else {
                map[col][row] = 0;
            }
            col++;
            if (col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }
}
