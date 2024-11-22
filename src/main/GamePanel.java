package main;

import bonuses.Bonus;
import bonuses.Goal;
import characters.Figure;
import ground.MapCreator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public String playerName;
    public boolean oldGame=true;
    public static final int originalTilesize= 16;
    final static int scale= 3;

    public static final int tileSize= originalTilesize* scale;
    public int maxScreenCol;
    public int maxScreenRow;
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
    public UI ui= new UI(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    //ha fileból olvasunk be, akkor betölti a fileból a helyüket, ha új game, akkor lekéri
    public void setUpGame(){
        aSetter.setObject(mapC.diamondPlaces);
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

    int cnt=0;
    boolean finished=false;
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        //GROUND
        if(finished){
            //mapC.draw(g2);
            ui.draw(g2);
        }
        //GOAL tile
        cnt=0;
        for(Bonus element: obj){
            if(element!=null){
                cnt+=1;
            }
        }
        if(cnt==0){
            goal.collision=true;
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
