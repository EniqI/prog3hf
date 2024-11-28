package main;

import bonuses.Bonus;
import bonuses.Goal;
import characters.Figure;
import ground.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GamePanel is a custom JPanel that runs a game loop and handles all the
 * main game functionality, including rendering and updating game states.
 *
 * The panel can be initialized with custom dimensions, and encapsulates
 * the logic for setting up the game, starting and ending the game thread,
 * updating game states, and rendering game components.
 *
 * In addition, it handles user input through a KeyHandler, manages
 * collision detection, and allows for the setting and rendering of various game assets.
 *
 * Fields:
 * - playerName: String representing the player's name.
 * - originalTilesize: Constant representing the original tile size.
 * - scale: Constant representing the scaling factor for the tile size.
 * - tileSize: Constant representing the final tile size after scaling.
 * - maxScreenCol: Maximum number of columns on the screen.
 * - maxScreenRow: Maximum number of rows on the screen.
 * - screenWidth: Width of the screen in pixels.
 * - screenHight: Height of the screen in pixels.
 * - FPS: Frames per second.
 * - mapC: Instance of MapCreator responsible for map creation.
 * - keyH: Instance of KeyHandler for handling keyboard input.
 * - gameThread: Thread running the game loop.
 * - cChecker: CollisionChecker for detecting collisions.
 * - aSetter: AssetSetter for setting game assets.
 * - figure: The player's character.
 * - obj: Array of Bonus objects.
 * - goal: The goal object in the game.
 * - ui: User interface component.
 * - gameOver: Boolean flag indicating if the game is over.
 * - finished: Boolean flag indicating if the game has been completed.
 * - menuButton: JButton for returning to the menu.
 *
 * Constructor:
 * - GamePanel(int maxScreenCol, int maxScreenRow, JFrame frame): Initializes the game panel with custom dimensions and sets up the initial game configurations.
 *
 * Methods:
 * - setUpGame(): Sets up the game by placing objects at possible diamond locations.
 * - startGameThread(): Starts the game thread and begins the game loop.
 * - endGameThread(): Stops the game thread and signals that the game is over.
 * - checkEndCondition(): Checks if the game has been completed and ends the game thread if it has.
 * - checkGoalCondition(): Checks if the player has collected enough bonus points to meet the goal.
 * - run(): The main game loop that updates and repaints game components at a fixed interval based on FPS.
 * - update(): Updates the game state, including the player's character and the goal condition.
 * - paintComponent(Graphics g): Renders the game components, including the map, bonuses, player character, and UI.
 */
public class GamePanel extends JPanel implements Runnable{
    /**
     * The `playerName` variable holds the name of the player.
     * This name is used within the game to identify the player and personalize the gaming experience.
     */
    public String playerName;
    /**
     * Represents the original size of a tile in the game, measured in pixels.
     * It is used as a base measurement for scaling tiles to different sizes
     * within the game's graphical interface.
     */
    public static final int originalTilesize= 16;
    /**
     * Represents the scale factor for rendering elements within the GamePanel.
     * This scale factor*/
    final static int scale= 3;

    /**
     * Represents the size of each tile in the game, scaled according to the original tile size and the specified scale factor.
     * This value is used throughout the game to maintain consistent tile dimensions for rendering and collisions.
     */
    public static final int tileSize= originalTilesize* scale;
    /**
     * The maximum number of columns displayed on the game screen.
     */
    public int maxScreenCol=20;
    /**
     * Represents the maximum number of rows that can be displayed on the game screen.
     */
    public int maxScreenRow=17;
    /**
     * Holds the screen width of the game display.
     * The value is calculated by multiplying the tile size with the maximum number of columns.
     */
    public int screenWidth= tileSize* maxScreenCol;
    /**
     * The height of the game screen in pixels.
     *
     * This value is calculated by multiplying the size of each tile (tileSize)
     * by the maximum number of tile rows (maxScreenRow) that can fit on the screen.
     *
     * The screenHight variable is essential for defining the vertical dimension
     * of the game's graphical user interface and is used throughout the code to
     * ensure consistent rendering and layout of game elements.
     */
    public int screenHight= tileSize* maxScreenRow;
    /**
     * The target frames per second for rendering the game's graphics. A higher frame
     * rate results in smoother animations and gameplay, while a lower frame rate
     * may improve performance on slower hardware.
     */
    //FPS
    int FPS= 60;
    /**
     * The mapC instance of the MapCreator class is responsible for creating and managing
     * the game map within the GamePanel. It handles tasks such as loading tile images,
     * generating mazes, and placing key game elements like diamonds and the end goal.
     */
    MapCreator mapC;
    /**
     * Instance of KeyHandler to manage keyboard inputs.
     */
    KeyHandler keyH= new KeyHandler();
    /**
     * Represents the main game loop thread responsible for continuously updating and rendering
     * the game state at a fixed frame rate. The thread ensures smooth gameplay by invoking
     * update and rendering methods in a controlled loop*/
    Thread gameThread;
    /**
     * The CollisionChecker instance used to handle collision detection within the game.
     * This variable is responsible for checking interactions between game objects.
     */
    public CollisionChecker cChecker;
    /**
     * Instance of the AssetSetter class used for placing game assets, such as Diamonds,
     * on the game board within the specified positions.
     */
    public AssetSetter aSetter;
    /**
     * The player's character in the game, which can move and interact with the game world.
     */
    public Figure figure;
    /**
     * An array to store Bonus objects in the game.
     * The array has a fixed size of 10 and can contain up to 10 Bonus items.
     * Each Bonus item represents a special object in the game with its own properties and behaviors.
     */
    public Bonus obj[]= new Bonus[10];;
    /**
     * The goal is the target object in the game that the player needs to reach or interact with.
     * This variable holds an instance of the Goal class, which extends the Bonus class and contains
     * attributes and methods related to the goal's properties and behavior within the game.
     */
    public Goal goal;
    /**
     * The UI object responsible for managing and rendering the user interface
     * within the GamePanel class. It interacts with the game state and provides
     * visual feedback to the player, such as the end game message.
     */
    public UI ui;
    /**
     *
     */
    public boolean gameOver;
    /**
     * Indicates whether the game has finished.
     * This boolean variable is used to determine if the game has reached its end state.
     * It is initially set to false and can be toggled to true when the game concludes.
     */
    boolean finished=false;
    /**
     * The menuButton is a JButton used within the GamePanel to provide users
     * with an interactive button element, typically related to menu navigation
     * or actions within the game's user interface.
     */
    JButton menuButton;
    /**
     *
     */
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
        goal.setX(((maxScreenCol-2)*tileSize));
        goal.setY(((maxScreenRow-2)*tileSize));
    }
    /*public void setScreenSize(int maxScreenCol, int maxScreenRow) {
        this.maxScreenCol = maxScreenCol;
        this.maxScreenRow = maxScreenRow;
        this.screenWidth= maxScreenCol* tileSize;
        this.screenHight= maxScreenRow* tileSize;
        this.setPreferredSize(new Dimension(screenWidth,screenHight));
    }*/

    /**
     * Sets up the game by initializing the positions for possible Diamond objects.
     * This method configures the game state by placing Diamond objects at
     * potential locations defined in the game map.
     */
    public void setUpGame(){
        aSetter.setObject(mapC.possibleDiamondPlaces);
    }

    /**
     * Starts the game thread by initializing a new Thread with this object and starting it.
     * This method is typically called after the game setup is complete.
     */
    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();
    }

    /**
     * Ends the game thread and sets the game over flag. If the game thread is active,
     * it terminates the thread and sets the game over status to true.
     * Additionally, it outputs a message to the console indicating the game thread has ended.
     */
    public void endGameThread() {
        if (gameThread != null) {
            gameThread = null; // Stop the thread
            gameOver = true;   // Set the game over flag
            System.out.println("Game thread ended.");
        }
    }

    /**
     * Checks if the end condition for the game has been met.
     * If the game is finished, it prints a completion message
     * and stops the game thread.
     */
    public void checkEndCondition() {
            if (finished) {
                System.out.println("Game Completed!");
                endGameThread(); // Stop the game thread
            }
    }
    /**
     * Checks if the goal condition is met based on the figure's bonus points.
     * The*/
    public boolean checkGoalCondition(){
        if(figure.getBonusPoints()>=2){
            return true;
        }
        return false;
    }
    /**
     * The primary game loop method, invoked when the game thread starts.
     * Controls the timing for game updates and rendering.
     *
     * The loop continuously calculates the time difference between iterations
     * to ensure the game updates and renders at a consistent frame rate defined by the FPS variable.
     */
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

    /**
     * Updates the game state by performing the following actions:
     * 1. Updates the state of the figure.
     * 2. Checks if the game should end, and if so, stops the game thread.
     * 3. Checks and updates the goal condition based on the figure's bonus points.
     */
    public void update() {
        figure.update();
        checkEndCondition();// Check if the game should end
        goal.collision= checkGoalCondition();
    }

    /**
     * Custom rendering for the game panel which includes drawing bonuses, the player, goal,
     * and additional UI elements if the game has finished.
     *
     * @param g the Graphics context in which to paint, provided by Swing.
     */
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
