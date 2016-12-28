/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 *
 * @author Rajtek
 */
public class ClientControler implements SocketListener {

    private ClientView clientView;
    private ClientModel clientModel;
    private Connection connection;
    Thread t = null;

    public ClientControler(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    void setView(ClientView v) {
        this.clientView = v;
        this.clientView.addSendListener((ActionEvent e) -> {
            String address;
            int port;
            address = clientView.getAddress();
            try {
                port = Integer.parseInt(clientView.getPort());
                connection = new Connection(address, port);
                connection.connect();
                connection.addListener(this);
                t = connection.start();
                clientView.showLoginPanel();

            } catch (IOException ex) {
                clientView.displayErrorMessage("Błąd Połączenia");
            } catch (IPException ex) {
                clientView.displayErrorMessage("Błędny adres IP");
            } catch (NumberFormatException ex) {
                clientView.displayErrorMessage("Numer portu musi być liczbą");
            }

        });

        this.clientView.addLoginListener((ActionEvent e) -> {
            String login=clientView.getLogin();
            if (login.length() < 3) {
                clientView.displayErrorMessage("Za krótki login");
            } else {
                
                connection.SendMessage(new MessageLogin(connection.getSource(), login));
            }
        });

    }

    @Override
    public void getMessage(Message msg) {

        if (msg instanceof MessageLoginFailed) {
            clientView.displayErrorMessage("Podany login jest już zajęty");
        }
       else if (msg instanceof MessageLoginSuccessful) {    
            clientView.showLobbyPanel();
            clientModel.setPlayer(((MessageLoginSuccessful) msg).getPlayer());
            clientView.setLobbyLogin(clientModel.getPlayer().getLogin());
            clientView.setLobbyCash(clientModel.getPlayer().getCash());
            }
       else if (msg instanceof MessageGetTablesList) {
           clientView.setTablesList(((MessageGetTablesList) msg).getTablesList());
       }
        
        System.out.println(msg.getSource() + " z kontroloera");
    }

}
