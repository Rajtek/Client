/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.*;
import java.net.*;
import Shared.Messages.Message;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Rajtek
 */
public class Connection implements Runnable {

    static boolean CheckIPv4(String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);

    }

    private String address;
    private int port;
    public Thread t;
    private String threadName;
    private Socket sock;
    private OutputStream os;
    private InputStream is;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private List<SocketListener> listeners = new ArrayList<>();

    public Connection(String address, int port) throws IPException {
        if (!CheckIPv4(address)) {
            throw new IPException();
        }
        this.address = address;
        this.port = port;
        threadName = "connection";
        
    }

    public void connect() throws IOException {
        sock = new Socket(address, port);
        System.out.println("Nawiazalem polaczenie: " + sock);
        os = sock.getOutputStream();
        is = sock.getInputStream();
        oos = new ObjectOutputStream(os);
        ois = new ObjectInputStream(is);
    }

    @Override
    public void run() {

        try {

            while (true) {
                Shared.Messages.Message a;
                a = (Shared.Messages.Message) ois.readObject();
//                try {
//                        sleep(50);
//                    } catch (InterruptedException ex) {
//                        
//                    }
                notifyListener(a);
               
            }
            //zamykanie polaczenia                                                           

        } catch (IOException ex) {
            System.err.println(ex+"qweqwe");

        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    public void SendMessage(Message m) {
        try {

            oos.writeObject(m);

        } catch (IOException ex) {
            System.err.print(ex);
        }

    }

    public Thread start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
        return t;
    }

    public String getSource() {

        return sock.getLocalSocketAddress().toString();
    }
    
   public void addListener(SocketListener toAdd){
       listeners.add(toAdd);
   }
   
   private void notifyListener(Shared.Messages.Message msg){
       for (SocketListener s : listeners){
           s.getMessage(msg);
       }
   }

}
