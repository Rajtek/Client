/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Message;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 *
 * @author Rajtek
 */
public class ClientControler {
    private ClientView clientView;
    private ClientModel clientModel;
    private Connection connection;
    Thread t=null;
    public ClientControler(ClientModel clientModel){ 
        this.clientModel=clientModel;
    }

    void setView(ClientView v) {
        this.clientView=v;
        this.clientView.addSendListener((ActionEvent e) -> {
            String address;
            int port;
            address = clientView.getAddress();               
            try {
                port=Integer.parseInt(clientView.getPort());
                connection = new Connection(address,port);
                connection.connect();
                t=connection.start();
                clientView.showLoginPanel();

            } catch (IOException ex) {
                clientView.displayErrorMessage("Błąd Połączenia");
            } catch (IPException ex) {
                clientView.displayErrorMessage("Błędny adres IP");
            } catch(NumberFormatException ex){
                clientView.displayErrorMessage("Numer portu musi być liczbą");
            }
            
        });
        
        
        this.clientView.addLoginListener((ActionEvent e) -> {
            connection.SendMessage(new Message(connection.getSource()));
            
        });
        
        
        
    }
    
}
