/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import javax.swing.JFrame;

/**
 *
 * @author Rajtek
 */
public class Client {

    private static ClientModel createClientModel() {
        return new ClientModel();
    }

    private static ClientControler createClientControler(ClientModel clientModel) {
        return new ClientControler(clientModel);
    }

    private static ClientView createModelViewController() {
        ClientModel m = createClientModel();
        ClientControler c = createClientControler(m);
        ClientView v = new ClientView();
        v.setModel(m);
        v.setController(c);
        c.setView(v);
        return v;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Texas Holdem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        ClientView v = createModelViewController();
        frame.getContentPane().add(v);

        frame.setSize(v.getSize());
        frame.setVisible(true);

        v.requestFocus();

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
