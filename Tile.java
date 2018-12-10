/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogui;

/**
 * A class that creates, displays and changes the sides of a tile
 *
 * @author John, Leos
 */
public class Tile {
    public static final int maxValue = 6;
    public static final int minValue = 0;
    
    private int leftValue = minValue;
    private int rightValue = minValue;

    /**
     * Constructor: Initialize the Tile.
     * @param LeftVal the min value
     * @param RightVal the max value
     */
    public Tile(int LeftVal, int RightVal){
        if(LeftVal >= minValue && LeftVal <= maxValue){
            
            leftValue = LeftVal;
        }
        
        if(RightVal >= minValue && RightVal <= maxValue){
            
            rightValue = RightVal;
        }
    }

    /** Getter
     * Gets the leftValue.
     * @return leftValue
     */
    public int getLeftVal() {
        return leftValue;
    }

    /** Getter
     * Gets the rightValue.
     * @return rightValue
     */
    public int getRightVal() {
        return rightValue;
    }

    /**
     * Displays the tile as a string.
     * @return String, the tile
     */
    @Override
    public String toString(){
        String left = "[" + leftValue;
        String right = rightValue + "]";
        
        return left + "|" + right;
    }

    /**
     * Changes the side of a tile
     */
    public void OtherSide(){
        int temp = leftValue;
        leftValue = rightValue;
        rightValue = temp;
    }
}
