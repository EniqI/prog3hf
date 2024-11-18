package main;

import bonuses.Bonus;
import characters.Character;
import characters.Figure;

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
    public int checkBonus(Character character, boolean player){
        int returnValue= 999;
        int placeInArray=0;
        for(Bonus element: gp.obj){
            if(element!=null){
                //get entities solid area position
                character.solidArea.x= character.x+ character.solidArea.x;
                character.solidArea.y= character.y+ character.solidArea.y;
                //get the object's solid area
                element.solidArea.x= element.x+ element.solidArea.x;
                element.solidArea.y= element.y+ element.solidArea.y;

                switch (character.direction){
                    case "up":
                        character.solidArea.y -= character.speed;
                        if(character.solidArea.intersects(element.solidArea)){
                            if(element.collision){
                                character.collisionOn=true;
                            }
                            if(player){
                                returnValue=placeInArray;
                            }
                        }
                        break;
                    case "down":
                        character.solidArea.y += character.speed;
                        if(character.solidArea.intersects(element.solidArea)){
                            if(element.collision){
                                character.collisionOn=true;
                            }
                            if(player){
                                returnValue=placeInArray;
                            }
                        }
                        break;
                    case "left":
                        character.solidArea.x -= character.speed;
                        if(character.solidArea.intersects(element.solidArea)){
                            if(element.collision){
                                character.collisionOn=true;
                            }
                            if(player){
                                returnValue=placeInArray;
                            }
                        }
                        break;
                    case "right":
                        character.solidArea.x += character.speed;
                        if(character.solidArea.intersects(element.solidArea)){
                            if(element.collision){
                                character.collisionOn=true;
                            }
                            if(player){
                                returnValue=placeInArray;
                            }
                        }
                        break;
                }
                character.solidArea.x=character.solidAreaDefaultX;
                character.solidArea.y=character.solidAreaDefaultY;
                element.solidArea.x= element.solidAreaDefaultX;
                element.solidArea.y= element.solidAreaDefaultY;
            }
            placeInArray++;
        }
        return returnValue;
    }
    public void checkGoal(Character character, boolean player) {

        //get entities solid area position
        character.solidArea.x = character.x + character.solidArea.x;
        character.solidArea.y = character.y + character.solidArea.y;
        //get the object's solid area
        gp.goal.solidArea.x = gp.goal.x + gp.goal.solidArea.x;
        gp.goal.solidArea.y = gp.goal.y + gp.goal.solidArea.y;
        switch (character.direction) {
            case "up":
                character.solidArea.y -= character.speed;
                if (character.solidArea.intersects(gp.goal.solidArea)) {
                    if (gp.goal.collision) {
                        character.collisionOn = true;
                    }
                    if (player && character.bonusPoints>=2) {
                        gp.finished = true;
                    }
                }
                break;
            case "down":
                character.solidArea.y += character.speed;
                if (character.solidArea.intersects(gp.goal.solidArea)) {
                    if (gp.goal.collision) {
                        character.collisionOn = true;
                    }
                    if (player && character.bonusPoints>=2) {
                        gp.finished = true;
                    }
                }
                break;
            case "left":
                character.solidArea.x -= character.speed;
                if (character.solidArea.intersects(gp.goal.solidArea)) {
                    if (gp.goal.collision) {
                        character.collisionOn = true;
                    }
                    if (player && character.bonusPoints>=2) {
                        gp.finished = true;
                    }
                }
                break;
            case "right":
                character.solidArea.x += character.speed;
                if (character.solidArea.intersects(gp.goal.solidArea)) {
                    if (gp.goal.collision) {
                        character.collisionOn = true;
                    }
                    if (player && character.bonusPoints>=2) {
                        gp.finished = true;
                    }
                }
                break;
        }
        character.solidArea.x = character.solidAreaDefaultX;
        character.solidArea.y = character.solidAreaDefaultY;
        gp.goal.solidArea.x = gp.goal.solidAreaDefaultX;
        gp.goal.solidArea.y = gp.goal.solidAreaDefaultY;
    }
}
