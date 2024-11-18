package main;

import bonuses.Bonus;
import bonuses.Goal;
import characters.Figure;
import ground.MapCreator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    public static final int originalTilesize= 16;
    final static int scale= 3;

    public static final int tileSize= originalTilesize* scale;
    public final int maxScreenCol= 16;
    public int maxScreenRow= 12;
    public final int screenWidth= tileSize* maxScreenCol;
    public final int screenHight= tileSize* maxScreenRow;
    //FPS
    int FPS= 60;
    MapCreator mapC= new MapCreator(this);
    KeyHandler keyH= new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker= new CollisionChecker(this);
    public AssetSetter aSetter= new AssetSetter(this);
    public Figure figure=new Figure(this,keyH);
    public Bonus obj[]= new Bonus[10];
    public Goal goal= new Goal();

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame(){
        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval= (double) 1000000000 /FPS;
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
    int counter=0;
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        //GROUND
        if(counter<2) {
            mapC.draw(g2);
            counter+=1;
        }
        //GOAL tile
        for(Bonus element: obj){
            if(element!=null){
                break;
            }
            goal.draw(g2,this);
        }
        //BONUS
        for(Bonus element: obj){
            if(element!=null){
                element.draw(g2,this);
            }
        }
        //FIGURE
        figure.draw(g2);
        g2.dispose();
    }
}
