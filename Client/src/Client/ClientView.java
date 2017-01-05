/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.GUIpannels.*;
import Shared.Model.Player;
import Shared.Model.Table;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Rajtek
 */
public class ClientView extends JPanel implements ClientViewInterface {

    private ConnectionJPanel connectionJPanel = new ConnectionJPanel();
    private LoginJPanel loginJPanel = new LoginJPanel();
    private LobbyJPanel lobbyJPanel = new LobbyJPanel();
    private TableJPanel tableJPanel = new TableJPanel();
    private DefaultListModel listModel= new DefaultListModel();
    private int id;
    String login;
    int cash;
    public ClientView() {
        setSize(800, 600);
        setFocusable(true);

        this.add(connectionJPanel);
        this.add(loginJPanel);
        this.add(lobbyJPanel);
        this.add(tableJPanel);
        connectionJPanel.setVisible(true);
        loginJPanel.setVisible(false);
        lobbyJPanel.getTablesList().setModel(listModel);
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
        lobbyJPanel.getJoinButton().addActionListener(listenForJoinButton);
    }

    public void setTableInfo(String s) {
        lobbyJPanel.setTableInfo(s);
    }

    public void addListValueListener(ListSelectionListener listSelectionListener) {
        lobbyJPanel.getTablesList().addListSelectionListener(listSelectionListener);
    }

    public void addLoginListener(ActionListener listenForLoginButton) {
        loginJPanel.getLoginButton().addActionListener(listenForLoginButton);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        lobbyJPanel.getTablesList().addListSelectionListener(listener);
    }
    
    public void addFoldListener(ActionListener listenForFoldButton){
        tableJPanel.getFoldButton().addActionListener(listenForFoldButton);
    }
    
    public void addCallListener(ActionListener listenForCallButton){
        tableJPanel.getCallButton().addActionListener(listenForCallButton);
    }

    public void addCheckListener(ActionListener listenForCheckButton){
        tableJPanel.getCheckButton().addActionListener(listenForCheckButton);
    }
    
    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    void setLobbyLogin(String login) {
        lobbyJPanel.setLogin(login);
    }

    void setLobbyCash(int cash) {
        lobbyJPanel.setCash(cash);
    }

    String getLogin() {
        return loginJPanel.getLogin();
    }

    public int getSelectedTableID() throws NoItemSelectedException {
        return lobbyJPanel.getTableID();
    }

    void showLoginPanel() {

        connectionJPanel.setVisible(false);
        loginJPanel.setVisible(true);
    }

    void showTablePanel() {
        tableJPanel.setVisible(true);
        lobbyJPanel.setVisible(false);
        

    }

    void showLobbyPanel() {
        loginJPanel.setVisible(false);
        lobbyJPanel.setVisible(true);
        
        

    }
    
    
    void addPlayersToTable(Player[] players) {
        tableJPanel.setPlayers(players);
    }



    public void setTablesList(List<Table> tablesList) {
        listModel.clear();
        for(Table table : tablesList){
            listModel.addElement(table);
            if(table.getId()==id){
                
                tableJPanel.refreshTable(table);
            }
        }
        lobbyJPanel.getTablesList().ensureIndexIsVisible(0);
        
    }

    public void refreshTableStatus(Table table){
        tableJPanel.refreshTable(table);
    }
    
    public void setCanJoin(boolean b) {

        lobbyJPanel.getJoinButton().setEnabled(b);
    }
    
    public void setPlayer(Player player){
        login = player.getLogin();
        cash = player.getCash();
        
        lobbyJPanel.setLogin(login);
        lobbyJPanel.setCash(cash);
    }
    
    public void setTableID(int id){
        this.id=id;
    }
    

}
