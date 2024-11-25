package ground;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Ground class represents a specific type of rectangular ground tile in the game.
 * It extends from the Rectangle class and contains an image to visually represent the tile
 * and a collision attribute to denote whether the tile can be collided with.
 */
public class Ground extends Rectangle {

    public BufferedImage image;
    public boolean collision= false;

}
