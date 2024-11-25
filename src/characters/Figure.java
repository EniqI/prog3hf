package characters;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a game figure controlled by the player.
 * The figure can move in different directions and interact with various objects in the game world.
 */
public class Figure extends Character {
    GamePanel gp;
    KeyHandler keyH;
    int bonusPoints=0;

    /**
     * Constructs a new Figure object.
     *
     * @param gp   the game panel in which the figure will be rendered and updated
     * @param keyH the key handler to manage user input for the figure
     */
    public Figure(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getFigureImage();
    }

    /**
     * Sets the default values for the figure's position, speed, and direction.
     * This method initializes the figure's x and y coordinates to 50, speed to 4,
     * and direction to "right".
     */
    public void setDefaultValues() {
        x = 50;
        y = 50;
        speed = 4;
        direction = "right";
    }

    /**
     * Loads the images of the character figure in different directions.
     *
     * This method reads image files for the figure's movements (up, down, left, right)
     * and assigns them to corresponding BufferedImage fields.
     * It catches and logs any IOException that may occur during the image loading process.
     */
    public void getFigureImage() {
        try {
            up1= ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("./figureimages/boy_right_2.png"));
        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    /**
     * Updates the state of the Figure based on input and collisions.
     *
     * The method checks input from the KeyHandler to determine the direction of movement.
     * It performs collision detection with tiles, bonuses, and the goal.
     * The character's position is updated based on the direction and the absence of collisions.
     * The sprite is toggled to animate the character's movement.
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkBonus(this, true);
            pickUpObject(objIndex);
            gp.cChecker.checkGoal(this, true);

            if (!collisionOn) {
                switch (direction) {
                    case "up": y -= speed; break;
                    case "down": y += speed; break;
                    case "left": x -= speed; break;
                    case "right": x += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum % 2) + 1; // Toggle sprite
                spriteCounter = 0;
            }
        }
    }

    /**
     * Handles the logic for the player character to pick up an object.
     * If the object at the given index is not null and the index is not 999,
     * the object is picked up, a message is printed, the object is removed,
     * and bonus points are incremented.
     *
     * @param index the index of the object to be picked up
     */
    public void pickUpObject(int index) {
        if (index != 999 && gp.obj[index] != null) {
            System.out.println(gp.obj[index].name);
            gp.obj[index] = null;
            bonusPoints += 1;
        }
    }
    /**
     * Retrieves the current bonus points accumulated by the figure.
     *
     * @return the number of bonus points.
     */
    public int getBonusPoints(){return bonusPoints;}

    /**
     * Draws the current sprite of the character on the given Graphics2D context
     * at the character's x and y coordinates. The sprite drawn depends on
     * the character's direction and sprite number.
     *
     * If a corresponding image is not found, a red rectangle is drawn instead.
     *
     * @param g2 the Graphics2D context on which to draw the sprite
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        }
    }
}
