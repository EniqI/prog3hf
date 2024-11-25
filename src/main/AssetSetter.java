package main;

import bonuses.Diamond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssetSetter {
    GamePanel gp;
    Random random = new Random();
    public List<int[]> diamondPlaces;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
        diamondPlaces=new ArrayList<>();
    }

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

    public List<int[]> getDiamondPlaces(){
        return diamondPlaces;
    }
}
