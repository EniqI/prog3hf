package main;

import ground.MapCreator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTilesize= 16;
    final int scale= 3;

    public final int tileSize= originalTilesize* scale;
    public final int maxScreenCol= 16;
    public int maxScreenRow= 12;
    public final int screenWidth= tileSize* maxScreenCol;
    public final int screenHight= tileSize* maxScreenRow;
    //FPS
    int FPS= 60;

    ground.MapCreator mapC= new MapCreator(this);
    KeyHandler keyH= new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker= new CollisionChecker(this);
    characters.Figure figure=new characters.Figure(this,keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval= 1000000000/FPS;
        double delta=0;
        long lastTime= System.nanoTime();
        long currentTime;
        while (gameThread != null){
            currentTime= System.nanoTime();
            delta+=(currentTime- lastTime)/drawInterval;
            lastTime= currentTime;

            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }

    }
    public void update(){
        figure.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        mapC.draw(g2);
        figure.draw(g2);
        g2.dispose();
    }
}
