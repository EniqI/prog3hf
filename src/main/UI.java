package main;

import javax.swing.*;
import java.awt.*;

public class UI {
    GamePanel gp;
    Font TimesNewRoman_40;

    public  UI(GamePanel gp){
        this.gp=gp;
        TimesNewRoman_40= new Font("Times New Roman", Font.BOLD,40);
    }
    public void draw(Graphics2D g2){
        g2.setFont(TimesNewRoman_40);
        g2.setColor(Color.white);
        g2.drawString("CONGRATS "+gp.playerName+" \n you won!", 1* gp.tileSize, 3* gp.tileSize);

    }
}
