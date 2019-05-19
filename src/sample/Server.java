package sample;


import Listas.Esquema;
import Listas.ListaEsquemas;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    public static Logger log = LoggerFactory.getLogger(Server.class);
    public static ListaEsquemas esquemas= new ListaEsquemas();
    private Accionador accionador=new Accionador();
    private int puerto=9500;




    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
        Server server=new Server();
    }



    @SuppressWarnings("resource")
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
        } catch (BindException b) {
        	System.out.println("\n\n\n\n\r\t\tEl server ya está corriendo...");
    	} catch (IOException e) {
            e.printStackTrace();
        }

    }




    public Server() throws IOException {
        Esquema esquema=new Esquema("Esquema1,dato1:STRING:6,dato2:INT:3");
        esquema.anadirfila("dato1:perro,dato2:222");
        esquema.anadirfila("dato1:gato,dato2:222");
        esquema.anadirfila("dato1:cobra,dato2:211");
        esquemas.addLast(esquema);
        Esquema esquema4=new Esquema("Esquema4,rato1:STRING:6,Esquema1:JOIN:3");
        esquema4.anadirfila("rato1:peluca,Esquema1:perro");
        esquema4.anadirfila("rato1:pelo,Esquema1:cobra");
        esquemas.addLast(esquema4);
//        System.out.println(esquema.getID());
        Esquema esquema2=new Esquema("Esquema2,Dato1:STRING:6,Esquema1:JOIN:3");
        esquema2.anadirfila("Dato1:raton,Esquema1:gato");
        esquema2.anadirfila("Dato1:liebre,Esquema1:perro");
        esquema2.anadirfila("Dato1:rata,Esquema1:cobra");
        esquemas.addLast(esquema2);
        Esquema esquema3=new Esquema("Esquema3,DAto1:STRING:6,Esquema2:JOIN:3,Esquema4:JOIN:3");
        esquema3.anadirfila("DAto1:lobo,Esquema2:raton,Esquema4:pelo");
        esquema3.anadirfila("DAto1:zorro,Esquema2:liebre,Esquema4:peluca");
        esquema3.anadirfila("DAto1:coyote,Esquema2:rata,Esquema4:peluca");
        esquemas.addLast(esquema3);
        System.out.println(esquemas.getLargo());
        System.out.println(esquemas.buscar("Esquema3").buscardatos("edificio","DAto1"));
        System.out.println("\n\n\n\n");
        System.out.println(esquemas.buscar("Esquema1").buscartodos()+"1");
        System.out.println("\n\n");
        System.out.println(esquemas.buscar("Esquema2").buscartodos()+"2");
        System.out.println("\n\n");
        System.out.println(esquemas.buscar("Esquema3").buscardatosjoin("Esquema1","dato2","222")+":3");
	    
	esquema.Meter_refe(NombreArbol.AVL, "dato1");//esto hace el arbol con los indices
        esquema.Meter_refe(NombreArbol.ArbolB,"dato2");//donde dato5 
	System.out.print(esquema.arboles.Search(0).getAVL().search("perro"));//se tiene que espedificar cual arbol tiene los datos
        System.out.print(esquema.repetidos("dato2")+"\n");//ver que si tiene repetidos la columna de dato2  
        Thread hilo = new Thread(this);
        hilo.start();

    }

}
