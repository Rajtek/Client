/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.List;
import Shared.Model.*;

/**
 *
 * @author Rajtek
 */
public class ClientModel {

    private User user;
    private List<Room> roomsList;
    private ModelListener listener;

    
    private int id;
    private boolean isDrawing = false;
    private boolean gameStared = false;
    private String phrase;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Room> getRoomsList() {
        return roomsList;
    }

    public void setRoomsList(List<Room> roomsList) {
        this.roomsList = roomsList;
        listener.propertyRoomsListChanged();
    }

    public void setUser(User user) {
        this.user = user;
        listener.propertyUserChanged();
    }

    public User getUser() {
        return user;
    }

    public void addListener(ModelListener listener) {
        this.listener = listener;
    }

    public void setIsDrawing(boolean b) {
        listener.setChatEnabled(!b);
        
        isDrawing = b;
        
        listener.propertyIsDrawingChanged();
    }

    public boolean isDrawing() {
        return isDrawing;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
        listener.propertyPhraseChanged();
    }

    public String getPhrase() {
        return phrase;
    }

    public void getTextMessage(String login, String msg) {
        listener.addNewTextMessage("<"+login+">: "+msg);
    }

    void setChatEnabled(boolean b) {
        listener.setChatEnabled(b);
    }

    void setGameStarted(boolean gameStarted) {
        this.gameStared=gameStarted;
        if(gameStarted&&isDrawing){
            listener.setChatEnabled(false);
        }
        if(gameStarted&&!isDrawing){
            listener.setChatEnabled(true);
        }
        if(!gameStarted){
            listener.setChatEnabled(false);
        }
    }


    void goodAnswer(String login, String answer) {
        setIsDrawing(false);
        listener.addNewTextMessage("<"+login+">: "+answer+" - Dobra odpowiedź!");
        
    }
    


}
