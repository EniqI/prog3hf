package characters;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Figure extends Character {
    GamePanel gp;
    KeyHandler keyH;

    public Figure(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getFigureImage();
    }

    public void setDefaultValues() {
        x = 90;
        y = 90;
        speed = 4;
        direction = "right";
    }

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

    public void pickUpObject(int index) {
        if (index != 999 && gp.obj[index] != null) {
            System.out.println(gp.obj[index].name);
            gp.obj[index] = null;
            bonusPoints += 1;
        }
    }

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
