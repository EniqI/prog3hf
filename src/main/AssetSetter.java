package main;

import bonuses.Diamond;
import ground.MapCreator;

import java.util.List;
import java.util.Random;

public class AssetSetter {
    GamePanel gp;

    int x,y;
    Random random= new Random();

    public AssetSetter(GamePanel gp){
        this.gp= gp;
    }
    public void setObject(List<int[]> whereToPut){

        for(int i=0; i< 2;i++) {
           int randomIndex = random.nextInt(whereToPut.size());
           int[] tileCoords = whereToPut.remove(randomIndex);
           x= tileCoords[0];
           y= tileCoords[1];
           gp.obj[i] = new Diamond();
           gp.obj[i].x = x * gp.tileSize;
           gp.obj[i].y = y * gp.tileSize;
       }
    }

}
