package ground;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class EndTile extends Ground{

    public EndTile(int x, int y){
        this.x= x* GamePanel.tileSize;
        this.y= y* GamePanel.tileSize;
        this.width= GamePanel.tileSize;
        this.height= GamePanel.tileSize;
        this.collision= true;
        getImage();
    }
    public void draw(Graphics2D g2){
        g2.drawImage(image,this.x,this.y,this.width,this.height,null);
    }
    public void getImage() {
        try {
            image= ImageIO.read(getClass().getResourceAsStream("./tileimages/goal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
