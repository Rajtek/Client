/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

/**
 *
 * @author Rajtek
 */
public class Card {
    private int value;
    private Colors color;
    
    public Card(int value, Colors color){
        this.value=value;
        this.color=color;
    }
    public String getPath(){
        String s;
        s=Integer.toString(value);
        s+="_OF_";
        s+=color.toString();
        s+=".PNG";
        return s;
    }
}
