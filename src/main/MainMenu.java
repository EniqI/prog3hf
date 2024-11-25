package main;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * The MainMenu class represents the main menu interface for the "Labirinth Extreme" game.
 * It allows the user to set up a new game by providing a name and selecting the game size.
 * The main menu consists of several GUI components including buttons, labels, text fields,
 * and combo boxes to input user details and preferences.
 *
 * This class extends JFrame and implements ActionListener to handle user interactions.
 *
 * The available game sizes are:
 * - SMALL
 * - MEDIUM
 * - BIG
 *
 * Once the user inputs their name and selects the game size, they can start a new game,
 * and a new game window will be displayed with the selected configuration.
 */
public class MainMenu extends JFrame implements ActionListener {
    /**
     * Represents a button labeled*/
    JButton menuButton= new JButton("MENU");
    /**
     * JButton used to start a new game.
     */
    JButton newGame = new JButton("NEW GAME");
    /**
     * A JLabel instance used to prompt the user to add their name.
     * It displays the text "ADD YOUR NAME".
     */
    JLabel addName = new JLabel("ADD YOUR NAME");
    /**
     *
     */
    JTextField name = new JTextField(20);
    /**
     * A JLabel component that displays the prompt for the user
     * to select the size of the game or application component
     * within*/
    JLabel select = new JLabel("SELECT THE SIZE");
    /**
     *
     */
    String[] sizes = {"SMALL", "MEDIUM", "BIG"};
    /**
     * A combo box component in the main menu that allows the user to select the size of the game.
     * The list of sizes available for selection are provided by the array `sizes`.
     */
    JComboBox<String> gameSize = new JComboBox<>(sizes);
    /**
     * A JPanel that contains*/
    JPanel buttonPanel = new JPanel();
    /**
     * A JPanel component used in the MainMenu class.
     * This panel is dedicated to handling and displaying name-related inputs.
     */
    JPanel namePanel = new JPanel();
    /**
     *
     */
    JPanel sizePanel = new JPanel();
    /**
     * The GamePanel instance representing the main panel where the game is rendered.
     */
    GamePanel gp;

    /**
     * The MainMenu class initializes and*/
    public MainMenu() {
        //gp = new GamePanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Labirinth Extreme");
        this.setSize(new Dimension(400, 200));
        this.setLocationRelativeTo(null);

        newGame.addActionListener(this);
        menuButton.addActionListener(this);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(newGame);

        namePanel.setLayout(new FlowLayout());
        namePanel.add(addName);
        namePanel.add(name);

        sizePanel.setLayout(new FlowLayout());
        sizePanel.add(select);
        sizePanel.add(gameSize);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(namePanel);
        mainPanel.add(sizePanel);
        mainPanel.add(buttonPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Handles action events triggered by user interaction. Specifically, it manages
     * the creation of a new game based*/
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        String nameAdded= name.getText().trim();
        String whichSize = (String) gameSize.getSelectedItem();
        JFrame gameWindow = new JFrame();
        if (e.getSource() == newGame) {

            if (nameAdded.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
                return;
            }

            switch (whichSize) {
                case "SMALL" -> {
                    gp = new GamePanel(12, 9, gameWindow);
                }
                case "MEDIUM" -> {
                    gp = new GamePanel(16, 12, gameWindow);
                }
                case "BIG" -> {
                    gp = new GamePanel(20, 16, gameWindow);
                }
                default -> {
                    gp = new GamePanel(20, 17, gameWindow);
                }
            }

            gp.playerName = nameAdded;
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameWindow.setResizable(true);
            gameWindow.setTitle("Labirinth Extreme");
            gameWindow.add(gp);
            gameWindow.setSize(gp.screenWidth, gp.screenHight);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setVisible(true);
            gp.setUpGame();
            gp.startGameThread();
        }
    }
}
