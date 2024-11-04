import javax.imageio.ImageIO;
import java.awt.*;

public class Figure extends Character{
    GamePanel gp;
    KeyHandler keyH;

    public Figure(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        setDefaultValues();
    }
    public void setDefaultValues(){
        x= 100;
        y= 100;
        speed= 4;
    }
    public void getFigureImage(){
        //up1= ImageIO.read(getClass().getResourceAsStream(""));
    }

    public void update(){
        if(keyH.upPressed==true){
            y -= speed;
        }else if(keyH.downPressed==true){
            y += speed;
        }else if(keyH.leftPressed==true){
            x -= speed;
        }else if(keyH.rightPeressed==true){
            x += speed;
        }
    }
    public void draw(Graphics2D g2){

        g2.setColor(Color.blue);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
