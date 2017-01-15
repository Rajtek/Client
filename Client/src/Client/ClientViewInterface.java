/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Model.Room;
import Shared.Model.User;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Rajtek
 */
public interface ClientViewInterface {

    public void displayErrorMessage(String errorMessage);

    public String getLogin();

    public String getAddress();

    public String getPort();

    public int getSelectedRoomID() throws NoItemSelectedException;

    public void addSendListener(ActionListener e);
    public void addJoinListener(ActionListener listenForJoinButton);
    public void showLoginPanel();

    public void showRoomPanel();
    public void setRoomInfo(String s);
    public void showLobbyPanel();

    public void setRoomsList(List<Room> roomsList);

    public void setCanJoin(boolean b);
    
    public void setUser(User user);
    public void setRoomID(int id);
    
    public void setUsersListInRoom(List<User> userList);
    
    public void updateImage(int[] data);
    public void addLoginListener(ActionListener listenForLoginButton);
    public void drawingChanged(int[] data);
    
    public String getAnswer();
    public void setDrawingEnabled(boolean drawing);
    public void setChatEnabled(boolean b);
    public void setPhrase(String phrase);
    public void addNewTextMessage(String msg);
    public void setLobbyLogin(String login);
    public void addListSelectionListener(ListSelectionListener listener);
    public void setControlerListener(ClientViewListener listener);
    public void addRoomSendListener(ActionListener listener);
}
