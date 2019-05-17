package Listas;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import sample.Controller;
import sample.Server;

import java.util.ArrayList;
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


    public Esquema (String constructor,Boolean cliente) {
        String[] partes = constructor.split(",");
        this.nombre=partes[0];
        int cont = 1;
        this.ID = partes[cont].split(":")[0];
        while (cont < partes.length) {
            String nombre = partes[cont].split(":")[0];
            String tipo = partes[cont].split(":")[1];
            int tamano = Math.abs(Integer.parseInt(partes[cont].split(":")[2]));
            if ("STRING,INT,DOUBLE,LONG,FLOAT".contains(tipo)) {
                this.getTamanos().addLast(new Tamano(nombre, tamano));
                System.out.println(getTamanos().largo);
            } else {
                getMijoins().addFirst(nombre);
            }
            cont++;
        }
    }

    public Esquema() {

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

    public void anadirfila(String fila) throws TamanoException, DatoNoExistenteException, NumberFormatException, DatosUsadosException, EsquemaNuloException {
        System.out.println(fila);
        Hashtable base= (Hashtable) this.filas.head.getNodo().clone();
        String[] datos= fila.split(",");
        int cont=0;
        while (cont<datos.length){
            String nombre=datos[cont].split(":")[0];
            String dato=datos[cont].split(":")[1];
            if (!nombre.equals(this.ID) || !this.filas.existe(dato,nombre)) {
                if (this.getTamanos().contiene(nombre)) {
                    System.out.println(this.getTamanos().buscartamano(nombre));
                    if (this.getTamanos().buscartamano(nombre) >= dato.length()) {
                        System.out.println("voy a cambiar dato");
                        base.replace(nombre, this.filas.convertir(dato, nombre));
                    } else {
                        throw new TamanoException();
                    }
                } else if (this.getMijoins().contiene(nombre)) {
                    Esquema esquema = Server.esquemas.buscar(nombre);
                    if (!esquema.tiene_filas){throw new EsquemaNuloException();}
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

    public String buscardatos(String dato,String nombre) throws StringIndexOutOfBoundsException, EsquemaNuloException {
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
        if (!this.tiene_filas){throw new EsquemaNuloException();}
        return datos;
    }
    public String buscardatosjoin(String nombre_join,String nombre,String dato) throws StringIndexOutOfBoundsException, EsquemaNuloException {//usado si el parametro de busqueda es por el de un dato en un join que no sea el ID
        String datos="";
        int i=0;
        Esquema esquema=Server.esquemas.buscar(nombre_join);
        ListaString IDs= new ListaString();
        while (i<esquema.filas.getLargo()){
            Hashtable linea=esquema.filas.buscar(i);
            if (linea.get(nombre).toString().equals(dato)){
                IDs.addFirst(linea.get(esquema.getID()).toString());
            }
            i++;
        }
        ListaString thisIDs= this.buscarhaciaatras(IDs,esquema.getNombre(),esquema.getID(),this.nombre);
        thisIDs.Print();
        thisIDs.limpiarlista();
        thisIDs.Print();
        int cont=0;
        while (cont<thisIDs.getLargo()) {
            Hashtable fila = this.filas.buscar(thisIDs.buscar(cont),this.ID);
            datos = datos.concat(this.crearstring(fila.get(this.ID).toString(), this.getID()));
            datos = datos.concat(";");
            cont++;
        }
        if (!this.tiene_filas){throw new EsquemaNuloException();}
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


    private String crearstring(String dato,String nombre) throws EsquemaNuloException {
        Hashtable fila= this.filas.buscar(dato,nombre);
        String string="";
        int i=0;
        while (i<this.tamanos.getLargo()){
            string=string.concat( fila.get(this.tamanos.buscarnombre(i))+",");
            i++;
            if (this.getMijoins().getLargo()!=0){
                int cont=0;
                while (cont<getMijoins().getLargo()){
                    Esquema esquema=Server.esquemas.buscar(getMijoins().buscar(cont));
                    string=string.concat("_"+",");
                    string=string.concat(esquema.buscardatos(fila.get(getMijoins().buscar(cont)).toString(),esquema.getID())+",");
                    cont++;
                }

            }
        }
        System.out.println("esto envio a concatenar");
        System.out.println(string.substring(0,string.length()-1));
        return string.substring(0,string.length()-1);
    }



    public ArrayTuple getDatosArray() throws EsquemaNuloException{
        ArrayTuple tuple = new ArrayTuple();
        ArrayList<String> keys = this.obtenercolumnas().getArraycolumnas(this.obtenercolumnas());
        String[] cadena = this.buscartodos().split(",");
        for (int i=0; i<cadena.length; i++) {
            String nombre = keys.get(0);
            String dato=cadena[i].split(":")[0];
            tuple.addAll(nombre, dato);
        }
        return tuple;
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
            listaString.concatenarlistas(Controller.listaEsquemas.buscar(nombreesquema).obtenercolumnas());
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

    public ArrayList<String> getMijoinsArray() {
        ArrayList<String> array = new ArrayList<String>();
        Nodo<String> tmp = this.mijoins.getHead();
        for (int i=0; i<this.mijoins.getLargo(); i++) {
            array.add(tmp.getNodo());
            tmp = tmp.getNext();
        }
        return array;
    }


    public void cambiarnombrecolumna(String nombre, String nuevonombre){
        int cont=0;
        while (cont<this.filas.getLargo()){
            Hashtable fila=this.filas.buscar(cont);
            Object dato=fila.get(nombre);
            fila.remove(nombre);
            System.out.println(dato);
            fila.put(nuevonombre,dato);
            cont++;
        }
    }

    public void cambiardato(String ID, String columna, String nuevodato) throws DatoNoExistenteException {
        System.out.println("ID por editar : " +ID);
        System.out.println("COLUMNA por editar : " +columna);
        System.out.println("NUEVODATO por editar : " +nuevodato);
        Hashtable fila=this.filas.buscar(ID,this.ID);
        if (this.getMijoins().contiene(columna)) {
            Esquema esquema = Server.esquemas.buscar(columna);
            System.out.println(esquema.getFilas().getHead().getNodo());
            if (esquema.existe(nuevodato, esquema.getID())) {
                System.out.println("voy a cambiar dato en join");
                fila.replace(columna, this.filas.convertir(nuevodato, columna));
            }else {
                throw new DatoNoExistenteException(nombre);}
        }else {
            fila.replace(columna, this.filas.convertir(nuevodato, columna));
        }
    }



    public String crearconstructor(){
        String constructor="";
        constructor=constructor.concat(this.nombre+",");
        System.out.println(this.nombre);
        System.out.println(constructor);
        int cont=0;
        while (cont<this.tamanos.getLargo()){
            String nombre=this.tamanos.buscarnombre(cont);
            constructor=constructor.concat(nombre+":"+obtenertipo(nombre)+":"+ this.tamanos.buscartamano(nombre)+",");
            cont++;
        }
        cont=this.mijoins.getLargo()-1;
        while (cont>=0){
            String nombre=this.mijoins.buscar(cont);
            constructor=constructor.concat(nombre+":"+"JOIN"+":"+ this.tamanos.buscartamano(nombre)+",");
            cont--;
        }
        System.out.println(constructor);
        constructor=constructor.substring(0,constructor.length()-1);
        return constructor;
    }
    public ListaString crearconstructoresdatos(){
        ListaString constructores=new ListaString();
        if (this.tiene_filas){
            int cont=0;
            while (cont<this.filas.getLargo()){
                String constructor="";
                Hashtable fila=this.filas.buscar(cont);
                int n=0;
                while (n<this.tamanos.getLargo()) {
                    constructor=constructor.concat(this.tamanos.buscarnombre(n) + ":" + fila.get(this.tamanos.buscarnombre(n)) + ",");
                    n++;
                }
                n=0;
                while (n<this.mijoins.getLargo()){
                    constructor=constructor.concat(this.mijoins.buscar(n) + ":" + fila.get(this.mijoins.buscar(n)) + ",");
                    n++;
                }
                constructores.addFirst(constructor.substring(0,constructor.length()-1));
                cont++;
            }
        }
        return constructores;
    }
    private ListaString buscarhaciaatras(ListaString IDs, String nombre, String nombre_join, String nombre_fin){
        Esquema esquema=Server.esquemas.buscar(nombre);
        int cont= 0;
        ListaString thisIDs=new ListaString();
        ListaString nuevoIDs=new ListaString();
        while (cont<IDs.getLargo()){
            String ID=IDs.buscar(cont);
            int i=0;
            while (i<esquema.filas.getLargo()){
                Hashtable linea=esquema.filas.buscar(i);
                if (linea.get(nombre_join).toString().equals(ID)){
                    nuevoIDs.addFirst(linea.get(esquema.getID()).toString());
                }
                i++;
            }
            cont++;
        }
        nuevoIDs.Print();
        System.out.println(nombre);
        System.out.println(nombre_fin);
        if (nombre.equals(nombre_fin)){
            return nuevoIDs;
        }
        else if(esquema.getJoinde().getLargo()==0){
            System.out.println("no tengo mas joins");
            return new ListaString();
        }
        else {
            int n=0;
            while (n<esquema.getJoinde().getLargo()){
                ListaString listatmp=esquema.buscarhaciaatras(nuevoIDs,esquema.getJoinde().buscar(n),esquema.getNombre(),nombre_fin);
                thisIDs.concatenarlistas(listatmp);
                n++;
            }
            return thisIDs;
        }
    }
    public ListaString obtenercolumnasparaedit(){
        ListaString listaString=new ListaString();
        int cont=this.mijoins.getLargo()-1;

        while (cont>=0){
            String nombreesquema=this.mijoins.buscar(cont);
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
    public String buscardatosparaedit() throws EsquemaNuloException {
        String datos="";
        int cont=0;
        while (cont<this.filas.getLargo()){
            System.out.println("entro aca");
            Hashtable fila=this.filas.buscar(cont);
            datos=datos.concat(this.crearstringparaedit(fila.get(this.ID).toString(),this.getID()));
            datos=datos.concat(";");
            cont++;
        }
        datos=datos.substring(0,datos.length()-1);
        if (!this.tiene_filas){throw new EsquemaNuloException();}
        return datos;
    }
    private String crearstringparaedit(String dato,String nombre) {
        Hashtable fila= this.filas.buscar(dato,nombre);
        String string="";
        int i=0;
        while (i<this.tamanos.getLargo()){
            string=string.concat( fila.get(this.tamanos.buscarnombre(i))+",");
            i++;
        }
        if (this.getMijoins().getLargo()!=0){
            int cont=0;
            while (cont<getMijoins().getLargo()){
                string=string.concat(fila.get(mijoins.buscar(cont))+",");
                cont++;
            }
        }
        return string.substring(0,string.length()-1);
    }



    private String obtenertipo(String nombre){
        String tipo="";
        Object base=this.filas.getHead().getNodo().get(nombre);
        if (base instanceof String) {
            tipo="STRING";
        }
        else if (base instanceof Integer) {
            tipo="INT";
        }
        else if (base instanceof Double) {
            tipo="DOUBLE";
        }
        else if (base instanceof Long) {
            tipo="LONG";
        }
        else if (base instanceof Float) {
            tipo="FLOAT";
        }
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String Nombre0() {
        return "Nombre0";
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
