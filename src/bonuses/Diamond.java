package bonuses;

import javax.imageio.ImageIO;

/**
 * Represents a Diamond bonus item in the game.
 * The Diamond item extends the Bonus class and initializes its own name and image.
 * The image of the Diamond is loaded from a resource file.
 */
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
