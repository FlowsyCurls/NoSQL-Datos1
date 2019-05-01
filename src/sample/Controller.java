package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;

public class Controller {
    ObjectMapper objectMapper=new ObjectMapper();
    public static Logger log = LoggerFactory.getLogger(Controller.class);
    public void hola(){
//        System.out.println("estoy en la funcion");
//        ObjectNode perro = objectMapper.createObjectNode();
//        perro.put("hola", 82).put("charanco","tornado");
//        perro.put("hola", "tornado");
//        System.out.println(perro);
       try {
           Integer.parseInt("23.4");
       }
       catch (NumberFormatException e){
           e.getCause();
       }
}

    public void table() throws IOException {
//        Hashtable hashtable= new Hashtable();
//        hashtable.put("hello", "pico" );
//        hashtable.put("Paco",85);
//        hashtable.put("Paco",89);
//        Hashtable holga = new Hashtable();
//        holga= (Hashtable) hashtable.clone();
//        System.out.println(objectMapper.readValue(objectMapper.writeValueAsString(holga),Hashtable.class));
//
//        System.out.println(holga);
        Cliente cliente=new Cliente();
        String respuesta=cliente.crearEsquema("Esquema1,dato1:STRING:6,dato2:INT:3");
        System.out.println(respuesta);
//        DataInputStream datosentrada = new DataInputStream(client.getInputStream());
//        log.debug("entrada se conecto");
//        Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
//        log.debug("se creo objeto");
    }
}
