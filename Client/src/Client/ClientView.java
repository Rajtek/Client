/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Table;
import java.awt.*;
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

    private JTextField addressTextField = new JTextField("127.0.0.1", 10);
    private JTextField portNumberField = new JTextField("50017", 5);
    private JTextField loginField = new JTextField(10);
    public JButton sendButton = new JButton("Wyślij");
    private JLabel addressLabel = new JLabel("Podaj adres serwera i port");
    private JLabel sepLabel = new JLabel(":");
    public JPanel addressPanel;
    public JPanel lobbyPanel= new JPanel();
    public JPanel loginPanel = new JPanel();
    public JButton loginButton = new JButton("Wyślij");
    private JLabel loginLabel = new JLabel("login:");
    private JLabel lobbyLabel = new JLabel("No kurwa gratulację XD");
    
    private JList tablesList = new JList();
    
    public ClientView() {
        setSize(800, 600);
        setFocusable(true);
        addressPanel = new JPanel();
        addressPanel.add(addressLabel);
        addressPanel.add(addressLabel);
        addressPanel.add(addressTextField);
        addressPanel.add(sepLabel);
        addressPanel.add(portNumberField);
        addressPanel.add(sendButton);
        this.add(addressPanel);

        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(loginButton);

        this.add(loginPanel);
        
        loginPanel.setVisible(false);
        
        GridLayout lobbyLayout = new GridLayout(0,2);
        lobbyPanel.setLayout(lobbyLayout);
        lobbyPanel.add(tablesList);
        lobbyPanel.add(lobbyLabel);
        
        
        
        
        tablesList.setLayoutOrientation(JList.VERTICAL);
        //lobbyLayout.addLayoutComponent("Tables List", tablesList);
        
        
        this.add(lobbyPanel);
        lobbyPanel.setVisible(false);
        
    }

    @Override
    public String getAddress() {
        return addressTextField.getText();
    }

    @Override
    public String getPort() {
        return portNumberField.getText();
    }

    @Override
    public void addSendListener(ActionListener listenForSendButton) {
        sendButton.addActionListener(listenForSendButton);
    }

    public void addLoginListener(ActionListener listenForLoginButton) {
        loginButton.addActionListener(listenForLoginButton);
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    String getLogin() {
        return loginField.getText();
    }

    void showLoginPanel() {

        addressPanel.setVisible(false);
        loginPanel.setVisible(true);
    }
    
    void showLobbyPanel(){
        loginPanel.setVisible(false);
        lobbyPanel.setVisible(true);
    }
    public void setLogin(String login){
        lobbyLabel.setText(lobbyLabel.getText()+" "+login);
    }

    @Override
    public void setModel(ClientModel m) {
        this.clientModel = m;
    }

    @Override
    public void setController(ClientControler c) {
        this.clientControler = c;
    }
   
    public void setTablesList(List<Table> tablesList){
        String[] VisibleString = new String[tablesList.size()];
        int i=0;
        for (Table table : tablesList) {
            VisibleString[i]="#"+table.getId()+" blind: "+table.getBlind()+"/"+table.getBlind()*2+"$ "+table.getNumberOfPlayers()+"/"+table.getMaxPlayers()+" "+ 
            i++;
        }
        
        this.tablesList.setListData(VisibleString);
    }
}
