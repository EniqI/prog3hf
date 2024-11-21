package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Font TimesNewRoman_80;

    public  UI(GamePanel gp){
        this.gp=gp;
        TimesNewRoman_80= new Font("Times New Roman", Font.BOLD,160);
    }
    public void draw(Graphics2D g2){
        g2.setFont(TimesNewRoman_80);
        g2.setColor(Color.white);
        g2.drawString("You won!", 1* gp.tileSize, 6* gp.tileSize);

    }
}
