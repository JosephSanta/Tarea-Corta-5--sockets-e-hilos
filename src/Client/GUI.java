
//DEBE DE RECIBIR LA NUEVA POSICION DEL DOT


package Client;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;
import Common.Target;

import java.net.*;
import java.io.*;

public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Target target;

    Dot dot;

    Socket client;
    ObjectOutputStream output;
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        target = new Target();

        dot = new Dot();
        
        Server server = new Server(dot);//creamos un servidor , recibne el dot
        Thread hilo = new Thread(server); //re
        hilo.start();

        moveDot();
        run();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mapa.tablero[target.coords[X]][target.coords[Y]].clearTarget();
        ((Casilla)e.getSource()).setAsTarget();
        target.coords = ((Casilla)e.getSource()).getCoords();

        try {



            //crea el socket
            //debe de ser output ya que escribimos 
            //cada vez que se apreta un boton se envia el target

            //lo usamos para la tarea  , pero al revez
            //envez de enviar debemos recibir


            client = new Socket("127.0.0.1", 4444);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(target); //LE ENVIAMOS EL TARGET  , lo prepara
            output.flush(); //LO ENVIA 
            output.close();
            client.close();
        } catch (Exception ex) {
            //TODO: handle exception
        }
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    
    }

    public void run(){
        while (true){
            moveDot();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}