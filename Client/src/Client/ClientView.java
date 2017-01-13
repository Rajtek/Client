/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.GUIpannels.*;
import Shared.Model.User;
import Shared.Model.Room;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Rajtek
 */
public class ClientView extends JPanel implements ClientViewInterface, DrawAreaListener {

    

    private ConnectionJPanel connectionJPanel = new ConnectionJPanel();
    private LoginJPanel loginJPanel = new LoginJPanel();
    private LobbyJPanel lobbyJPanel = new LobbyJPanel();
    private RoomJPanel roomJPanel = new RoomJPanel();
    private DefaultListModel lobbyListModel = new DefaultListModel();
    private DefaultListModel roomListModel = new DefaultListModel();
    private int id;
    String login;
    ClientViewListener viewListener;

    public ClientView() {
        setSize(800, 600);
        setFocusable(true);

        this.add(connectionJPanel);
        this.add(loginJPanel);
        this.add(lobbyJPanel);
        this.add(roomJPanel);
        connectionJPanel.setVisible(true);
        loginJPanel.setVisible(false);
        lobbyJPanel.setListModel(lobbyListModel);
        roomJPanel.setDrawAreaListener(this);
        
    }

    @Override
    public String getAddress() {
        return connectionJPanel.getAddress();//addressTextField.getText();
    }

    @Override
    public String getPort() {
        return connectionJPanel.getPort();
    }

    @Override
    public void addSendListener(ActionListener listenForSendButton) {
        connectionJPanel.getSendButton().addActionListener(listenForSendButton);

    }

    public void addJoinListener(ActionListener listenForJoinButton) {
        lobbyJPanel.addJoinListener(listenForJoinButton);
    }

    public void setRoomInfo(String s) {
        lobbyJPanel.setRoomInfo(s);
    }



    public void addLoginListener(ActionListener listenForLoginButton) {
        loginJPanel.getLoginButton().addActionListener(listenForLoginButton);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        lobbyJPanel.addListListener(listener);
    }
    
    public void setControlerListener(ClientViewListener listener){
        viewListener=listener;
    }
    
    public void addRoomSendListener(ActionListener listener){
        roomJPanel.addSendListener(listener);
    }

    
    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    void setLobbyLogin(String login) {
        lobbyJPanel.setLogin(login);
    }



    String getLogin() {
        return loginJPanel.getLogin();
    }

    public int getSelectedRoomID() throws NoItemSelectedException {
        return lobbyJPanel.getRoomID();
    }

    void showLoginPanel() {

        connectionJPanel.setVisible(false);
        loginJPanel.setVisible(true);
    }

    void showRoomPanel() {
        roomJPanel.setVisible(true);

        lobbyJPanel.setVisible(false);
        

    }

    void showLobbyPanel() {
        loginJPanel.setVisible(false);
        lobbyJPanel.setVisible(true);
        
        

    }
    


    public void setRoomsList(List<Room> roomsList) {
        lobbyListModel.clear();
        for(Room room : roomsList){
            lobbyListModel.addElement(room);
        }
        lobbyJPanel.refreshList();
        
    }

    
    
    public void setCanJoin(boolean b) {

        lobbyJPanel.setJoinButtonEnabled(b);
    }
    
    public void setUser(User user){
//        this.user=user;
        login = user.getLogin();
        
        lobbyJPanel.setLogin(login);
    }
    
    public void setRoomID(int id){
        this.id=id;
    }

    public void setUsersListInRoom(List<User> userList) {
        roomJPanel.setUsersList(userList);
    }
    
    public void updateImage(int[] data){
        roomJPanel.updateImage(data);
    }
    @Override
    public void drawingChanged(int[] data) {
        System.out.println("Narysowane");
        viewListener.drawingChanged(data);
    }

    String getAnswer() {
        return roomJPanel.getAnswer();
    }

    public void setDrawingEnabled(boolean drawing) {
        roomJPanel.setDrawing(drawing);
        roomJPanel.setChatEnabled(false);
    }

    public void setPhrase(String phrase) {
        roomJPanel.setPhrase(phrase);
    }

    void addNewTextMessage(String msg) {
        roomJPanel.addTextMessage(msg);
    }

    void setChatEnabled(boolean b) {
        roomJPanel.setChatEnabled(b);
    }
    

}
