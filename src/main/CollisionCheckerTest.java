package main;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCheckerTest {

    @Test
    void checkTile() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(5,5,frame);
        gp.cChecker.checkTile(gp.figure);
        boolean actual= gp.figure.collisionOn;
        boolean expected=false;
        assertEquals(expected,actual);


    }

    @Test
    void checkBonus() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(5,5,frame);
        int expected=999;
        int actual= gp.cChecker.checkBonus(gp.figure,true);
        assertEquals(expected,actual);

    }

    @Test
    void checkGoal() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(7,6,frame);
        boolean expected=false;
        gp.cChecker.checkGoal(gp.figure,true);
        boolean actual=gp.finished;
        assertEquals(expected,actual);
    }
}