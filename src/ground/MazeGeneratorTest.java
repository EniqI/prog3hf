package ground;

import main.GamePanel;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazeGeneratorTest {

    @Test
    void getEnd() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,20,frame);

        MazeGenerator mg= new MazeGenerator(gp);
        int[] expected= {18,18};
        int[] actual= mg.getEnd();
        assertArrayEquals(expected,actual);
    }
/*
    @Test
    void generateMaze() {
        GamePanel gp=new GamePanel();
        gp.oldGame= false;
        MazeGenerator mg= new MazeGenerator(gp);
        mg.generateMaze(2,2);
        String expected= "S";
    }*/

    @Test
    void getNonWallTiles() {
        JFrame frame= new JFrame();
        GamePanel gp=new GamePanel(20,20,frame);

        MazeGenerator mg= new MazeGenerator(gp);
        mg.generateMaze(2,2);
        int unexpected= 0;
        List<int[]> nonwalltiles= mg.getNonWallTiles();
        int actual= nonwalltiles.size();
        assertNotEquals(unexpected,actual);
    }
}