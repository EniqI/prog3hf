package main;

import javax.swing.*;
import java.awt.*;

/**
 * The UI class is responsible for rendering the user interface elements
 * for the game, specifically the congratulatory message when the player wins.
 */
public class UI {
    /**
     * Represents the game panel that provides the primary graphical
     * interface for the game. This panel is responsible for rendering
     * the game's visual elements and managing the player's interactions
     * with the game world.
     */
    GamePanel gp;
    /**
     * A Font object representing "Times New Roman" with a bold style and size 40.
     * Utilized for rendering congratulatory messages when the player wins.
     */
    Font TimesNewRoman_20;
    long elapsedMinutes;
    long elapsedSeconds;

    /**
     * Initializes the UI with the given GamePanel and sets up the font for rendering texts.
     *
     * @param gp the GamePanel instance that provides context for rendering the UI
     */
    public  UI(GamePanel gp){
        this.gp=gp;
        TimesNewRoman_20= new Font("Times New Roman", Font.BOLD,20);
    }
    /**
     * Draws the congratulatory message on the provided Graphics2D context when the player wins the game.
     *
     * @param g2 the Graphics2D context on which to draw the message
     */
    public void draw(Graphics2D g2){
        g2.setFont(TimesNewRoman_20);
        g2.setColor(Color.white);
        elapsedMinutes= gp.elapsedTime/ 60;
        elapsedSeconds= gp.elapsedTime% 60;

        g2.drawString("CONGRATS "+gp.playerName+" you won!", 1* gp.tileSize, 3* gp.tileSize);
        g2.drawString("Elapsed time: "+elapsedMinutes+" minutes and "+elapsedSeconds+" seconds.", 1* gp.tileSize, 4* gp.tileSize);

    }
}
