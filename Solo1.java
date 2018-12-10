/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominogui;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author John, Leos
 */
public class Solo1{
    protected Set pack;
    private Player player;
    protected ArrayList<Tile> stack;
    protected ArrayList<Tile> table;
    protected Tile [][]stackTiles;
    
    Scanner scanner;
    
    public Solo1(){
        player = new Player();
        pack = new Set();
        stack = new ArrayList();
        table = new ArrayList();
    }
    
    public void initStack(){
        for(int i=0;i<28;i++){
            stack.add(pack.getArrayElement(i));
        }
    }
    
    public void initGame(){
        pack.Mixture();
        stack.clear();
        table.clear();
        initStack();
    }
    
    public String showTableTiles(){
        StringBuilder sb = new StringBuilder();
        for(Tile x: table){
            sb.append(x.toString());
        }
        return sb.toString();
    }
    
    public boolean canLeftMove(Tile toPlayed){
        if(table.isEmpty()){
            table.add(toPlayed);
            return true;
        }
        
        Tile firstTile = table.get(0);
        
        if(firstTile.getLeftVal() == toPlayed.getLeftVal()){
            toPlayed.OtherSide();
            table.add(0, toPlayed);
            return true;
        }
        if(firstTile.getLeftVal() == toPlayed.getRightVal()){
            table.add(0, toPlayed);
            return true;
        }
        return false;
    }
    
    public boolean canRightMove(Tile toPlayed){
        if(table.isEmpty()){
            table.add(toPlayed);
            return true;
        }
        
        Tile lastTile = table.get(table.size()-1);
        
        if(lastTile.getRightVal() == toPlayed.getLeftVal()){
            table.add(table.size(), toPlayed);
            return true;
        }
        if(lastTile.getRightVal() == toPlayed.getRightVal()){
            toPlayed.OtherSide();
            table.add(table.size(), toPlayed);
            return true;
        }
        return false;
    }
            
    public boolean canMove(int i){
        return ((stack.get(i).getLeftVal() == table.get(0).getLeftVal() || stack.get(i).getRightVal() == table.get(0).getLeftVal()) || (stack.get(i).getLeftVal() == table.get(table.size()-1).getRightVal() || stack.get(i).getRightVal() == table.get(table.size()-1).getRightVal()));
    }
       
}

