package characters;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Represents a character in the game, including its position, speed, direction,
 * graphical representation, collision status, and bonus points.
 */
public class Character {
    public int x,y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter= 0;
    public  int spriteNum= 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn= false;
    public int bonusPoints=0;
}
