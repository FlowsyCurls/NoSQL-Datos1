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
        Esquema esquema=new Esquema("Esquema1,dato1:STRING:6,dato2:INT:3");
        esquema.añadirfila("dato1:perro,dato2:222");
        esquema.añadirfila("dato1:gato,dato2:222");
        esquemas.addLast(esquema);
        System.out.println(esquema.getID());
        Esquema esquema2=new Esquema("Esquema2,Dato1:STRING:6,Esquema1:JOIN:3");
        esquema2.añadirfila("Dato1:raton,Esquema1:gato");
        esquema2.añadirfila("Dato1:liebre,Esquema1:perro");
        esquemas.addLast(esquema2);
        Esquema esquema3=new Esquema("Esquema3,DAto1:STRING:6,Esquema2:JOIN:3");
        esquema3.añadirfila("DAto1:lobo,Esquema2:raton");
        esquema3.añadirfila("DAto1:zorro,Esquema2:liebre");
        esquemas.addLast(esquema3);
        System.out.println(esquema.getFilas().getHead().getNodo()+","+esquema2.getFilas().buscar(0));
        System.out.println(esquemas.getLargo());
        System.out.println(esquemas.buscar("Esquema3").buscardatos("lobo","DAto1"));
        System.out.println(esquemas.buscar("Esquema1").buscardatos("222","dato2"));
        System.out.println(esquemas.buscar("Esquema3").buscartodos());

        Thread hilo = new Thread(this);
        hilo.start();

    }

}
