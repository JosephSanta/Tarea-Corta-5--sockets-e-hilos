package Server;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

//import Client.Server;

import Common.Casilla;
import Common.Constantes;
import Common.Dot;
import Common.Mapa;



public class GUI implements ActionListener, Constantes{

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;

    ObjectOutputStream output;

    Socket client; //creamos el dot
    
    public GUI(){

        ventana = new JFrame();
        mapa = new Mapa(this);


        ventana.add(mapa.panelTablero);

        next = new JButton("continuar");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        Server server = new Server(dot);//creamos un servidor , recibne el dot
        Thread hilo = new Thread(server); //re
        hilo.start();

        moveDot();
        run();

    }

    @Override
    public void actionPerformed(ActionEvent e) {  
    }

    public void moveDot(){
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run(){
        while (true){



            dot.move();

         
            try {


            client = new Socket("127.0.0.1", 4445);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(dot); 
            output.flush(); //LO ENVIAMOS EL DOT COMPLETO , DOT.POSICION ACTUAL , ESTO ME ESTOY ENREDANDO
            output.close();
            client.close();

            //HACEMOS EL SOKET PARA ENVIARLE EL DOT
            moveDot();
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }

}