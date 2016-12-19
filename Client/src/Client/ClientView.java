/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;


import javax.swing.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Rajtek
 */
public class ClientView extends JPanel implements ClientViewInterface{
    private ClientModel clientModel;
    private ClientControler clientControler;
    
    private JTextField addressTextField = new JTextField("127.0.0.1",10);
    private JTextField portNumberField = new JTextField("50017",5);
    private JTextField loginField = new JTextField(10);
    public JButton sendButton = new JButton("Wyślij");
    private JLabel addressLabel = new JLabel("Podaj adres serwera i port");
    private JLabel sepLabel = new JLabel(":");
    public JPanel addressPanel;

    public JPanel loginPanel= new JPanel();;
    public JButton loginButton = new JButton("Wyślij");
    private JLabel loginLabel = new JLabel("login:");
    
    public ClientView(){
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
    }
    
    
    @Override
    public String getAddress(){
        return addressTextField.getText();
    }
    @Override
    public String getPort(){
        return portNumberField.getText();
    }
    @Override
    public void addSendListener(ActionListener listenForSendButton){
		sendButton.addActionListener(listenForSendButton);	
	}
    public void addLoginListener(ActionListener listenForLoginButton){
                loginButton.addActionListener(listenForLoginButton);
    }
    @Override
    public void displayErrorMessage(String errorMessage){	
		JOptionPane.showMessageDialog(this, errorMessage);		
	}
    
    String getLogin(){
        return loginField.getText();
    }
    void showLoginPanel(){
        
        addressPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    @Override
    public void setModel(ClientModel m) {
        this.clientModel=m;
    }

    @Override
    public void setController(ClientControler c) {
        this.clientControler=c;
    }
}
