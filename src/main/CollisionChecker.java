package main;

import bonuses.Bonus;
import characters.Character;
import characters.Figure;

/**
 * The CollisionChecker class is responsible for handling collision detection
 * between characters and tiles, bonuses, or goals within the game panel.
 */
public class CollisionChecker {
   /**
    * The GamePanel instance associated with the CollisionChecker.
    * This variable allows the CollisionChecker to interact with the overall game state and UI elements
    * provided by the GamePanel.
    */
   GamePanel gp;
    /**
     * Constructor for the CollisionChecker class.
     *
     * @param gp The GamePanel instance to associate with this CollisionChecker.
     */
    public  CollisionChecker(GamePanel gp){
        this.gp= gp;
    }
    /**
     * Checks the tile collisions for the given character based on its current position and direction.
     * It determines which tiles the character is trying to move into and sets the collision flag
     * if any of those tiles are impassable.
     *
     * @param character the character whose tile collisions are to be checked
     */
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
    /**
     * Checks for collisions between a character and bonus objects present in the game panel.
     * Depending on the character's direction, it updates the character's position and determines
     * if it intersects with any bonus objects.
     *
     * @param character the character whose collision with bonus objects is to be checked
     * @param player a boolean indicating if the character is a player
     * @return the index of the bonus object that the character collides with, or 999 if no collision occurs
     */
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
    /**
     * Checks if the character has reached the goal and updates the game state accordingly.
     *
     * This method adjusts the character's solid area based on its current direction and speed,
     * checks for intersections with the goal area's solid area, and updates the
     * character's collision status and the game panel's finished status if necessary.
     *
     * @param character the character whose position is being checked against the goal.
     * @param player indicates if the character is controlled by the player.
     */
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
                    if (player && gp.figure.getBonusPoints()==2) {
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
                    if (player &&  gp.figure.getBonusPoints()==2) {
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
                    if (player &&  gp.figure.getBonusPoints()==2) {
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
                    if (player &&  gp.figure.getBonusPoints()==2) {
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
