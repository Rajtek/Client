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
import Shared.Model.Player;
import Shared.Model.Table;
import java.util.List;
import Shared.Model.ControlerInterface;


/**
 *
 * @author Rajtek
 */
public class ClientControler implements SocketListener, ControlerInterface, ModelListener {

    private ClientView clientView;
    private ClientModel clientModel;
    private Connection connection;
    Thread t = null;

    public ClientControler(ClientModel clientModel) {
        this.clientModel = clientModel;
        clientModel.addListener(this);

    }

    void setView(ClientView v) {
        clientView = v;

        clientView.addSendListener((ActionEvent e) -> {
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

        clientView.addLoginListener((ActionEvent e) -> {
            String login = clientView.getLogin();
            if (login.length() < 3) {
                clientView.displayErrorMessage("Za krótki login");
            } else {

                connection.SendMessage(new MessageLogin(connection.getSource(), login));
            }
        });
        clientView.addListSelectionListener((ListSelectionEvent evt) -> {
            if (!evt.getValueIsAdjusting()) {

                try {
                    connection.SendMessage(new MessageAskAboutTable(connection.getSource(), clientView.getSelectedTableID()));
                } catch (NoItemSelectedException ex) {
                }

            }
        });

        clientView.addJoinListener((ActionEvent e) -> {

            try {
                connection.SendMessage(new MessageJoinTable(connection.getSource(), clientView.getSelectedTableID()));

                clientView.showTablePanel();

            } catch (NoItemSelectedException ex) {

            }

        });
        
        clientView.addCallListener((ActionEvent e) -> {
            
           

        });
        
        clientView.addCheckListener((ActionEvent e) -> {

           

        });
        
        clientView.addFoldListener((ActionEvent e) -> {

           

        });
    }

    @Override
    public void getMessage(Message msg) {
        msg.performAction(this);
        System.out.println(msg.getClass());
    }

    @Override
    public void reactToMessageLoginFailed() {
        clientView.displayErrorMessage("Podany login jest już zajęty");
    }

    @Override
    public void reactToMessageLoginSuccessful(Player player, List<Table> tablesList) {
        clientModel.setPlayer(player);
        clientModel.setTablesList(tablesList);

        clientView.showLobbyPanel();

    }

    @Override
    public void reactToMessageTablesList(List<Table> tablesList) {
        clientModel.setTablesList(tablesList);

    }

    @Override
    public void reactToMessagePlayerList(Player[] playerList, int maxPlayers, int id) {
        clientView.setTableInfo(" ");
        try {
            if (clientView.getSelectedTableID() == id) {
                String s = "<html>";
                clientView.setCanJoin(false);

                for (int i = 0; i < maxPlayers; i++) {
                    if (playerList[i] == null) {
                        clientView.setCanJoin(true);
                        break;
                    }
                }

                for (int i = 0; i < maxPlayers; i++) {
                    if (playerList[i] != null) {
                        s += playerList[i].getLogin() + " " + playerList[i].getCash() + "$<br/>";
                    }
                }
                s += "</html>";
                clientView.setTableInfo(s);
            }
        } catch (NoItemSelectedException ex) {
        }
    }

    @Override
    public void reactToMessagePlayersOnTable(Player[] playerlist, int id) {
        clientView.setTableID(id);
        clientView.addPlayersToTable(playerlist);
        
    }

    @Override
    public void reactToMessageLogin(String source, String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageAskAboutTable(String source, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageJoinTable(String source, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageAskAboutTablesList(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyPlayerChanged() {
        clientView.setPlayer(clientModel.getPlayer());
    }

    @Override
    public void propertyTablesListChanged() {
        clientView.setTablesList(clientModel.getTablesList());
        
    }

    @Override
    public void reactToMessageCall(int call) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageFold() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageTableStatus(Table table) {
       clientView.refreshTableStatus(table);
    }
}
