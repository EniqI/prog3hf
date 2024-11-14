package bonuses;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bonus {
    public BufferedImage image;
    public String name;
    public  boolean collision=false;
    public int x, y;
    public Rectangle solidArea= new Rectangle(0,0,32,32);
    public int solidAreaDefaultX=0;
    public int solidAreaDefaultY=0;


    public void draw(Graphics2D g2, GamePanel gp){
        g2.drawImage(image,x,y,gp.originalTilesize* 2, gp.originalTilesize* 2, null);
    }

}
