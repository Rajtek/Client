/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.Serializable;

/**
 *
 * @author Rajtek
 */
public class Player implements Serializable{
    private String nick;
    private String password;
    private int cash;
    
    Player(String nick, String password){
        this.nick=nick;
        this.password=password;
    }
}
