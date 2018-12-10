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
public class Hungarian implements GameInterface {

    private Set dominos;

    private ArrayList<Tile> tableTiles;

    protected Player player1;
    protected Player bot;

    public Hungarian() {
        dominos = new Set();
        player1 = new Player();
        bot = new Player("Computer");
        tableTiles = new ArrayList<>();
    }

    /**
     * Initialize the game.
     *
     * Shuffle the pack of dominoes, remove all the tiles from table, player's
     * and computer's hand, set player's and computer's score to 0.
     */
    @Override
    public void initGame() {
        dominos.Mixture();
        tableTiles.clear();
        player1.clearHand();
        player1.clearScore();
        bot.clearHand();
        bot.clearScore();
        deal();
    }

    /**
     * Deal the tiles to players.
     */
    @Override
    public void deal() {
        while (true) {
            for (int i = 0; i < 12; i++) {
                Tile temp = dominos.getArrayElement(i);
                if (temp != null) {
                    player1.addToHand(temp);
                } else {
                    throw new IllegalArgumentException("Fail to add domino tile to your hand.");
                }
            }
            for (int i = 12; i < 24; i++) {
                Tile temp = dominos.getArrayElement(i);
                if (temp != null) {
                    bot.addToHand(temp);
                } else {
                    throw new IllegalArgumentException("Fail to add domino tile to computer's hand.");
                }
            }
            if (player1.numOfHandTiles() == 12 && bot.numOfHandTiles() == 12) {
                return;
            }
        }
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
        Tile firstTile = tableTiles.get(0);
        if (firstTile.getLeftVal() == toPlayed.getLeftVal()) {
            toPlayed.OtherSide();
            tableTiles.add(0, toPlayed);
            return true;
        }
        if (firstTile.getLeftVal() == toPlayed.getRightVal()) {
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

        Tile lastTile = tableTiles.get(tableTiles.size() - 1);;

        if (lastTile.getRightVal() == toPlayed.getLeftVal()) {
            tableTiles.add(tableTiles.size(), toPlayed);
            return true;
        }
        if (lastTile.getRightVal() == toPlayed.getRightVal()) {
            toPlayed.OtherSide();
            tableTiles.add(tableTiles.size(), toPlayed);
            return true;
        }
        return false;
    }

    /**
     * Checks if there is any possible move for player to do.
     *
     * @return true if there is and false if not.
     */
    public boolean checkPlayer() {
        if (player1.numOfHandTiles() == 0) {
            return false;
        }
        return ((player1.findTile(tableTiles.get(0).getLeftVal()) != -1 || player1.findTile(tableTiles.get(tableTiles.size() - 1).getRightVal()) != -1));
    }

    /**
     * Checks if there is any possible move for computer to do.
     *
     * @return true if there is and false if not.
     */
    public boolean checkBot() {
        Tile firstTile = tableTiles.get(0);

        Tile lastTile = tableTiles.get(tableTiles.size() - 1);

        if (bot.numOfHandTiles() == 0) {
            return false;
        }

        int computerChoiceTileIndex = bot.findTile(firstTile.getLeftVal());
        if (computerChoiceTileIndex != -1) {
            return true;
        } else {
            computerChoiceTileIndex = bot.findTile(lastTile.getRightVal());
            if (computerChoiceTileIndex != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkPlayer if bot can play.
     *
     * @return true if there is a possible move that bot can do an false if not.
     */
    public boolean botMove() {
        if (tableTiles.isEmpty()) {
            tableTiles.add(bot.getHandTile(0));
            bot.removeTile(0);
            return true;
        }

        Tile firstTile = tableTiles.get(0);

        Tile lastTile = tableTiles.get(tableTiles.size() - 1);

        int computerChoiceTileIndex = bot.findTile(firstTile.getLeftVal());

        if (computerChoiceTileIndex != -1) {
            Tile tileToInsert = bot.getHandTile(computerChoiceTileIndex);
            canLeftMove(tileToInsert);
            bot.removeTile(computerChoiceTileIndex);
            return true;
        } else {
            computerChoiceTileIndex = bot.findTile(lastTile.getRightVal());
            if (computerChoiceTileIndex != -1) {
                Tile tileToInsert = bot.getHandTile(computerChoiceTileIndex);
                canRightMove(tileToInsert);
                bot.removeTile(computerChoiceTileIndex);
                return true;
            } else {
                return false;
            }
        }
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
     * Calculate how many points has every player and adds the opponent hand's
     * points to the player with the minimum points at his hand.
     */
    public void setScore() {
        if (player1.pointsToGive() < bot.pointsToGive()) {
            player1.addScore(bot.pointsToGive());
        }
        if (player1.pointsToGive() > bot.pointsToGive()) {
            bot.addScore(player1.pointsToGive());
        }
    }

    /**
     * Checks if player1 or computer has over 100 points and then shuffle the dominoes pack clear the player1 and computer's hand, and also clear the tableTiles.
     * @return true if game has over and false if it hasn't.
     */
    public boolean gameOver(){
        setScore();
        if(player1.getScore() < 100 && bot.getScore() < 100){
            dominos.Mixture();
            player1.clearHand();
            bot.clearHand();
            tableTiles.clear();
            deal();
            return false;
        }
        return true;
    }
    
}
