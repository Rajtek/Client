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
public class Client {
     public static void main(String[] args) {
         ClientView theView = new ClientView();
         ClientModel theModel = new ClientModel();
         ClientControler theControler = new ClientControler(theView, theModel);
         theView.setVisible(true);
     }
}
