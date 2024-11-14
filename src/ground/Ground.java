package ground;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ground extends Rectangle {

    public BufferedImage image;
    public boolean collision= false;
    public Ground(int x,int y){
        this.x= x;
        this.y= y;
        this.width= GamePanel.tileSize;
        this.height=GamePanel.tileSize;
    }
    public Ground(){
        this.width= GamePanel.tileSize;
        this.height=GamePanel.tileSize;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(image,x,y, width, height, null);

    }
    public void setImage(int i){
        String fileName= null;
        switch (i){
            case 0:
                fileName="src/ground/tileimages/road.png";
                break;
            case 1:
                fileName= "src/ground/tileimages/dirt.png";
                break;
            case 2:
                fileName= "src/ground/tileimages/magma.png";
                collision=true;
                break;
            case 3:
                fileName= "src/ground/tileimages/lava.png";
                collision= true;
            case 4:
                fileName= "src/ground/tileimages/fire.jpg";
                collision=true;
                break;
            default:
                return;
        }
        try {
            image= ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
