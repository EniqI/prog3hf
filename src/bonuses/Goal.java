package bonuses;

import javax.imageio.ImageIO;

/**
 * The Goal class represents the goal in the game, extending the Bonus class.
 * This class is responsible for initializing the goal with specific properties such as name, collision status, and image.
 * It also provides methods to set the x and y coordinates of the goal.
 */
public class Goal extends Bonus{
    public Goal(){
        name="Goal!";
        collision=false;
        try {
            image= ImageIO.read(getClass().getResourceAsStream("./boniusimages/goal.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        solidAreaDefaultX= 0;
        solidAreaDefaultY= 0;
    }
    public void setX(int xCoord){
        this.x= xCoord;
    }
    public void setY(int yCoord){
        this.y= yCoord;
    }
}
