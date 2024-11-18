package bonuses;

import javax.imageio.ImageIO;

public class Goal extends Bonus{
    public Goal(){
        name="Goal!";
        try {
            image= ImageIO.read(getClass().getResourceAsStream("./boniusimages/goal.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        solidAreaDefaultX= 10;
        solidAreaDefaultY= 10;
    }
}
