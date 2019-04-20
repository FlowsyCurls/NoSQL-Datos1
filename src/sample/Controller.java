package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Hashtable;

public class Controller {
    ObjectMapper objectMapper=new ObjectMapper();
    public void hola(){
        System.out.println("estoy en la funcion");
        ObjectNode perro = objectMapper.createObjectNode();
        perro.put("hola", 82).put("charanco","tornado");
        perro.put("hola", "tornado");
        System.out.println(perro);
    }

    public void table() throws IOException {
        Hashtable hashtable= new Hashtable();
        hashtable.put("hello", "pico" );
        hashtable.put("Paco",85);
        hashtable.put("Paco",89);
        Hashtable holga = new Hashtable();
        holga= (Hashtable) hashtable.clone();
        System.out.println(objectMapper.readValue(objectMapper.writeValueAsString(holga),Hashtable.class));

        System.out.println(holga);
    }
}
