package ground;

import main.GamePanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The MazeGenerator class is responsible for generating mazes with specified dimensions
 * and providing utility methods to interact with the maze.
 */
public class MazeGenerator {

    /**
     * Represents the number of rows in the maze.
     */
    private int rows, /**
     * The number of columns in the maze.
     */
    cols;
    /**
     * A two-dimensional array representing the maze, where each element is a Tile object.
     * The maze is initially filled with wall tiles and then paths are carved out using
     * maze generation algorithms.
     */
    private Tile[][] maze;

    /**
     * Represents the code for a wall tile in the maze.
     *
     * This constant is used to denote a wall tile in the maze grid
     * within the `MazeGenerator` class. It helps identify which tiles
     * are walls when generating or transforming the maze.
     */
    private static final int WALL_TILE = 2;
    /**
     * Constant representing a tile in the maze that is a path.
     * Used by the MazeGenerator class to differentiate between
     * wall tiles and walkable tiles during maze generation.
     */
    private static final int PATH_TILE = 0;

    private static class Tile {
        boolean isWall = true;
        boolean isStart = false;
        boolean isEnd = false;

        public Tile(boolean isWall) {
            this.isWall = isWall;
        }

        @Override
        public String toString() {
            if (isStart) return "S";
            if (isEnd) return "E";
            return isWall ? "#" : " ";
        }
    }

    /**
     * Retrieves the end position of the maze.
     *
     * @return an array containing the column and row coordinates of the end position.
     */
    public int[] getEnd() {
        int[] endPosition={cols-2, rows-2};
        return endPosition;
    }

    /**
     * Constructs a MazeGenerator with a specific GamePanel instance.
     * Initializes the maze dimensions and sets up the initial maze state.
     *
     * @param gp the GamePanel instance that provides the configuration for the maze dimensions
     */
    public MazeGenerator(GamePanel gp) {
        this.rows = gp.maxScreenCol;
        this.cols = gp.maxScreenRow;
        this.maze = new Tile[rows][cols];
        initializeMaze();
    }

    /**
     * Initializes the maze by filling all the tiles with walls.
     *
     * This method iterates through each row and column of the maze
     * and assigns a new Tile object with the isWall property set to true,
     * effectively making every tile in the maze a wall tile.
     */
    private void initializeMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = new Tile(true);
            }
        }
    }

    /**
     * Generates a maze starting from the given startRow and startCol coordinates.
     * The method marks the starting tile, carves paths recursively, and sets the end tile.
     *
     * @param startRow the starting row index of the maze
     * @param startCol the starting column index of the maze
     */
    public void generateMaze(int startRow, int startCol) {
        maze[startRow][startCol].isWall = false;
        maze[startRow][startCol].isStart = true;

        carvePath(startRow, startCol);

        int endRow = rows - 2;
        int endCol = cols - 2;
        maze[endRow][endCol].isWall = false;
        maze[endRow][endCol].isEnd = true;
    }

    /**
     * Recursively carves a path through the maze by turning walls into paths,
     * ensuring the creation of a solvable maze structure.
     *
     * @param row The current row position in the maze.
     * @param col The current column position in the maze.
     */
    private void carvePath(int row, int col) {
        maze[row][col].isWall = false;

        List<int[]> directions = Arrays.asList(
                new int[]{0, -2}, // Up
                new int[]{2, 0},  // Right
                new int[]{0, 2},  // Down
                new int[]{-2, 0}  // Left
        );
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int newRow = row + dir[1];
            int newCol = col + dir[0];

            if (isInBounds(newRow, newCol) && maze[newRow][newCol].isWall) {
                maze[row + dir[1] / 2][col + dir[0] / 2].isWall = false;
                carvePath(newRow, newCol);
            }
        }
    }

    /**
     * Checks if the specified row and column are within the valid bounds
     * of the maze grid.
     *
     * @param row the row index to check
     * @param col the column index to check
     * @return true if the specified row and column are within bounds,
     *         false otherwise
     */
    private boolean isInBounds(int row, int col) {
        return row > 0 && row < rows - 1 && col > 0 && col < cols - 1;
    }

    /**
     * Retrieves a list of coordinates for the non-wall tiles in the maze.
     * Non-wall tiles are defined as tiles that are not walls, the start tile,
     * or the end tile.
     *
     * @return a list of integer arrays where each array contains the row and column
     *         coordinates of a non-wall tile.
     */
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

    /**
     * Transforms the internal maze representation to a simple map representation.
     * Each cell in the map will be marked as 2 if it corresponds to a wall in the maze,
     * or 0 if it corresponds to a path.
     *
     * @param map a 2D integer array representing the map where transformation will be applied.
     */
    public void transformMaze(int[][] map) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                map[row][col] = maze[row][col].isWall ? 2 : 0;
            }
        }
    }
}
