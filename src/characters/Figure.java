package characters;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public class Figure extends Character{
    GamePanel gp;
    KeyHandler keyH;
    int bonusPoints=0;

    /**
     * @param gp
     * @param keyH
     */
    public Figure(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        solidArea= new Rectangle();
        solidArea.x= 8;
        solidArea.y= 16;
        solidAreaDefaultX= solidArea.x;
        solidAreaDefaultY= solidArea.y;
        solidArea.width= 32;
        solidArea.height= 32;

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
            up1= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_up_2.png"));
            down1= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_down_2.png"));
            left1= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_left_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_left_2.png"));
            right1= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(){
        if(keyH.upPressed|| keyH.downPressed|| keyH.leftPressed
                || keyH.rightPeressed){
            if(keyH.upPressed){
                direction="up";
            }else if(keyH.downPressed){
                direction="down";
            }else if(keyH.leftPressed){
                direction="left";
            }else if(keyH.rightPeressed){
                direction="right";
            }
            collisionOn= false;
            gp.cChecker.checkTile(this);
            int objIndex= gp.cChecker.checkBonus(this,true);
            pickUpObject(objIndex);
            gp.cChecker.checkGoal(this,true);
            if(!collisionOn){
                switch (direction){
                    case "up": y-=speed; break;
                    case "down": y+=speed; break;
                    case "left": x-=speed; break;
                    case "right": x+=speed; break;
                }
            }

            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum==1){
                    spriteNum=2;
                }
                if (spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }
    }

    public void pickUpObject(int index){
        if(index!=999){
            System.out.println(gp.obj[index].name);
            gp.obj[index]= null;
        }
    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.blue);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image=null;
        switch (direction){
            case "up":
                if(spriteNum== 1){
                    image=up1;
                }
                if(spriteNum== 2){
                    image=up2;
                }
                break;
            case "down":
                if(spriteNum== 1){
                    image=down1;
                }
                if(spriteNum== 2){
                    image=down2;
                }
                break;
            case "left":
                if(spriteNum== 1){
                    image=left1;
                }
                if(spriteNum== 2){
                    image=left2;
                }
                break;
            case "right":
                if(spriteNum== 1){
                    image=right1;
                }
                if(spriteNum== 2){
                    image=right2;
                }
                break;
        }
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
