package main;

import characters.Character;

public class CollisionChecker {
   GamePanel gp;
    public  CollisionChecker(GamePanel gp){
        this.gp= gp;
    }
    public void checkTile(Character character){
        int characterLeftX= character.x+ character.solidArea.x;
        int characterRightX= character.x+ character.solidArea.width;
        int characterTopY= character.y+ character.solidArea.y;
        int characterBottomY= character.y+ character.solidArea.height;

        int characterLeftCol= characterLeftX/gp.tileSize;
        int characterRightCol= characterRightX/gp.tileSize;
        int characterTopRow= characterTopY/gp.tileSize;
        int characterBottomRow= characterBottomY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (character.direction){
            case "up":
                characterTopRow= (characterTopY-character.speed)/gp.tileSize;
                tileNum1= gp.mapC.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2= gp.mapC.mapTileNum[characterRightCol][characterTopRow];
                if(gp.mapC.tile[tileNum1].collision || gp.mapC.tile[tileNum2].collision){
                    character.collisionOn=true;
                }
                break;
            case "down":
                characterBottomRow= (characterBottomY+character.speed)/gp.tileSize;
                tileNum1= gp.mapC.mapTileNum[characterLeftCol][characterBottomRow];
                tileNum2= gp.mapC.mapTileNum[characterRightCol][characterBottomRow];
                if(gp.mapC.tile[tileNum1].collision || gp.mapC.tile[tileNum2].collision){
                    character.collisionOn=true;
                }
                break;
            case "left":
                characterLeftCol= (characterLeftX-character.speed)/gp.tileSize;
                tileNum1= gp.mapC.mapTileNum[characterLeftCol][characterTopRow];
                tileNum2= gp.mapC.mapTileNum[characterLeftCol][characterBottomRow];
                if(gp.mapC.tile[tileNum1].collision || gp.mapC.tile[tileNum2].collision){
                    character.collisionOn=true;
                }
                break;
            case "right":
                characterRightCol= (characterRightX+character.speed)/gp.tileSize;
                tileNum1= gp.mapC.mapTileNum[characterRightCol][characterTopRow];
                tileNum2= gp.mapC.mapTileNum[characterRightCol][characterBottomRow];
                if(gp.mapC.tile[tileNum1].collision || gp.mapC.tile[tileNum2].collision){
                    character.collisionOn=true;
                }
                break;
        }
    }

}
