import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public class Figure extends Character{
    GamePanel gp;
    KeyHandler keyH;

    /**
     * @param gp
     * @param keyH
     */
    public Figure(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        setDefaultValues();
        getFigureImage();
    }
    public void setDefaultValues(){
        x= 100;
        y= 100;
        speed= 4;
        direction= "right";
    }
    public void getFigureImage() {
        try {
            up1= ImageIO.read(getClass().getResourceAsStream("./images/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("./images/boy_up_2.png"));
            down1= ImageIO.read(getClass().getResourceAsStream("./images/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("./images/boy_down_2.png"));
            left1= ImageIO.read(getClass().getResourceAsStream("./images/boy_left_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("./images/boy_left_2.png"));
            right1= ImageIO.read(getClass().getResourceAsStream("./images/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("./images/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(){
        if(keyH.upPressed==true){
            direction="up";
            y -= speed;
        }else if(keyH.downPressed==true){
            direction="down";
            y += speed;
        }else if(keyH.leftPressed==true){
            direction="left";
            x -= speed;
        }else if(keyH.rightPeressed==true){
            direction="right";
            x += speed;
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.blue);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image=null;
        switch (direction){
            case "up":
                image=up1;
                break;
            case "down":
                image=down1;
                break;
            case "left":
                image=left1;
                break;
            case "right":
                image=right1;
                break;
        }
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
