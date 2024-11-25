package main;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenu extends JFrame implements ActionListener {

    JButton restart = new JButton("RESTART");
    JButton newGame = new JButton("NEW GAME");
    JLabel addName = new JLabel("ADD YOUR NAME");
    JTextField name = new JTextField(20);
    JLabel select = new JLabel("SELECT THE SIZE");
    String[] sizes = {"SMALL", "MEDIUM", "BIG"};
    JComboBox<Object> gameSize = new JComboBox<>(sizes);
    JPanel buttonPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel sizePanel = new JPanel();

    GamePanel gp;

    public MainMenu() {
        gp = new GamePanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Labirinth Extreme");
        this.setSize(new Dimension(400, 200));
        this.setLocationRelativeTo(null);

        newGame.addActionListener(this);
        restart.addActionListener(this);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(restart);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart) {
            gp.oldGame = true;
            gp.playerName = name.getText().trim();
            if (gp.playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
                return;
            }
            // Load game state
        } else if (e.getSource() == newGame) {
            gp.oldGame = false;
            gp.playerName = name.getText().trim();
            if (gp.playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
                return;
            }
            String whichSize = (String) gameSize.getSelectedItem();
            switch (whichSize) {
                case "SMALL" -> {
                    gp.setScreenSize(12,9);
                }
                case "MEDIUM" -> {
                    gp.setScreenSize(16,12);
                }
                case "BIG" -> {
                    gp.setScreenSize(20,16);
                }
                default ->{gp.setScreenSize(20,20);}
            }
        }

        this.setVisible(false); // Hide the main menu
        JFrame gameWindow = new JFrame();
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
