/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.GUIpannels.*;
import Shared.Player;
import Shared.Table;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionListener;

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
    private TableJPanel tableJPanel=new TableJPanel();
    public DefaultListModel listModel;
    
    
    public ClientView() {
        setSize(800, 600);
        setFocusable(true);
        
        this.add(connectionJPanel);
        this.add(loginJPanel);
        this.add(lobbyJPanel);
        this.add(tableJPanel);
        connectionJPanel.setVisible(true);
        loginJPanel.setVisible(false);

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
    public void addJoinListener(ActionListener listenForJoinButton){
        lobbyJPanel.getJoinButton().addActionListener(listenForJoinButton);
    }
    
    public void setTableInfo(String s){
        lobbyJPanel.setTableInfo(s);
    }
    public void addListValueListener(ListSelectionListener listSelectionListener){
        lobbyJPanel.getTablesList().addListSelectionListener(listSelectionListener);
    }

    public void addLoginListener(ActionListener listenForLoginButton) {
        loginJPanel.getLoginButton().addActionListener(listenForLoginButton);
    }
    public void addListSelectionListener(ListSelectionListener listener){
        lobbyJPanel.getTablesList().addListSelectionListener(listener);
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
    public int getSelectedTableID() throws NoItemSelectedException{
        return lobbyJPanel.getTableID();
    }
    void showLoginPanel() {

        connectionJPanel.setVisible(false);
        loginJPanel.setVisible(true);
    }
    void showTablePanel(){
        tableJPanel.setVisible(true);
        lobbyJPanel.setVisible(false);
        
    }
    void showLobbyPanel() {
        loginJPanel.setVisible(false);
        lobbyJPanel.setVisible(true);
        listModel=new DefaultListModel();
        lobbyJPanel.getTablesList().setModel(listModel);
        
    }
    
    void addPlayersToTable(Player[] players){
        tableJPanel.setPlayers(players);
    }

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
        //String[] VisibleString = new String[tablesList.size()];
        int i = 0;
        listModel.clear();
        for (Table table : tablesList) { 
            listModel.addElement(formatTableInfo(table));
            
            i++;
        }
        lobbyJPanel.getTablesList().setModel(listModel);
        lobbyJPanel.getTablesList().ensureIndexIsVisible(0);
    }

    
    void setCanJoin(boolean b) {
       
       lobbyJPanel.getJoinButton().setEnabled(b);
    }
    
    
    
}
