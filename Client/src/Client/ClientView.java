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

    @Override
    public void addJoinListener(ActionListener listenForJoinButton) {
        lobbyJPanel.addJoinListener(listenForJoinButton);
    }

    @Override
    public void setRoomInfo(String s) {
        lobbyJPanel.setRoomInfo(s);
    }


    @Override
    public void addLoginListener(ActionListener listenForLoginButton) {
        loginJPanel.getLoginButton().addActionListener(listenForLoginButton);
    }

    @Override
    public void addListSelectionListener(ListSelectionListener listener) {
        lobbyJPanel.addListListener(listener);
    }
    
    @Override
    public void setControlerListener(ClientViewListener listener){
        viewListener=listener;
    }
    
    @Override
    public void addRoomSendListener(ActionListener listener){
        roomJPanel.addSendListener(listener);
    }

    
    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    @Override
    public void setLobbyLogin(String login) {
        lobbyJPanel.setLogin(login);
    }


    @Override
    public String getLogin() {
        return loginJPanel.getLogin();
    }

    
    @Override
    public int getSelectedRoomID() throws NoItemSelectedException {
        return lobbyJPanel.getRoomID();
    }

    @Override
    public void showLoginPanel() {

        connectionJPanel.setVisible(false);
        loginJPanel.setVisible(true);
    }
    @Override
    public void showRoomPanel() {
        roomJPanel.setVisible(true);

        lobbyJPanel.setVisible(false);
        

    }
    @Override
    public void showLobbyPanel() {
        loginJPanel.setVisible(false);
        lobbyJPanel.setVisible(true);
        
    }
    

    @Override
    public void setRoomsList(List<Room> roomsList) {
        lobbyListModel.clear();
        for(Room room : roomsList){
            lobbyListModel.addElement(room);
        }
        lobbyJPanel.refreshList();
        
    }

    
    @Override
    public void setCanJoin(boolean b) {

        lobbyJPanel.setJoinButtonEnabled(b);
    }
    
    @Override
    public void setUser(User user){
        login = user.getLogin();
        
        lobbyJPanel.setLogin(login);
    }
    
    @Override
    public void setRoomID(int id){
        this.id=id;
    }
    
    @Override
    public void setUsersListInRoom(List<User> userList) {
        roomJPanel.setUsersList(userList);
    }
    
    @Override
    public void updateImage(int[] data){
        roomJPanel.updateImage(data);
    }
    @Override
    public void drawingChanged(int[] data) {
        System.out.println("Narysowane");
        viewListener.drawingChanged(data);
    }
    @Override
    public String getAnswer() {
        return roomJPanel.getAnswer();
    }
    @Override
    public void setDrawingEnabled(boolean drawing) {
        roomJPanel.setDrawing(drawing);
    }
    @Override
    public void setChatEnabled(boolean b) {
        roomJPanel.setChatEnabled(b);
    }
    @Override
    public void setPhrase(String phrase) {
        roomJPanel.setPhrase(phrase);
    }
    @Override
    public void addNewTextMessage(String msg) {
        roomJPanel.addTextMessage(msg);
    }

    
    

}
