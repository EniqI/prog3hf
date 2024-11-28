package ground;

import bonuses.Goal;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The MapCreator class is responsible for creating and managing the game map,
 * including loading tile images, generating and transforming mazes, and
 * placing key game elements such as diamonds and the end goal.
 */
public class MapCreator {
    /**
     * An instance of the GamePanel class used for managing and rendering
     * the game's graphical user interface and core game components.
     */
    GamePanel gp;
    /**
     * Represents the array of ground tiles on the map.
     * Each element in the array is an object of the Ground class, which includes
     * properties for image representation and collision detection.
     * This field is used to construct and manage different segments of the game map.
     */
    public Ground[] tile;
    /**
     * This variable represents the tile numbers for the map being created or modified.
     * Each element in the 2D array corresponds to a specific tile in the map grid.
     * The values within the array indicate the type or ID of each tile.
     */
    public int[][] mapTileNum;
    /**
     * A 2D array representing the game map. Each element in the array is a
     * Ground object, which specifies the type of ground tile at that position.
     */
    public Ground[][] map;
    /**
     * The mazeGenerator variable is an instance of the MazeGenerator class, used for generating and managing maze structures.
     * It initializes and configures the maze based on the dimensions and other properties defined in the GamePanel instance.
     * The maze generation involves creating a grid of tiles, defining walls and paths using a maze generation algorithm, and
     * marking the start and end positions within the maze.
     */
    public MazeGenerator mazeGenerator;
    /**
     * A list of coordinates representing the locations of diamond tiles within the game map.
     * Each entry in the list is an integer array where the first element is the x-coordinate
     * and the second element is the y-coordinate of a diamond's position.
     */
    public List<int[]> diamondPlaces;
    /**
     * A list of possible locations for placing diamonds on the game map.
     * Each location is represented as an integer array with coordinates.
     */
    public List<int[]> possibleDiamondPlaces;
    /**
     * Represents the end tile in the game, marking the goal position that the player needs to reach.
     * This tile is an instance of the Goal class, which extends the Bonus class, and is utilized
     * by the MapCreator class to define and save the end position within the map.
     * The Goal class includes properties such as collision status and image, as well as
     * methods to set its x and y coordinates.
     */
    public Goal endTile;

    /**
     * Constructs a new MapCreator instance and initializes all required fields.
     *
     * @param gp the GamePanel instance used to initialize and interact with the map
     */
    public MapCreator(GamePanel gp) {
        this.gp = gp;
        tile = new Ground[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        map = new Ground[gp.maxScreenCol][gp.maxScreenRow];
        mazeGenerator = new MazeGenerator(gp);
        diamondPlaces = new ArrayList<>(); // Initialize diamondPlaces
        possibleDiamondPlaces= new ArrayList<>();
        endTile= new Goal();
        getGroundImage();
        mazeGenerator.generateMaze(1, 1);
        mazeGenerator.transformMaze(mapTileNum);
        possibleDiamondPlaces = mazeGenerator.getNonWallTiles();
        //int[] endPosition= mazeGenerator.getEnd();
        //endTile.setX(gp.maxScreenCol-2);
        //endTile.setY(gp.maxScreenRow-2);
    }



    /**
     * Loads ground images for different types of tiles and initializes the collision attribute
     * for certain tiles. The tile images are loaded from the specified file paths.
     *
     * This method assigns images to different tile objects as follows:
     * - road.png: A road tile
     * - dirt.png: A dirt tile
     * - magma.png: A magma tile; has collision enabled
     * - lava.png: A lava tile; has collision enabled
     * - fire.jpg: A fire tile; has collision enabled
     *
     * If an error occurs while reading an image file, the exception stack trace is printed.
     */
    public void getGroundImage() {
        try {
            tile[0] = new Ground();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("./tileimages/road.png"));

            tile[1] = new Ground();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("./tileimages/dirt.png"));

            tile[2] = new Ground();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("./tileimages/magma.png"));
            tile[2].collision = true;

            tile[3] = new Ground();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("./tileimages/lava.png"));
            tile[3].collision = true;

            tile[4] = new Ground();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("./tileimages/fire.jpg"));
            tile[4].collision = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map and diamond positions from a file.
     *
     * The method reads the map tiles and diamond positions from a specified file.
     * The map tiles are stored as integers in a 2D array, and the diamond positions
     * are stored in a list. The last diamond position is set as the end tile.
     *
     * @param filePath the file path to load the map from
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath); // File system path
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.split("\t");
                while (col < gp.maxScreenCol) {
                    int num = Integer.parseInt(numbers[col].strip());
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }

            // Read diamond positions
            String line;
            int diamondCnt=0;
            while ((line = br.readLine()) != null) {
                String[] nums = line.split("\t");
                if (nums.length == 2) {
                    int xParam = Integer.parseInt(nums[0].strip());
                    int yParam = Integer.parseInt(nums[1].strip());
                    int[] place = {xParam, yParam};
                    if(diamondCnt<2){
                        diamondPlaces.add(place);
                    }else {
                        endTile.setX(xParam);
                        endTile.setY(yParam);
                    }
                }
                diamondCnt+=1;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/**
 * Draws the map on the given Graphics2D object based on the current state of the map tiles.
 *
 * @param g2 The Graphics2D object used for drawing the map.
 */
    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int pixelX = 0;
        int pixelY = 0;
        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, pixelX, pixelY, gp.tileSize, gp.tileSize, null);
            col++;
            pixelX += gp.tileSize;
            if (col == gp.maxScreenCol) {
                col = 0;
                pixelX = 0;
                row++;
                pixelY += gp.tileSize;
            }
        }
    }
}
