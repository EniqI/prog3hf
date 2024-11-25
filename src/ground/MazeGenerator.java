package ground;

import main.GamePanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MazeGenerator {

    private int rows, cols;
    private Tile[][] maze;

    private static final int WALL_TILE = 2;
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

    public int[] getEnd() {
        int[] endPosition={cols-2, rows-2};
        return endPosition;
    }

    public MazeGenerator(GamePanel gp) {
        this.rows = gp.maxScreenCol;
        this.cols = gp.maxScreenRow;
        this.maze = new Tile[rows][cols];
        initializeMaze();
    }

    private void initializeMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = new Tile(true);
            }
        }
    }

    public void generateMaze(int startRow, int startCol) {
        maze[startRow][startCol].isWall = false;
        maze[startRow][startCol].isStart = true;

        carvePath(startRow, startCol);

        int endRow = rows - 2;
        int endCol = cols - 2;
        maze[endRow][endCol].isWall = false;
        maze[endRow][endCol].isEnd = true;
    }

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

    public void transformMaze(int[][] map) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                map[row][col] = maze[row][col].isWall ? 2 : 0;
            }
        }
    }
}
