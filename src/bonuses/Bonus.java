package bonuses;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a Bonus item in the game, which can be drawn on the screen and interact with the player.
 * The Bonus item can have a name, image, position coordinates (x and y), and a collision status.
 * It also has a solid area represented by a rectangle for collision detection.
 */
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
