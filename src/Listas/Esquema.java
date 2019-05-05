package Listas;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import sample.Server;


import java.util.Hashtable;

public class Esquema {
    private String nombre,ID;
    private Boolean tiene_filas;
    private ListaTables filas=new ListaTables();
    private ListaTamano tamanos=new ListaTamano();
    private ListaString mijoins =new ListaString();
    public ListaString joinde = new ListaString();

    public Esquema(String constructor) throws EsquemaNuloException, DatosUsadosException {
        String[] partes=constructor.split(",");
        this.tiene_filas=false;
        this.nombre=partes[0];
        this.crearfila(partes);

    }
    private void crearfila(String[] partes) throws NumberFormatException, EsquemaNuloException, DatosUsadosException {
        Hashtable fila=new Hashtable();
        int cont=1;
        this.ID=partes[cont].split(":")[0];
        while (cont<partes.length) {
            String nombre = partes[cont].split(":")[0];
            String tipo = partes[cont].split(":")[1];
            int tamano = Math.abs(Integer.parseInt(partes[cont].split(":")[2]));
            System.out.println(tipo);
            if (!fila.containsKey(nombre)) {
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
                    this.getTamanos().addLast(new Tamano(nombre, tamano));
                    System.out.println(getTamanos().largo);
                }
                if (tipo.equals("JOIN")) {
                    Esquema esquema = Server.esquemas.buscar(nombre);
                    if (esquema == null) {
                        throw new EsquemaNuloException();
                    } else {
                        getMijoins().addFirst(nombre);
                        esquema.joinde.addFirst(this.nombre);
                        System.out.println(getMijoins().head.getNodo());
                        fila.put(nombre, esquema.filas.getHead().getNodo().get(esquema.getID()));
                    }
                }
            }
            else {throw new DatosUsadosException();}
            cont++;
        }
        System.out.println(fila);
        this.filas.addLast(fila);
    }

    public void anadirfila(String fila) throws TamanoException, DatoNoExistenteException, NumberFormatException, DatosUsadosException {
        Hashtable base= (Hashtable) this.filas.head.getNodo().clone();
        String[] datos= fila.split(",");
        int cont=0;
        while (cont<datos.length){
            String nombre=datos[cont].split(":")[0];
            String dato=datos[cont].split(":")[1];
            if (!nombre.equals(this.ID) && !this.filas.existe(dato,nombre)) {
                if (this.getTamanos().contiene(nombre)) {
                    System.out.println(this.getTamanos().buscartamano(nombre));
                    if (this.getTamanos().buscartamano(nombre) >= dato.length()) {
                        if (!nombre.equals(this.ID)) {
                            System.out.println("voy a cambiar dato");
                            base.replace(nombre, this.filas.convertir(dato, nombre));
                        }
                    } else {
                        throw new TamanoException();
                    }
                } else if (this.getMijoins().contiene(nombre)) {
                    Esquema esquema = Server.esquemas.buscar(nombre);
                    System.out.println(esquema.getFilas().getHead().getNodo());
                    if (esquema.existe(dato, esquema.getID())) {
                        System.out.println("voy a cambiar dato en join");
                        base.replace(nombre, this.filas.convertir(dato, nombre));
                    } else {
                        throw new DatoNoExistenteException(nombre);
                    }
                }
            }
            else {throw new  DatosUsadosException();}
            cont++;
        }
        if (!tiene_filas){
            this.filas.head.setNodo(base);
            tiene_filas=true;
        }
        else {this.filas.addLast(base);}
    }

    public void eliminarfila(String dato) throws DatosUsadosException {
        if (this.datousado(dato)){
            throw new DatosUsadosException();
        }
        else {
            if (this.filas.getLargo()>1){
            this.filas.eliminar(dato,this.ID);
            }
            else{
                this.tiene_filas=false;
            }
        }
    }

    public String buscardatos(String dato,String nombre)throws StringIndexOutOfBoundsException{
        String datos="";
        if (nombre.equals(this.ID)){
            if (this.filas.existe(dato,nombre)){datos = crearstring(dato,nombre);}
        }
        else {
            int cont=0;
            while (cont<this.filas.getLargo()){
                Hashtable fila=this.filas.buscar(cont);
                if (fila.get(nombre).toString().equals(dato)){
                    datos=datos.concat(this.crearstring(fila.get(this.ID).toString(),this.getID()));
                    datos=datos.concat(";");
                }
                cont++;
            }
            datos=datos.substring(0,datos.length()-1);
        }
        return datos;
    }
    public String buscardatosjoin(ListaString joins,String nombre,String dato) throws StringIndexOutOfBoundsException{//usado si el parametro de busqueda es por el de un dato en un join que no sea el ID

        String datos="";
        int cont=0;
        while (cont<this.filas.getLargo()){
            Hashtable fila=filas.buscar(cont);
            Hashtable filatmp=filas.buscar(cont);
            int i=0;
            Hashtable filajoin=null;
            while (i<joins.getLargo()){//aqui se mueve desde el join más cercano hasta el join final donde se encuentra el nombre de la columna
                Esquema esquema=Server.esquemas.buscar(joins.buscar(i));
                filajoin=esquema.getFilas().buscar(filatmp.get(joins.buscar(i)).toString(),esquema.getID());
                filatmp=filajoin;
                i++;
            }

            if (filajoin.get(nombre).toString().equals(dato)){
                datos=datos.concat(this.crearstring(fila.get(this.ID).toString(),this.getID()));
                datos=datos.concat(";");
            }
            cont++;
        }
        datos=datos.substring(0,datos.length()-1);
        return datos;
    }

    public String buscartodos() throws EsquemaNuloException {
        String datos="";
        int cont=0;
        while (cont<this.filas.getLargo()){
            System.out.println("entro aca");
            Hashtable fila=this.filas.buscar(cont);
            datos=datos.concat(this.crearstring(fila.get(this.ID).toString(),this.getID()));
            datos=datos.concat(";");
            cont++;
        }
        datos=datos.substring(0,datos.length()-1);
        if (!this.tiene_filas){throw new EsquemaNuloException();}
        return datos;
    }

    private String crearstring(String dato,String nombre){
        Hashtable fila= this.filas.buscar(dato,nombre);
        String string=fila.toString();
        if (this.getMijoins().getLargo()!=0){
            int cont=0;
            while (cont<getMijoins().getLargo()){
                Esquema esquema=Server.esquemas.buscar(getMijoins().buscar(cont));
                string=string.concat(":"+esquema.getNombre()+"=");
                string=string.concat(esquema.buscardatos(fila.get(getMijoins().buscar(cont)).toString(),esquema.getID()));
                cont++;
            }
        }
        return string;
    }

    public Boolean existe(String dato,String nombre){
        return this.filas.existe(dato,nombre);
    }

    public Boolean existejoin(){
        Boolean existe =false;
        if (this.joinde.getLargo()>0){existe=true;}
        return existe;
    }
    private Boolean datousado(String dato){
        Boolean usado=false;
        int cont=0;
        while (cont< joinde.largo) {
            if (Server.esquemas.buscar(joinde.buscar(cont)).existe(dato,this.nombre)){
                usado=true;
                break;
            }
            cont++;
        }
        return usado;
    }
    public ListaString obtenercolumnas(){
        ListaString listaString=new ListaString();
        int cont=this.mijoins.getLargo()-1;
        while (cont>=0){
            String nombreesquema=this.mijoins.buscar(cont);
            listaString.concatenarlistas(Server.esquemas.buscar(nombreesquema).obtenercolumnas());
            listaString.addFirst(nombreesquema);
            cont--;
        }
        cont=this.tamanos.getLargo()-1;
        while (cont>=0){
            listaString.addFirst(this.tamanos.buscarnombre(cont));
            cont--;
        }
        return listaString;
    }



    public String getNombre() {
        return nombre;
    }

    public Boolean getTiene_filas() {
        return tiene_filas;
    }

    public ListaTables getFilas() {
        return filas;
    }

    public ListaTamano getTamanos() {
        return tamanos;
    }

    public ListaString getMijoins() {
        return mijoins;
    }

    public String getID() {
        return ID;
    }

    public ListaString getJoinde() {
        return joinde;
    }

    public void setJoinde(ListaString joinde) {
        this.joinde = joinde;
    }
	public void setMijoins(ListaString mijoins) {
		this.mijoins = mijoins;
	}
	public void setTamanos(ListaTamano tamanos) {
		this.tamanos = tamanos;
	}
    
}
