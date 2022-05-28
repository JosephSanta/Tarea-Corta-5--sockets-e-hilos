package Server; //codigo modificado
//hola
import java.net.*;
import java.io.*;
import Common.*;

public class Server implements Runnable{
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    //RECIBE EL DOT

    public Server(Dot d){
        dot = d;
        try {
            server = new ServerSocket(4444);
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }
    //CICLO INFINITO

    public void run(){
        try {
            while(true){
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());
                dot.target = (Target)input.readObject(); //LEE
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }
    
}