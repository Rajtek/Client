/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Shared.Messages.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.event.ListSelectionEvent;
import Shared.Player;

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
            String login = clientView.getLogin();
            if (login.length() < 3) {
                clientView.displayErrorMessage("Za krótki login");
            } else {

                connection.SendMessage(new MessageLogin(connection.getSource(), login));
            }
        });
        this.clientView.addListSelectionListener((ListSelectionEvent evt) -> {
            if (!evt.getValueIsAdjusting()) {

                try {
                    connection.SendMessage(new MessageAskAboutTable(connection.getSource(), clientView.getSelectedTableID()));
                } catch (NoItemSelectedException ex) {}

            }
        });

        this.clientView.addJoinListener((ActionEvent e) -> {

            try {
                connection.SendMessage(new MessageJoinTable(connection.getSource(), clientView.getSelectedTableID()));
                
                clientView.showTablePanel();

            } catch (NoItemSelectedException ex) {

            }

        });

    }

    @Override
    public void getMessage(Message msg) {

        if (msg instanceof MessageLoginFailed) {
            clientView.displayErrorMessage("Podany login jest już zajęty");
        } else if (msg instanceof MessageLoginSuccessful) {
            clientView.showLobbyPanel();
            clientModel.setPlayer(((MessageLoginSuccessful) msg).getPlayer());
            clientView.setLobbyLogin(clientModel.getPlayer().getLogin());
            clientView.setLobbyCash(clientModel.getPlayer().getCash());
            clientView.setTablesList(((MessageLoginSuccessful) msg).getTablesList());

        } else if (msg instanceof MessageTablesList) {
            clientView.setTablesList(((MessageTablesList) msg).getTablesList());

        } else if (msg instanceof MessagePlayerList) {
            int maxPlayers = ((MessagePlayerList) msg).getMaxPlayers();
            Player[] playerlist = ((MessagePlayerList) msg).getPlayersList();
            String s = "<html>";
            clientView.setCanJoin(false);

            for (int i = 0; i < maxPlayers; i++) {
                if (playerlist[i] == null) {
                    clientView.setCanJoin(true);
                    break;
                }
            }

            for (int i = 0; i < maxPlayers; i++) {
                if (playerlist[i] != null) {
                    s += playerlist[i].getLogin() + " " + playerlist[i].getCash() + "$<br/>";
                }
            }
            s += "</html>";
            clientView.setTableInfo(s);
        } else if (msg instanceof MessagePlayersOnTable){
            int maxPlayers = ((MessagePlayersOnTable) msg).getMaxPlayers();
            Player[] playerlist = ((MessagePlayersOnTable) msg).getPlayersList();
            
            
            clientView.addPlayersToTable(playerlist);
                
            
            
        }

    }

}
