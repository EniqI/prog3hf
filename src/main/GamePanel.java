package main;

import bonuses.Bonus;
import bonuses.Goal;
import characters.Figure;
import ground.MapCreator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public String playerName;
    public boolean oldGame;
    public static final int originalTilesize= 16;
    final static int scale= 3;

    public static final int tileSize= originalTilesize* scale;
    public int maxScreenCol= 20;
    public int maxScreenRow= 17;
    public int screenWidth= tileSize* maxScreenCol;
    public int screenHight= tileSize* maxScreenRow;
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
    public boolean gameOver = false;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setScreenSize(int maxScreenCol, int maxScreenRow) {
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        this.screenWidth= maxScreenCol* tileSize;
        this.screenHight= maxScreenRow* tileSize;
        this.setPreferredSize(new Dimension(screenWidth,screenHight));
    }
    //ha fileból olvasunk be, akkor betölti a fileból a helyüket, ha új game, akkor lekéri
    public void setUpGame(){
        aSetter.setObject(mapC.diamondPlaces);
    }

    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    public void endGameThread() {
        if (gameThread != null) {
            gameThread = null; // Stop the thread
            gameOver = true;   // Set the game over flag
            System.out.println("Game thread ended.");
        }
    }


    public void checkEndCondition() {
        int[] endCoordinates = mapC.mazeGenerator.getEnd();
        if (figure.x == endCoordinates[0] * tileSize && figure.y == endCoordinates[1] * tileSize) {
            System.out.println("Game Completed!");
            mapC.saveMap();
            endGameThread(); // Stop the game thread
        }
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) { // Loop will stop when gameThread is null
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
        System.out.println("Game loop terminated.");
    }

    public void update() {
        figure.update();
        checkEndCondition(); // Check if the game should end
    }


    int cnt=0;
    boolean finished=false;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (!gameOver) {
            // Draw the regular game elements
            mapC.draw(g2); // Ground
            for (Bonus element : obj) {
                if (element != null) {
                    element.draw(g2, this); // Bonuses
                }
            }
            figure.draw(g2); // Player
        } else {
            // Display the winning message
            ui.draw(g2);
        }
        g2.dispose();
    }

}
