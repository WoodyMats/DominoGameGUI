/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogui;

/**
 * A class that makes a set of Tiles
 *
 * @author John, Leos
 */
public class Set {
    
    protected Tile[] PackOfTiles = new Tile[28];
    int currIndex;

    /**
     * Constructor: Initializes the pack of 28 tiles.
     */
    public Set(){
        int index = 0;
        
        for(int i = Tile.minValue; i <= Tile.maxValue; i++){
            for(int j = i; j <= Tile.maxValue; j++){
                PackOfTiles[index] = new Tile(i,j);
                index++;
            }
        }
    }

    /**
     * Mix the tiles randomly.
     */
    public void Mixture(){
        
        for(int i=0; i<PackOfTiles.length;i++){
            Tile temp = PackOfTiles[i];
            int mixIndex = (int) (Math.random()*27);
            PackOfTiles[i] = PackOfTiles[mixIndex];
            PackOfTiles[mixIndex] = temp;
        }
    }

    /**
     * Finds if a tile is in the pack of tiles.
     * @param element the position of the tile we want to return.
     * @return Tile PackOfTiles at element's position if "element" is valid, or null if it's not.
     */
    public Tile getArrayElement(int element){
        if(element >= 0 && element<PackOfTiles.length){
            return PackOfTiles[element];
        }
        return null;
    }
    
}
