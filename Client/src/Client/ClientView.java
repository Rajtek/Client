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
public class ClientView extends JFrame {
    private JTextField adressTextField = new JTextField(10);
    private JTextField portNumberField = new JTextField(5);
    private JButton sendButton = new JButton("Wyślij");
    private JLabel adressLabel = new JLabel("Podaj adres serwera i port");
    private JLabel sepLabel = new JLabel(":");
    private JPanel adressPanel;
    ClientView(){
        adressPanel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        adressPanel.add(adressLabel);
        
        adressPanel.add(adressTextField);
        adressPanel.add(sepLabel);
        adressPanel.add(portNumberField);
	adressPanel.add(sendButton);
        this.add(adressPanel);
    }
    
    
    public String getAdress(){
        return adressTextField.getText();
    }
    public String getPort(){
        return portNumberField.getText();
    }
    void addSendListener(ActionListener listenForSendButton){
		
		sendButton.addActionListener(listenForSendButton);
		
	}
    
    void displayErrorMessage(String errorMessage){
		
		JOptionPane.showMessageDialog(this, errorMessage);
		
	}
}
