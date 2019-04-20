package sample;


import Listas.Esquema;
import Listas.ListaEsquemas;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    public static Logger log = LoggerFactory.getLogger(Server.class);
    public static ListaEsquemas esquemas= new ListaEsquemas();




    public static void main(String[] args) throws IOException {
        Server server=new Server();
    }



    @Override
    public void run() {
        try {
            ServerSocket server= new ServerSocket(9500);
            System.out.println("hola");

            while (true) {
                Socket misocket = server.accept();
                log.debug("se acepto cliente");
                DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
                String entrada = flujo_entrada.readUTF();
                System.out.println(entrada);
                log.debug("se recibio objeto");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public Server() throws IOException {
        Esquema esquema=new Esquema("");
        System.out.println(esquema.getFilas().getHead().getNodo());
        Thread hilo = new Thread(this);
        hilo.start();

    }

}
