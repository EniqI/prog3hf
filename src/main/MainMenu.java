package main;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenu extends JFrame implements ActionListener {
    JButton menuButton= new JButton("MENU");
    JButton newGame = new JButton("NEW GAME");
    JLabel addName = new JLabel("ADD YOUR NAME");
    JTextField name = new JTextField(20);
    JLabel select = new JLabel("SELECT THE SIZE");
    String[] sizes = {"SMALL", "MEDIUM", "BIG"};
    JComboBox<String> gameSize = new JComboBox<>(sizes);
    JPanel buttonPanel = new JPanel();
    JPanel namePanel = new JPanel();
    JPanel sizePanel = new JPanel();
    GamePanel gp;

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
