package main;

import bonuses.Diamond;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp=gp;
    }
public void setObject(){
    gp.obj[0] =new Diamond();
    gp.obj[0].x=4* gp.tileSize;
    gp.obj[0].y= 5* gp.tileSize;

    gp.obj[1]=new Diamond();
    gp.obj[1].x=11* gp.tileSize;
    gp.obj[1].y=5* gp.tileSize;
}
}
