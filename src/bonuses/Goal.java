package bonuses;

import javax.imageio.ImageIO;

public class Goal extends Bonus{
    public Goal(){
        name="Goal!";
        collision=false;
        try {
            image= ImageIO.read(getClass().getResourceAsStream("./boniusimages/goal.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        solidAreaDefaultX= 4;
        solidAreaDefaultY= 4;
    }
    public void setX(int xCoord){
        this.x= xCoord;
    }
    public void setY(int yCoord){
        this.y= yCoord;
    }
}
