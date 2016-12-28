/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.GUIpannels.*;
import Shared.Table;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Rajtek
 */
public class ClientView extends JPanel implements ClientViewInterface {

    private ClientModel clientModel;
    private ClientControler clientControler;

    
    
    private ConnectionJPanel connectionJPanel= new ConnectionJPanel();
    private LoginJPanel loginJPanel=new LoginJPanel();
    private LobbyJPanel lobbyJPanel=new LobbyJPanel();
    
    private JList tablesList = new JList();
    
    public ClientView() {
        setSize(800, 600);
        setFocusable(true);
        
        this.add(connectionJPanel);
        this.add(loginJPanel);
        this.add(lobbyJPanel);
        connectionJPanel.setVisible(true);
        loginJPanel.setVisible(false);
        



        tablesList.setLayoutOrientation(JList.VERTICAL);
        tablesList.setVisibleRowCount(50);



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

    public void addLoginListener(ActionListener listenForLoginButton) {
        loginJPanel.getLoginButton().addActionListener(listenForLoginButton);
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    void setLobbyLogin(String login){
        lobbyJPanel.setLogin(login);
    }
    void setLobbyCash(int cash){
        lobbyJPanel.setCash(cash);
    }
    String getLogin(){
        return loginJPanel.getLogin();
    }
    void showLoginPanel() {

        connectionJPanel.setVisible(false);
        loginJPanel.setVisible(true);
    }

    void showLobbyPanel() {
        loginJPanel.setVisible(false);
        lobbyJPanel.setVisible(true);
    }
//    public void setLogin(String login){
//        
//    }

    @Override
    public void setModel(ClientModel m) {
        this.clientModel = m;
    }

    @Override
    public void setController(ClientControler c) {
        this.clientControler = c;
    }
    private String formatTableInfo(Table table){
        String temp="#";
        //VisibleString[i] = "#" + table.getId() + " blind: " + table.getBlind() + "$/" + table.getBlind() * 2 + "$ graczy/graczy.max:" + table.getNumberOfPlayers() + "/" + table.getMaxPlayers() + " ";
        temp+=table.getId();
        while(temp.length()<5)
            temp+=" ";
        temp+="| "+table.getBlind() + "$/" + table.getBlind() * 2 + "$";
        while(temp.length()<27)
            temp+=" ";
        temp+="| "+table.getNumberOfPlayers() + "/" + table.getMaxPlayers();
        
        
        return temp;
    }
    public void setTablesList(List<Table> tablesList) {
        String[] VisibleString = new String[tablesList.size()];
        int i = 0;
        for (Table table : tablesList) { 
            VisibleString[i] = formatTableInfo(table);
            
            
            i++;
        }
        
        lobbyJPanel.getTablesList().setListData(VisibleString);
    }
    
    
    
}
