package main;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssetSetterTest {

    @Test
    void setObject() {
        JFrame frame=new JFrame();
        GamePanel gp=new GamePanel(20,17,frame);
        List<int[]> places = new ArrayList<>();
        places.add(new int[]{2, 3});
        places.add(new int[]{28, 4});
        places.add(new int[]{5, 9});
        places.add(new int[]{3, 2});
        places.add(new int[]{4, 5});
        places.add(new int[]{10, 9});
        AssetSetter as= new AssetSetter(gp);
        as.setObject(places);
        int unexpected=0;
        int cnt=0;
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i]!=null){
                cnt+=1;
            }
        }
        int actual= cnt;
        assertNotEquals(unexpected,actual);
    }
}