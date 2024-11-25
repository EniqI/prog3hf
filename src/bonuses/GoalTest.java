package bonuses;

import main.GamePanel;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
class GoalTest {

    @Test
    void setX() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        int xPosition= gp.goal.x;
        gp.goal.setX(13);
        int actual= gp.goal.x;
        int expected=13;
        assertEquals(expected,actual);
    }
    @Test
    void setX2() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        int xPosition= gp.goal.x;
        gp.goal.setX(13);
        int actual= gp.goal.x;
        assertNotEquals(xPosition,actual);
    }

    @Test
    void setY() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        gp.goal.setY(13);
        int actual= gp.goal.y;
        int expected=13;
        assertEquals(expected,actual);
    }
    @Test
    void setY2() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        int yPosition= gp.goal.y;
        gp.goal.setY(13);
        int actual= gp.goal.y;
        assertNotEquals(yPosition,actual);
    }
}