package Listas;

import sample.Server;


import java.util.Hashtable;

public class Esquema {
    private String nombre,ID;
    private Boolean contiene;
    private ListaTables filas=new ListaTables();
    private ListaTamaño tamaños=new ListaTamaño();
    private ListaString joins=new ListaString();

    public Esquema(String constructor) {
        constructor="Esquema1,dato1:STRING:6,dato2:INT:3";
        String[] partes=constructor.split(",");
        this.nombre=partes[0];
        this.crearfila(partes);
    }
    private void crearfila(String[] partes){
        Hashtable fila=new Hashtable();
        int cont=1;
        this.ID=partes[cont].split(":")[0];
        while (cont<partes.length) {
            String nombre = partes[cont].split(":")[0];
            String tipo = partes[cont].split(":")[1];
            int tamaño = Math.abs(Integer.parseInt(partes[cont].split(":")[2]));
            System.out.println(tipo);
            if (tipo.equals("STRING")) {
                fila.put(nombre, "");
            } else if (tipo.equals("INT")) {
                fila.put(nombre, -1);
            } else if (tipo.equals("DOUBLE")) {
                fila.put(nombre, (double) -1);
            } else if (tipo.equals("LONG")) {
                fila.put(nombre, (long) -1);
            } else if (tipo.equals("FLOAT")) {
                fila.put(nombre, (float) -1);
            }
            if ("STRING,INT,DOUBLE,LONG,FLOAT".contains(tipo)) {
                this.tamaños.addLast(new Tamaño(nombre, tamaño));
                System.out.println(tamaños.largo);
            }
            if (tipo.equals("JOIN")) {
                int i = 0;
                while (i < Server.esquemas.getLargo()) {
                    Esquema esquema = Server.esquemas.buscar(i);
                    if (nombre == esquema.nombre) {
                        joins.addFirst(nombre);
                        fila.put(nombre, esquema.filas.getHead().getNodo().get(esquema.getID()));
                        break;
                    }
                    i++;
                }
            }
            cont++;
        }
        System.out.println(fila);
        this.filas.addLast(fila);
    }

    public void añadirfila(String fila){
        fila="{dato1:perro,dato2:123}";
        Hashtable base= (Hashtable) this.filas.head.getNodo().clone();
        String[] datos= fila.split(",");
        int cont=0;
        while (cont<datos.length){
            String nombre=datos[0];
            String dato=datos[1];
            if (this.tamaños.buscartamaño(nombre)>=dato.length()){
                if (base.get(nombre).equals(""))
                base.replace(nombre,dato);
            }
            else if(this.joins.contiene(nombre)){

            }
            else {}
            cont++;
        }
        if (contiene){this.filas.head.setNodo(base);}
        else {this.filas.addLast(base);}
    }

    public boolean existe(){
    return true;
    }




    public String getNombre() {
        return nombre;
    }

    public Boolean getContiene() {
        return contiene;
    }

    public ListaTables getFilas() {
        return filas;
    }

    public ListaTamaño getTamaños() {
        return tamaños;
    }

    public ListaString getJoins() {
        return joins;
    }

    public String getID() {
        return ID;
    }
}
