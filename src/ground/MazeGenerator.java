package ground;

import main.GamePanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeGenerator {

        private int rows, cols;
        private Tile[][] maze;

        // Tile class representing each cell in the maze

            private static class Tile {
                boolean isWall = true;
                boolean isStart = false;
                boolean isEnd = false;

                public Tile(boolean isWall) {
                    this.isWall = isWall;
                }

                @Override
                public String toString() {
                    if (isStart) return "S"; // Mark start tile with 'S'
                    if (isEnd) return "E";   // Mark end tile with 'E'
                    return isWall ? "#" : " ";
                }
            }

        public MazeGenerator(GamePanel gp) {
            this.rows = gp.maxScreenRow;
            this.cols = gp.maxScreenCol;
            this.maze = new Tile[rows][cols];

            // Initialize the grid with walls
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    maze[row][col] = new Tile(true);
                }
            }
        }

        // Generate the maze using recursive backtracking
        public void generateMaze(int startRow, int startCol) {
            // Set the start tile
            maze[startRow][startCol].isWall = false;
            maze[startRow][startCol].isStart = true;

            // Generate the maze
            carvePath(startRow, startCol);

            // Set the end tile
            int endRow = rows - 2; // Second-to-last row
            int endCol = cols - 2; // Second-to-last column
            maze[endRow][endCol].isWall = false;
            maze[endRow][endCol].isEnd = true;
        }

        private void carvePath(int row, int col) {
            maze[row][col].isWall = false; // Mark the current tile as a path

            // Directions for movement (up, right, down, left)
            int[][] directions = {
                    {0, -2}, // Up
                    {2, 0},  // Right
                    {0, 2},  // Down
                    {-2, 0}  // Left
            };

            // Shuffle directions to create randomness
            List<int[]> shuffledDirections = new ArrayList<>();
            Collections.addAll(shuffledDirections, directions);
            Collections.shuffle(shuffledDirections);

            for (int[] dir : shuffledDirections) {
                int newRow = row + dir[1];
                int newCol = col + dir[0];

                // Check if the next cell is within bounds and is a wall
                if (isInBounds(newRow, newCol) && maze[newRow][newCol].isWall) {
                    // Carve the wall between the current cell and the neighbor
                    maze[row + dir[1] / 2][col + dir[0] / 2].isWall = false;

                    // Recursively carve the path from the neighbor cell
                    carvePath(newRow, newCol);
                }
            }
        }

        private boolean isInBounds(int row, int col) {
            return row > 0 && row < rows - 1 && col > 0 && col < cols - 1;
        }

    public List<int[]> getNonWallTiles() {
        List<int[]> nonWallTiles = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!maze[row][col].isWall && !maze[row][col].isStart && !maze[row][col].isEnd) {
                    nonWallTiles.add(new int[]{row, col});
                }
            }
        }
        return nonWallTiles;
    }

    public void transformMaze( int[][] map){
        int col= 0;
        int row= 0;
        while(col< cols && row< rows) {
            if (maze[col][row].isWall) {
                map[col][row] = 2;
            } else {
                map[col][row] = 0;
            }
            col++;
            if (col == cols) {
                col = 0;
                row++;
            }
        }
    }
}
