package ground;

import bonuses.Goal;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapCreator {
    GamePanel gp;
    public Ground[] tile;
    public int[][] mapTileNum;
    public Ground[][] map;
    public MazeGenerator mazeGenerator;
    public List<int[]> diamondPlaces;
    public Goal endTile;

    public MapCreator(GamePanel gp) {
        this.gp = gp;
        tile = new Ground[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        map = new Ground[gp.maxScreenCol][gp.maxScreenRow];
        mazeGenerator = new MazeGenerator(gp);
        diamondPlaces = new ArrayList<>(); // Initialize diamondPlaces
        endTile= new Goal();
        getGroundImage();

        if (gp.oldGame) {
            switch(gp.maxScreenRow){
                case 9:
                    loadMap("./maps/smallmap.txt");
                    break;
                case 12:
                    loadMap("./maps/meduimmap.txt");
                    break;
                case 16:
                    loadMap("./maps/bigmap.txt");
                    break;
                default:
                    loadMap("./maps/easteregg.txt");
            }
        } else {
            mazeGenerator.generateMaze(2, 2);
            mazeGenerator.transformMaze(mapTileNum);
            diamondPlaces = mazeGenerator.getNonWallTiles();
            int[] endPosition= mazeGenerator.getEnd();
            endTile.setX(endPosition[0]);
            endTile.setY(endPosition[1]);
        }
    }

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
