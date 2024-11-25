package main;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
class GamePanelTest {

    @Test
    void setUpGame() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        int cnt=0;
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i]!=null){
                cnt+=1;
            }
        }
        int unexpected=cnt;
        gp.setUpGame();
        cnt=0;
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i]!=null){
                cnt+=1;
            }
        }
        int actual=cnt;
        assertNotEquals(unexpected,actual);

    }

    @Test
    void checkEndCondition() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        boolean expected=false;
        boolean actual= gp.finished;
        assertEquals(expected,actual);
    }

    @Test
    void checkGoalCondition() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        boolean expected=false;
        boolean actual=gp.checkGoalCondition();
        assertEquals(expected,actual);
    }
}