package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        JFrame window= new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Labirinth Extreme");

        GamePanel gamePanel= new GamePanel();
        window.add(gamePanel);
        window.setSize(gamePanel.screenWidth,gamePanel.screenHight);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();

    }
}
