package main;

import bonuses.Bonus;
import bonuses.Goal;
import characters.Figure;
import ground.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements Runnable{
    public String playerName;
    public static final int originalTilesize= 16;
    final static int scale= 3;

    public static final int tileSize= originalTilesize* scale;
    public int maxScreenCol=20;
    public int maxScreenRow=17;
    public int screenWidth= tileSize* maxScreenCol;
    public int screenHight= tileSize* maxScreenRow;
    //FPS
    int FPS= 60;
    MapCreator mapC;
    KeyHandler keyH= new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker;
    public AssetSetter aSetter;
    public Figure figure;
    public Bonus obj[]= new Bonus[10];;
    public Goal goal;
    public UI ui;
    public boolean gameOver;
    boolean finished=false;
    JButton menuButton;
    public GamePanel(int maxScreenCol,int maxScreenRow,JFrame frame){
        this.maxScreenCol= maxScreenCol;
        this.maxScreenRow= maxScreenRow;
        this.screenWidth= maxScreenCol*tileSize;
        this.screenHight= maxScreenRow* tileSize;
        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        menuButton=new JButton("BACK TO MENU");
        menuButton.setVisible(false);
        this.add(menuButton,BorderLayout.SOUTH);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


        mapC= new MapCreator(this);
        cChecker= new CollisionChecker(this);
        aSetter= new AssetSetter(this);
        figure=new Figure(this,keyH);
        goal= new Goal();
        ui= new UI(this);
        gameOver = false;
        goal.setX((maxScreenCol-2)*tileSize);
        goal.setY((maxScreenRow-2)*tileSize);
    }
    /*public void setScreenSize(int maxScreenCol, int maxScreenRow) {
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        this.screenWidth= maxScreenCol* tileSize;
        this.screenHight= maxScreenRow* tileSize;
        this.setPreferredSize(new Dimension(screenWidth,screenHight));
    }*/
    //ha fileból olvasunk be, akkor betölti a fileból a helyüket, ha új game, akkor lekéri
    public void setUpGame(){
        aSetter.setObject(mapC.possibleDiamondPlaces);
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
            if (finished) {
                System.out.println("Game Completed!");
                endGameThread(); // Stop the game thread
            }
    }
    public boolean checkGoalCondition(){
        if(figure.getBonusPoints()>=2){
            return true;
        }
        return false;
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
        checkEndCondition();// Check if the game should end
        goal.collision= checkGoalCondition();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

            //mapC.draw(g2);
            // Draw the regular game elements
            for (Bonus element : obj) {
                if (element != null) {
                    element.draw(g2, this); // Bonuses
                }
            }
            figure.draw(g2); // Player
            if(figure.getBonusPoints()>=2){
                goal.draw(g2,this);
               // mapC.endTile.draw(g2,this);
            }
         if(finished) {
            // Display the winning message
            mapC.draw(g2);
            ui.draw(g2);
            //gombot hozzá
             menuButton.setVisible(true);
        }
        g2.dispose();

    }

}
