/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogui;

import java.util.ArrayList;

/**
 * A class that creates a player and sets name, score, tiles in hand
 *
 * @author John, Leos
 */
public class Player {
    private String name;
    private int score;
    private ArrayList<Tile> Hand = new ArrayList<>(12);

    /**
     * Plain Constructor: Initializes the player's name to "Player" and set the score to 0.
     */
    public Player(){
        name = "Player";
        score = 0;
    }

    /**
     * Constructor with param: Initializes the name of the player and sets score to 0
     * @param name the player's name.
     */
    public Player(String name){
        score = 0;
        this.name = name;
    }

    /**
     * Getter
     * Gets the name from the user
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**Getter
     * Gets the score
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases the score of the player
     * @param scoreToAdd Player's points
     */
    public void addScore(int scoreToAdd){
        score += scoreToAdd;
    }

    /**
     * Sets score to 0. (Clear the score)
     */
    public void clearScore(){
        score = 0;
    }

    /**
     * Clears the hand of the player.
     * Remove all remaining tiles from Hand ArrayList.
     */
    public void clearHand(){
        Hand.clear();
    }

    /**
     * Gets a specific tile that is in the hand of the player
     * @param index the position of the tile in player's hand.
     * @return Tile
     */
    public Tile getHandTile(int index){
        return Hand.get(index);
    }

    /**
     * Counts the number of the player's tiles to his hand.
     * @return the number of tiles
     */
    public int numOfHandTiles(){return Hand.size();}

    /**
     * Finds a tile which it's left or right value is equal to the key value.
     * @param key value of the tile we want to find.
     * @return int, the index of the tile or -1 if tile doesn't exists.
     */
    public int findTile(int key) {
        for(int i=0; i<Hand.size();i++){
            Tile tempTile = getHandTile(i);
            if(key == tempTile.getLeftVal() || key == tempTile.getRightVal()){
                return i;
            }
        }
        return -1;
    }

    /**
     * Deletes the tile from player's hand.
     * @param index the position of the tile we want to remove from players hand.
     * @return Tile, the tile that was deleted or null if index is invalid.
     */
    public Tile removeTile(int index){
        if(index >= 0 && index < Hand.size()){
            Tile tempTile = getHandTile(index);
            Hand.remove(index);
            return tempTile;
        }
        else{
            return null;
        }
    }

    /**
     * Adds a tile to the player's Hand ArrayList
     * @param domino The domino we want to add at player's hand.
     * @return true if tile was added or false if it was not
     */
    public boolean addToHand(Tile domino){
        if(domino != null){
            Hand.add(domino);
            return true;
        }
        return false;
    }
    
    /**
     * Finds the biggest double player's tile.
     * @return int the maximum value of the double tile that player keeps to his hand.
     */
    public int getBiggerDouble(){
        boolean flag = false;
        int biggerDouble = 0;
        for(Tile x: Hand){
            if(flag == false && x.getLeftVal() == x.getRightVal()){
                biggerDouble = x.getLeftVal();
                flag = true;
            }
            if(flag == true && x.getLeftVal() == x.getRightVal()){
                if(x.getLeftVal()> biggerDouble){
                    biggerDouble = x.getLeftVal();
                }
            }
        }
        return biggerDouble;
    }
    
    /**
     * Calculate the points, that player has to his hand.
     * @return (int)pointsToGive the number of the points.
     */
    public int pointsToGive(){
        int pointsToGive = 0;
        for(int i=0;i<Hand.size();i++){
            Tile temp = Hand.get(i);
            pointsToGive += temp.getLeftVal()+temp.getRightVal();
        }
        return pointsToGive;
    }
    

    /**
     * Displays the player's name, tiles and score
     * @return String, player's info
     */
    public String DisplayPlayersInfo(){
        String playerInfo = getName() + ": ";
        StringBuilder sb = new StringBuilder();
        
        for(Tile temp: Hand){
            sb.append(temp.toString());
        }
        return playerInfo + sb.toString();
    }
}
