package ground;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class MapCreator {
    GamePanel gp;
    public Ground[] tile;
    public int[][] mapTileNum;
    public Ground[][] map;
    public MazeGenerator mazeGenerator;

    public MapCreator(GamePanel gp){
        this.gp= gp;
        tile= new Ground[10];
        mapTileNum= new  int[gp.maxScreenCol][gp.maxScreenRow];
        map= new Ground[gp.maxScreenCol][gp.maxScreenRow];
        mazeGenerator=new MazeGenerator(gp);
        getGroundImage();
        //loadMap("src/ground/maps/map1.txt");
        if(gp.oldGame) {
            loadMap("./maps/map1.txt");
        }else {
            mazeGenerator.generateMaze(gp.maxScreenCol,gp.maxScreenRow);
            mazeGenerator.transformMaze(mapTileNum);
        }
    }
    public void getGroundImage(){
        try {
            tile[0]= new Ground();
            tile[0].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/road.png"));

            tile[1]= new Ground();
            tile[1].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/dirt.png"));

            tile[2]= new Ground();
            tile[2].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/magma.png"));
            tile[2].collision=true;

            tile[3]= new Ground();
            tile[3].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/lava.png"));
            tile[3].collision=true;

            tile[4]= new Ground();
            tile[4].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/fire.jpg"));
            tile[4].collision=true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try {
            InputStream is= getClass().getResourceAsStream(filePath); //ez jol mukodik
            BufferedReader br= new BufferedReader(new InputStreamReader(is));
            int col=0;
            int row=0;
            while(col< gp.maxScreenCol && row< gp.maxScreenRow){
                String line = br.readLine(); //ez is jo, vannak benne szamok

                while (col< gp.maxScreenCol){

                    String[] numbers = line.split("\t");
                    int num= Integer.parseInt(numbers[col].strip());
                    mapTileNum[col][row]= num;
                    col++;
                }
                if(col== gp.maxScreenCol){
                    col=0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2){
        int col= 0;
        int row= 0;
        int x= 0;
        int y= 0;
        while(col< gp.maxScreenCol && row< gp.maxScreenRow){
            int tileNum= mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image,x,y,gp.tileSize, gp.tileSize, null);
            col++;
            x+= gp.tileSize;
            if(col== gp.maxScreenCol){
                col=0;
                x=0;
                row++;
                y+= gp.tileSize;
            }
        }
    }
/*
    public void loadMap(String filePath) {

        Scanner scanner= null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0; i<gp.maxScreenRow; i++){
            for (int j = 0; j < gp.maxScreenCol; j++) {
                map[j][i] = new Ground(j*GamePanel.tileSize,i*GamePanel.tileSize);
                map[j][i].setImage(scanner.nextInt());
            }
        }
    }

    public void draw(Graphics2D g2){
        for(Ground[] col: map){
            for(Ground tile: col){
                tile.draw(g2);
            }
        }
    }*/
}
