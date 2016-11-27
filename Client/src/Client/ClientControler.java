/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Rajtek
 */
public class ClientControler {
    private ClientView theView;
    private ClientModel theModel;
   
    public ClientControler(ClientView theView, ClientModel theModel){
        this.theModel=theModel;
        this.theView=theView;
    
    
        this.theView.addSendListener(new ClientListener());
    }
    
    class ClientListener implements ActionListener{

        
        @Override
        public void actionPerformed(ActionEvent e) {
            String adress;
            int port;
            adress = theView.getPort();
            try{
            port=Integer.parseInt(theView.getPort());
            }
            catch(NumberFormatException ex){
                theView.displayErrorMessage("Numer portu musi być liczbą");
            }
            
            
           }
        
    }
}
