package bonuses;

import javax.imageio.ImageIO;

public class Diamond extends Bonus{

    public Diamond(){
        name="Diamond";
        try {
            image= ImageIO.read(getClass().getResourceAsStream("./boniusimages/diamond.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
