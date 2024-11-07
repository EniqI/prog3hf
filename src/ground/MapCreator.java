package ground;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;

public class MapCreator {
    GamePanel gp;
    Ground[] tile;

    public MapCreator(GamePanel gp){
        this.gp= gp;
        tile= new Ground[10];
        getGroundImage();
    }
    public void getGroundImage(){
        try {
            tile[0]= new Ground();
            tile[0].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/road.png"));

            tile[1]= new Ground();
            tile[1].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/dirt.png"));

            tile[2]= new Ground();
            tile[2].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/magma.png"));

            tile[3]= new Ground();
            tile[3].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/lava.png"));

            tile[4]= new Ground();
            tile[4].image= ImageIO.read(getClass().getResourceAsStream("./tileimages/fire.jpg"));

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
            g2.drawImage(tile[0].image,x,y,gp.tileSize, gp.tileSize, null);

        }
    }
}
