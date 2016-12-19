/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionListener;

/**
 *
 * @author Rajtek
 */
public interface ClientViewInterface {

    void setModel(ClientModel clientModel);

    void setController(ClientControler clientController);

    void displayErrorMessage(String errorMessage);

    String getAddress();

    String getPort();

    void addSendListener(ActionListener e);
}
