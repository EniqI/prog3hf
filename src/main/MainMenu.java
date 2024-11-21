package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    JButton restart= new JButton("RESTART");;
    JButton newGame= new JButton("NEW GAME");;
    JLabel addName= new JLabel("ADD YOUR NAME");
    JTextField name= new JTextField(20);
    JLabel select= new JLabel("SELECT THE SIZE");
    String[] sizes= {"SMALL","MEDIUM","BIG"};
    JComboBox<String> gameSize= new JComboBox<>(sizes);
    JPanel buttonPanel= new JPanel();
    JPanel namePanel= new JPanel();
    JPanel sizePanel= new JPanel();
    String playerName;
    int gamePanelSizeX;
    int gamePanelSizeY;
    boolean oldGame;
    GamePanel gp= new GamePanel();

    public MainMenu(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Labirinth Extreme");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(new Dimension(400, 600));


        restart.addActionListener(this);

        newGame.addActionListener(this);
        restart.addActionListener(this);
        newGame= new JButton("NEW GAME");
        newGame.addActionListener(this);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(restart, BorderLayout.NORTH);
        buttonPanel.add(newGame, BorderLayout.SOUTH);
        namePanel.setLayout(new FlowLayout());
        namePanel.add(addName,BorderLayout.WEST);
        namePanel.add(name,BorderLayout.EAST);
        sizePanel.setLayout(new FlowLayout());
        sizePanel.add(select,BorderLayout.WEST);
        sizePanel.add(gameSize,BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(buttonPanel,BorderLayout.NORTH);
        this.add(namePanel,BorderLayout.CENTER);
        this.add(sizePanel,BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame window= new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Labirinth Extreme");

        GamePanel gamePanel= new GamePanel();
        window.add(gamePanel);
        window.setSize(gamePanel.screenWidth,gamePanel.screenHight);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        if(e.getSource()==restart){
            oldGame=true;
            //load file from map and game
        }else if(e.getSource()==newGame){
            oldGame=false;
            playerName= name.getText();
            switch (gameSize.getItemCount()){
                case 0:
                    gp.maxScreenCol= 12;
                    gp.maxScreenRow= 9;
                    break;
                case 1:
                    gp.maxScreenCol= 16;
                    gp.maxScreenRow= 12;
                    break;
                case 2:
                    gp.maxScreenCol= 20;
                    gp.maxScreenRow= 16;
                    break;
            }

            //generate new map, arraylist
        }

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
