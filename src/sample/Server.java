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
    private Accionador accionador=new Accionador();
    private int puerto=9500;




    public static void main(String[] args) throws IOException {
        Server server=new Server();
    }



    @Override
    public void run() {
        try {
            ServerSocket server= new ServerSocket(puerto);
            System.out.println("hola");

            while (true) {
                Socket misocket = server.accept();//se pone el server a la escucha
                log.debug("se acepto cliente");
                DataInputStream flujo_entrada=new DataInputStream(misocket.getInputStream());
                String entrada= flujo_entrada.readUTF();
                System.out.println(entrada);
                log.debug("se recibio objeto");
                Datos datos=objectMapper.readValue(entrada, Datos.class);
                log.debug("se creo objeto");
                //aqui empieza accionar
                datos=accionador.realizar_accion(datos);
                DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
                log.debug("se creo abertura de datos");
                datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                log.debug("se logro enviar datos");
                datosenvio.close();
                misocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public Server() throws IOException {
//        Esquema esquema=new Esquema("Esquema1,dato1:STRING:6,dato2:INT:3");
//        esquema.anadirfila("dato1:perro,dato2:222");
//        esquema.anadirfila("dato1:gato,dato2:222");
//        esquemas.addLast(esquema);
//        System.out.println(esquema.getID());
//        Esquema esquema2=new Esquema("Esquema2,Dato1:STRING:6,Esquema1:JOIN:3");
//        esquema2.anadirfila("Dato1:raton,Esquema1:gato");
//        esquema2.anadirfila("Dato1:liebre,Esquema1:perro");
//        esquemas.addLast(esquema2);
//        Esquema esquema3=new Esquema("Esquema3,DAto1:STRING:6,Esquema2:JOIN:3");
//        esquema3.anadirfila("DAto1:lobo,Esquema2:raton");
//        esquema3.anadirfila("DAto1:zorro,Esquema2:liebre");
//        esquemas.addLast(esquema3);
//        System.out.println(esquemas.getLargo());
//        System.out.println(esquemas.buscar("Esquema3").buscardatos("lobo","DAto1"));
//        System.out.println(esquemas.buscar("Esquema1").buscartodos()+"que");
//        System.out.println(esquemas.buscar("Esquema2").obtenercolumnas().getLargo());

        Thread hilo = new Thread(this);
        hilo.start();

    }

}
