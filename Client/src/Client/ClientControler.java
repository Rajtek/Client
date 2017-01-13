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
import Shared.Model.User;
import Shared.Model.Room;
import java.util.List;
import Shared.Model.ControlerInterface;

/**
 *
 * @author Rajtek
 */
public class ClientControler implements SocketListener, ControlerInterface, ModelListener, ClientViewListener {

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
        clientView.setControlerListener(this);

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
                    connection.SendMessage(new MessageAskAboutRoom(connection.getSource(), clientView.getSelectedRoomID()));
                } catch (NoItemSelectedException ex) {
                }

            }
        });

        clientView.addJoinListener((ActionEvent e) -> {

            try {
                connection.SendMessage(new MessageJoinRoom(connection.getSource(), clientView.getSelectedRoomID()));

                clientView.showRoomPanel();

            } catch (NoItemSelectedException ex) {

            }

        });

        clientView.addRoomSendListener((ActionEvent e) -> {

            try {
                if (clientView.getAnswer().length() > 0) {
                    connection.SendMessage(new MessageAnswer(connection.getSource(), clientView.getSelectedRoomID(), clientView.getAnswer()));
                }

                clientView.showRoomPanel();

            } catch (NoItemSelectedException ex) {

            }

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
    public void reactToMessageLoginSuccessful(User user, List<Room> roomsList) {
        clientModel.setUser(user);
        clientModel.setRoomsList(roomsList);

        clientView.showLobbyPanel();

    }

    @Override
    public void reactToMessageRoomsList(List<Room> roomsList) {
        clientModel.setRoomsList(roomsList);

    }

    @Override
    public void reactToMessageUserList(List<User> userList, int maxUsers, int id) {
        clientView.setRoomInfo(" ");
        try {
            if (clientView.getSelectedRoomID() == id) {
                String s = "<html>";
                clientView.setCanJoin(false);
                if (userList.size() < maxUsers) {
                    clientView.setCanJoin(true);
                }

                for (User user : userList) {
                    s += user.getLogin() + "<br/>";
                }

                s += "</html>";
                clientView.setRoomInfo(s);
            }
        } catch (NoItemSelectedException ex) {
        }
    }

    @Override
    public void reactToMessageUsersOnRoom(List<User> userlist, int id) {
        clientModel.setId(id);
        clientView.setRoomID(id);
        clientView.setUsersListInRoom(userlist);

    }

    @Override
    public void reactToMessageLogin(String source, String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageAskAboutRoom(String source, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageJoinRoom(String source, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessageAskAboutRoomsList(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyUserChanged() {
        clientView.setUser(clientModel.getUser());
    }

    @Override
    public void propertyRoomsListChanged() {
        clientView.setRoomsList(clientModel.getRoomsList());

    }

    @Override
    public void reactToMessageRoomStatus(boolean gameStarted, int id) {
        clientModel.setId(id);
        clientModel.setGameStarted(gameStarted);
    }

    @Override
    public void reactToMessageTextMsg(String login, String msg) {
        clientModel.getTextMessage(login, msg);
    }

    @Override
    public void reactToMessageImage(String source, int id, int[] data) {
        clientView.updateImage(data);
    }

    @Override
    public void drawingChanged(int[] data) {
        connection.SendMessage(new MessageImage(connection.getSource(), clientModel.getId(), data));
    }

    @Override
    public void reactToMessageAnswer(String source, int id, String answer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToMessagePlayerDrawing(String phrase) {
        clientModel.setIsDrawing(true);
        clientModel.setPhrase(phrase);
    }

    @Override
    public void propertyIsDrawingChanged() {
        clientView.setDrawingEnabled(clientModel.isDrawing());
        
    }

    @Override
    public void propertyPhraseChanged() {
        clientView.setPhrase(clientModel.getPhrase());
    }

    @Override
    public void addNewTextMessage(String msg) {
        clientView.addNewTextMessage(msg);
    }

    @Override
    public void reactToMessageGameStopped() {
        clientModel.setIsDrawing(false);
        clientModel.setChatEnabled(false);
    }
    @Override
    public void setChatEnabled(boolean b){
        clientView.setChatEnabled(b);
    }

    @Override
    public void reactToMessageGoodAnswer(String source, int id, String answer) {
        clientModel.getGoodAnswer(source, answer);
    }


}
