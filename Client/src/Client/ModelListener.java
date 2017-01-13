/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author Rajtek
 */
public interface ModelListener {
    public void propertyUserChanged();
    public void propertyRoomsListChanged();
    public void propertyIsDrawingChanged();

    public void propertyPhraseChanged();

    public void addNewTextMessage(String msg);

    public void setChatEnabled(boolean b);

}
