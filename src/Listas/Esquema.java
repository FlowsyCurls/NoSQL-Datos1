package Listas;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import sample.Controller;
import sample.Datos;
import sample.IndiceBoolean;
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
    public Lista<Indice> arboles=new Lista<>();
    public int contador =0;
    public ListaIndice columnasconindice=new ListaIndice();


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
        ListaString lista=this.obtenercolumnasparaedit();
        cont=0;
        while (cont<lista.getLargo()){
            this.columnasconindice.addLast(new IndiceBoolean(lista.buscar(cont)));
            cont++;
        }
    }
    public boolean deleteIndice(String columna,String Arbol) {
        Indice indice=arboles.Search(columna);
        if ("ArbolAA".equals(Arbol)) {
            indice.setAA(null);
        } else if ("ArbolB".equals(Arbol)) {
            indice.setB(null);
        } else if ("ArbolBinario".equals(Arbol)) {
            indice.setBinario(null);
        } else if ("ArbolBPlus".equals(Arbol)) {
            indice.setBPlus(null);
        } else if ("ArbolRB".equals(Arbol)) {
            indice.setRB(null);
        } else {
            indice.setAVL(null);
        }
        if (indice.estoyvacio()){
            return arboles.deleteNode(columna);
        }
        return true;
    }
	public void MeterFilaArbol(Hashtable fila){
    	int parada=fila.size();
    	for (int i=0;parada>i;i++) {
    		String columna =fila.keySet().toArray()[i].toString();//me da el nombre de la columna del dato
    		Indice nuevo=arboles.Search(columna);//segun que columna, me busqueda el indice que tenga ese nombre
    		if (nuevo.getAA()!=null) {//ver que arbol tiene datos y cuales no
    			nuevo.getAA().insert(fila.get(columna).toString(), fila);
    		}else if (nuevo.getAVL()!=null) {
    			nuevo.getAVL().insert(fila.get(columna).toString(), fila);
    		}else if (nuevo.getB()!=null) {
    			nuevo.getB().insert(fila.get(columna).toString(), fila);
    		}else if (nuevo.getBinario()!=null) {
    			nuevo.getBinario().insert(fila.get(columna).toString(), fila);
    		}else if (nuevo.getBPlus()!=null) {
    			nuevo.getBPlus().insert(fila.get(columna).toString(), fila);
    		}else{
    			nuevo.getRB().insert(fila.get(columna).toString(), fila);
    		}
    	}
    }
    public boolean VNReferencia(String columna){
        if (arboles.Search(columna)==null){ return true;}
        else {return false;}
    }
    public void NReferencia(String columna,NombreArbol dato) {
        Indice nuevo = arboles.Search(columna);//El nodo donde quiero hacer un nuevo arbol
        referencia hola = new referencia();//la clase que me mete las varas al arbol
        if (dato == NombreArbol.ArbolB) {
            nuevo.setB(hola.setArbolB(nuevo.generateB(), filas, columna));
        } else if (dato == NombreArbol.ArbolRB) {
            nuevo.setRB(hola.setArbolRB(nuevo.generateRB(), filas, columna));
        } else if (dato == NombreArbol.ArbolAA) {
            nuevo.setAA(hola.setArbolAA(nuevo.generateAA(), filas, columna));
        } else if (dato == NombreArbol.ArbolBPlus) {
            nuevo.setBPlus(hola.setArbolBPlus(nuevo.generateBPlus(), filas, columna));
        } else if (dato == NombreArbol.ArbolBinario) {
            nuevo.setBinario(hola.setArbolBinario(nuevo.generateBinario(), filas, columna));
        } else {
            nuevo.setAVL(hola.setArbolAVL(nuevo.generateAVL(), filas, columna));
        }
    }



    public Esquema () {
    }
    public void Meter_refe(NombreArbol dato,String key){//segun el tipo de dato que se meta me genera un arbol con las columnas
    	referencia hola=new referencia();//la clase referencia me mete los datos al arbol
	    if (dato==NombreArbol.ArbolB) {
	    	Indice prueba= new Indice(key,NombreArbol.ArbolB);/// aqui vamos a meter a la lista de indices su Indice(arbol), su nombre de columna(key)
	    	hola.setArbolB(prueba.getB(), filas, key);///para asi poder buscar facilmente en su lista de indices, un indice por su nombre de columna
	    	arboles.addlist(prueba,key);//hay que hacer un caso para cada arbol
	    	
	    }else if (dato==NombreArbol.ArbolRB) {
	    	Indice prueba= new Indice(key,NombreArbol.ArbolRB);
	    	hola.setArbolRB(prueba.getRB(), filas, key);
	    	arboles.addlist(prueba,key);
	    	
	    }else if (dato==NombreArbol.ArbolAA) {
	    	Indice prueba= new Indice(key,NombreArbol.ArbolAA);
	    	hola.setArbolAA(prueba.getAA(), filas, key);
	    	arboles.addlist(prueba,key);
	    	
	    }else if (dato==NombreArbol.ArbolBPlus) {
	    	Indice prueba= new Indice(key,NombreArbol.ArbolBPlus);
	    	hola.setArbolBPlus(prueba.getBPlus(), filas, key);
	    	arboles.addlist(prueba,key);
	    	
	    }else if (dato==NombreArbol.ArbolBinario) {
	    	Indice prueba= new Indice(key,NombreArbol.ArbolBinario);
	    	hola.setArbolBinario(prueba.getBinario(), filas, key);
	    	arboles.addlist(prueba,key);
	    	
	   	}else {
	   		Indice prueba= new Indice(key,NombreArbol.AVL);
	    	hola.setArbolAVL(prueba.getAVL(), filas, key);
	    	arboles.addlist(prueba,key);
	    }
        contador++;
    }
    public boolean repetidos(String key){
    	return arboles.verDupl(filas, key);
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
        System.out.println(datos);
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

    public String buscarporindice(Datos datos) throws DatoNoExistenteException, EsquemaNuloException {
        Hashtable fila;
        String dato= "";
        if ("ArbolAA".equals(datos.getIndice())) {
            System.out.println(this.arboles.Search(datos.getColumna()).getAA());
            //busque en el server su lista de esquemas, el esquema donde se hace la busqueda, buscar por el nombre de la columna(key), le espesifico el arbol donde hacer la
             fila= (Hashtable) this.arboles.Search(datos.getColumna()).getAA().search(datos.getDato());//busqueda del dato
        } else if ("ArbolB".equals(datos.getIndice())) {
            //y asi con todos los datos, solo que se tiene que espesificar el arbol donde se hace la busqueda
             fila= (Hashtable) this.arboles.Search(datos.getColumna()).getB().search(datos.getDato());
        } else if ("ArbolBinario".equals(datos.getIndice())) {

             fila= (Hashtable)this.arboles.Search(datos.getColumna()).getBinario().search(datos.getDato());
        } else if ("ArbolBPlus".equals(datos.getIndice())) {

             fila= (Hashtable)this.arboles.Search(datos.getColumna()).getBPlus().search(datos.getDato());
        } else if ("ArbolRB".equals(datos.getIndice())) {

             fila= (Hashtable)this.arboles.Search(datos.getColumna()).getRB().search(datos.getDato());
        } else {
             fila= (Hashtable) this.arboles.Search(datos.getColumna()).getAVL().search(datos.getDato());
        }
        if (fila==null){
            throw new  DatoNoExistenteException();
        }else {
            dato=this.crearstring(fila.get(this.getID()).toString(),this.getID());
        }
        return dato;
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

    public ListaIndice getColumnasconindice() {
        return columnasconindice;
    }

    public void setColumnasconindice(ListaIndice columnasconindice) {
        this.columnasconindice = columnasconindice;
    }
}
