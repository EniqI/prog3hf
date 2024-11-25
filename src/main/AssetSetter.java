package main;

import bonuses.Diamond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The AssetSetter class is responsible for placing game assets, such as Diamonds,
 * on the game board within the specified positions.
 */
public class AssetSetter {
    /**
     * The GamePanel instance used in the AssetSetter for performing various game-related operations
     * such as placing objects on the game board and interacting with game elements.
     */
    GamePanel gp;
    /**
     * Instance of the Random class used for generating random numbers,
     * primarily to determine the positions where game assets, such as Diamonds,
     * will be placed on the game board.
     */
    Random random = new Random();
    /**
     * List storing the coordinates of the places where diamonds have been placed on the game board.
     * Each element in the list is an array of integers, where the first element represents the x-coordinate
     * and the second element represents the y-coordinate relative to the game panel.
     */
    public List<int[]> diamondPlaces;

    /**
     * Constructs an AssetSetter instance which is responsible for placing game assets on the game board.
     *
     * @param gp the GamePanel instance that represents the game environment
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        diamondPlaces=new ArrayList<>();
    }

    /**
     * Places a specified number of Diamond objects randomly within the provided coordinates.
     *
     * @param whereToPut a list of integer arrays representing the possible positions (coordinates)
     *                   where the Diamond objects can be placed.
     */
    public void setObject(List<int[]> whereToPut) {
        int numberOfObjects = 2;
        if (whereToPut.size() < numberOfObjects) {
            System.err.println("Not enough available positions to place objects.");
            return;
        }

        for (int i = 0; i < numberOfObjects; i++) {
            // Randomly pick and remove a tile coordinate
            int randomIndex = random.nextInt(whereToPut.size());
            int[] tileCoords = whereToPut.remove(randomIndex);

            // Assign Diamond object
            gp.obj[i] = new Diamond();
            gp.obj[i].x = tileCoords[0] * gp.tileSize;
            gp.obj[i].y = tileCoords[1] * gp.tileSize;
            diamondPlaces.add(tileCoords);
        }
    }

    /**
     * Retrieves the list of positions where diamonds have been placed.
     *
     * @return a List of int arrays where each array contains the coordinates of a diamond's position.
     */
    public List<int[]> getDiamondPlaces(){
        return diamondPlaces;
    }
}
