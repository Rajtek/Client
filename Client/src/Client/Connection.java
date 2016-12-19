/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.*;
import java.net.*;
import Shared.Message;
/**
 *
 * @author Rajtek
 */
public class Connection implements Runnable{
    
    static boolean CheckIPv4(String ip){
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
        
    }
    
    private String address;
    private int port;
    public Thread t;
    private String threadName;
    private Socket sock;
    OutputStream os;
    ObjectOutputStream oos;
    public Connection(String address, int port) throws IPException{
        if(!CheckIPv4(address)) throw new IPException();
        this.address=address;
        this.port=port;
        threadName="connection";
        
    }
    public void connect() throws IOException{
        sock=new Socket(address,port);
        System.out.println("Nawiazalem polaczenie: "+sock);
        os = sock.getOutputStream();
        oos = new ObjectOutputStream(os);

    }
    
    @Override
    public void run()  {
        
        try {
            
            //tworzenie strumieni danych pobieranych z klawiatury i dostarczanych do socketu 
            BufferedReader klaw;                                                             
            klaw=new BufferedReader(new InputStreamReader(System.in));                       
            PrintWriter outp;                                                               
            outp=new PrintWriter(sock.getOutputStream());                                    
        
            //komunikacja - czytanie danych z klawiatury i przekazywanie ich do strumienia
            while(true){
            System.out.print("<Wysylamy:> ");                                                
            String str=klaw.readLine();                                                      
            outp.println(str);                                                               
            outp.flush();                                                                    
            if(str.equals("exit"))break;
            }                                                                           
            //zamykanie polaczenia                                                           
            klaw.close();                                                                    
            outp.close();                                                                    
            sock.close();
            
        } catch (IOException ex) {
            System.err.println(ex);
            t.interrupt();
            
        }
        
        
    }
    
    
    public void SendLogin(String s){
        
        
           PrintWriter outp;                                                               
        try {
            outp=new PrintWriter(sock.getOutputStream());
            outp.println(s);                                                               
            outp.flush();
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    public void SendMessage(Message m){
        try {
            
            oos.writeObject(new Message(sock.getLocalAddress().toString()));
            
        } catch (IOException ex) {
            System.err.print(ex);
        }
        
    }
    public Thread start(){
        if (t==null){
            t=new Thread(this,threadName);
            t.start();
        }
        return t;
    }
    
    public String getSource(){
        
        return sock.getLocalSocketAddress().toString();
    }
    
    
}
