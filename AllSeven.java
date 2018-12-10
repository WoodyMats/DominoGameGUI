/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogui;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 * @author John, Leos
 */
public class AllSeven {

    private Set dominos;

    private ArrayList<Tile> tableTiles;
    private ArrayList<Tile> stack;

    protected Player player1;
    protected Player bot;

    public AllSeven() {
        dominos = new Set();
        player1 = new Player();
        bot = new Player("Computer");
        tableTiles = new ArrayList();
        stack = new ArrayList();
    }

    /**
     * Initialize the game.
     *
     * Shuffle the pack of dominoes, remove all the tiles from table, player's
     * and computer's hand, set player's and computer's score to 0.
     */
    public void initGame() {
        dominos.Mixture();
        tableTiles.clear();
        player1.clearHand();
        player1.clearScore();
        bot.clearHand();
        bot.clearScore();
        stack.clear();
        deal();
    }

    /**
     * Deal the tiles to players and fills the stack.
     */
    public void deal() {
        while (true) {
            for (int i = 0; i < 7; i++) {
                Tile temp = dominos.getArrayElement(i);
                if (temp != null) {
                    player1.addToHand(temp);
                } else {
                    throw new IllegalArgumentException("Fail to add domino tile to your hand.");
                }
            }
            for (int i = 7; i < 14; i++) {
                Tile temp = dominos.getArrayElement(i);
                if (temp != null) {
                    bot.addToHand(temp);
                } else {
                    throw new IllegalArgumentException("Fail to add domino tile to computer's hand.");
                }
            }
            for (int i = 14; i < 28; i++) {
                Tile temp = dominos.getArrayElement(i);
                stack.add(temp);
            }
            if (player1.numOfHandTiles() == 7 && bot.numOfHandTiles() == 7) {
                return;
            }
        }
    }
    
    /**
     * Gets a specific tile that is in the stack.
     * @param index the tile which added to player's hand
     * @return Stack's Tile
     */
    public Tile getStackTile(int index){
        return stack.get(index);
    }
    
    /**
     * Deletes the tile from stack.
     * @param index the tile which removed from stack.
     */
    public void removeTile(Tile index){
        stack.remove(index);
    }
    
    /**
     * 
     * @return int, the size of the Stack.
     */
    public int getStackSize(){
        return stack.size();
    }

    /**
     * If can be Left move this method add the tile toPlayed to the tableTiles
     * ArrayList and returns true.
     *
     * @param toPlayed (Tile which player chose to play.)
     * @return true if tile can be placed on the left and false if not.
     */
    public boolean canLeftMove(Tile toPlayed) {
        if (tableTiles.isEmpty()) {
            tableTiles.add(toPlayed);
            return true;
        }

        if ((toPlayed.getLeftVal() + toPlayed.getRightVal() == 7) || (toPlayed.getLeftVal() == 0 && toPlayed.getRightVal() == 0)) {
            tableTiles.add(0, toPlayed);
            return true;
        }

        Tile firstTile = tableTiles.get(0);

        if ((firstTile.getLeftVal() + toPlayed.getLeftVal()) == 7) {
            toPlayed.OtherSide();
            tableTiles.add(0, toPlayed);
            return true;
        }
        if ((firstTile.getLeftVal() + toPlayed.getRightVal()) == 7) {
            tableTiles.add(0, toPlayed);
            return true;
        }
        return false;
    }

    /**
     * If can be Right move this method add the tile toPlayed to the tableTiles
     * ArrayList and returns true.
     *
     * @param toPlayed (Tile which player chose to play.)
     * @return true if tile can be placed on the right and false if not.
     */
    public boolean canRightMove(Tile toPlayed) {
        if (tableTiles.isEmpty()) {
            tableTiles.add(toPlayed);
            return true;
        }

        if ((toPlayed.getLeftVal() + toPlayed.getRightVal() == 7) || (toPlayed.getLeftVal() == 0 && toPlayed.getRightVal() == 0)) {
            tableTiles.add(toPlayed);
            return true;
        }

        Tile lastTile = tableTiles.get(tableTiles.size() - 1);

        if ((lastTile.getRightVal() + toPlayed.getLeftVal()) == 7) {
            tableTiles.add(tableTiles.size(), toPlayed);
            return true;
        }
        if ((lastTile.getRightVal() + toPlayed.getRightVal()) == 7) {
            toPlayed.OtherSide();
            tableTiles.add(tableTiles.size(), toPlayed);
            return true;
        }
        return false;
    }

    /**
     * Checks if bot can make move.
     *
     * @return true if there is a possible move that bot can do and false if
     * not.
     */
    public boolean botMove() {
        if (tableTiles.isEmpty()) {
            tableTiles.add(bot.getHandTile(0));
            bot.removeTile(0);
            return true;
        }
        
        for(int i=0;i<bot.numOfHandTiles();i++){
            if(canLeftMove(bot.getHandTile(i))){
                bot.removeTile(i);
                return true;
            }
            if(canRightMove(bot.getHandTile(i))){
                bot.removeTile(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Show the table tiles using a string builder.
     *
     * @return The row with the played Tiles
     */
    public String showTableTiles() {

        StringBuilder sb = new StringBuilder();
        for (Tile x : tableTiles) {
            sb.append(x.toString());
        }
        return sb.toString();
    }

    /**
     * Calculate how many points has every player and adds the opponent hand's
     * points to the player with the minimum points at his hand.
     */
    public void setScore() {
        if (player1.pointsToGive() < bot.pointsToGive()) {
            player1.addScore(bot.pointsToGive() - player1.pointsToGive());
        }
        if (player1.pointsToGive() > bot.pointsToGive()) {
            bot.addScore(player1.pointsToGive() - bot.pointsToGive());
        }
    }

    /**
     * Checks if there is any possible move for player to do.
     *
     * @return true if there is and false if not.
     */
    public boolean checkPlayer() {
        if(tableTiles.isEmpty()) {return true;}
        
        if (player1.numOfHandTiles() == 0) {
            return false;
        }
        
        Tile firstTile = tableTiles.get(0);
        Tile lastTile = tableTiles.get(tableTiles.size()-1);
        
        for(int i=0;i<player1.numOfHandTiles();i++){
            Tile temp = player1.getHandTile(i);
            if((temp.getLeftVal() + temp.getRightVal() == 7) || (temp.getLeftVal() == 0 && temp.getRightVal() == 0)){
                return true;
            }
            if((temp.getLeftVal() + firstTile.getLeftVal() == 7) || (firstTile.getLeftVal() + temp.getRightVal() == 7)){
                return true;
            }
            if((lastTile.getRightVal() + temp.getLeftVal() == 7) || (lastTile.getRightVal() + temp.getRightVal() == 7)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is any possible move for player to do.
     *
     * @return true if there is and false if not.
     */
    public boolean checkBot() {
        if (bot.numOfHandTiles() == 0) {
            return false;
        }
        
        Tile firstTile = tableTiles.get(0);
        Tile lastTile = tableTiles.get(tableTiles.size()-1);
        
        for(int i=0;i<bot.numOfHandTiles();i++){
            Tile temp = bot.getHandTile(i);
            if((temp.getLeftVal() + temp.getRightVal() == 7) || (temp.getLeftVal() == 0 && temp.getRightVal() == 0)){
                return true;
            }
            if((temp.getLeftVal() + firstTile.getLeftVal() == 7) || (firstTile.getLeftVal() + temp.getRightVal() == 7)){
                return true;
            }
            if((lastTile.getRightVal() + temp.getLeftVal() == 7) || (lastTile.getRightVal() + temp.getRightVal() == 7)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if there are Tiles in stack that player or bot can add to their hands and play.
     * @param stackButtonPressed is a flag (different for bot and player) which shows if the "Stack" button has pressed.
     * @return false if Stack button has pressed for this round or if the size of the stack is lower or equal to 2 or if there isn't any possible move and true if there is!
     */
    public boolean avaliableMove(boolean stackButtonPressed){
        if(stackButtonPressed) {return false;}
        
        if(stack.size() <=2 ) {return false;}
        
        if(player1.numOfHandTiles() == 0) {return false;}
        
        Tile firstTile = tableTiles.get(0);
        Tile lastTile = tableTiles.get(tableTiles.size()-1);
        
        for(int i=0; i<stack.size()-2;i++){
            Tile temp = stack.get(i);
            if((temp.getLeftVal() + temp.getRightVal() == 7) || (temp.getLeftVal() == 0 && temp.getRightVal() == 0)){
                return true;
            }
            if((temp.getLeftVal() + firstTile.getLeftVal() == 7) || (firstTile.getLeftVal() + temp.getRightVal() == 7)){
                return true;
            }
            if((lastTile.getRightVal() + temp.getLeftVal() == 7) || (lastTile.getRightVal() + temp.getRightVal() == 7)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * After finishing the match this method shows the results of player and
     * computer.
     *
     * @return a string that shows the results.
     */
    public String displayResults() {
        ResourceBundle bundle = ResourceBundle.getBundle("dominogui.MessageBundles");
        
        String resultsToDisplay = "";

        if (player1.getScore() > bot.getScore()) {
            resultsToDisplay = bundle.getString("wonMessage");
        }
        if (player1.getScore() < bot.getScore()) {
            resultsToDisplay = bundle.getString("lostMessage");
        }
        if (player1.getScore() == bot.getScore()) {
            resultsToDisplay = bundle.getString("tieMessage");
        }
        resultsToDisplay += (bundle.getString("playerFinalScore") + player1.getScore() + ". ");
        resultsToDisplay += (bundle.getString("computerFinalScore") + bot.getScore() + ".");
        return resultsToDisplay;
    }
    
    /**
     * Checks if player1 or computer has over 100 points and then shuffle the dominoes pack clear the player1 and computer's hand, and also clear the tableTiles and the stack tiles.
     * @return true if game has over and false if it hasn't.
     */
    public boolean gameOver(){
        setScore();
        if(player1.getScore() < 100 && bot.getScore() < 100){
            dominos.Mixture();
            player1.clearHand();
            bot.clearHand();
            tableTiles.clear();
            stack.clear();
            deal();
            return false;
        }
        return true;
    }

}
