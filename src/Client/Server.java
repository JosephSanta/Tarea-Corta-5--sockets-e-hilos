//RECIBE EL DOT Y ACTUALIZA EL QUE ESTA EN EL CLIENTE


package Client; //codigo modificado

import java.net.*;
import java.io.*;
import Common.*;

public class Server implements Runnable{
    
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    Dot dot2;

    public Server(Dot d){
        dot = d;
        try {
            server = new ServerSocket(4445);
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void run(){
        try {
            while(true){
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());

                
                dot2 = (Dot)input.readObject();
                dot.currentPosition= dot2.currentPosition;
                dot.lastPosition = dot2.lastPosition;
                input.close();
                client.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
}