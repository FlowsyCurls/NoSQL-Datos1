package Listas;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import sample.Controller;
import sample.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;


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
            while (i<joins.getLargo()){//aqui se mueve desde el join mÃ¡s cercano hasta el join final donde se encuentra el nombre de la columna
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
//            System.out.println("entro aca");
            Hashtable fila=this.filas.buscar(cont);
            datos=datos.concat(this.crearstring(fila.get(this.ID).toString(),this.getID()));
            datos=datos.concat(";");
            cont++;
        }
        datos=datos.substring(0,datos.length()-1);
        if (!this.tiene_filas){throw new EsquemaNuloException();}
        return datos;
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
            listaString.concatenarlistas(Controller.listaEsquemas.buscar(nombreesquema).obtenercolumnas());
            listaString.addFirst(nombreesquema);
            cont--;
        }
        cont=this.tamanos.getLargo()-1;
        while (cont>=0){
            listaString.addFirst(this.tamanos.buscarnombre(cont));
            cont--;
        }
        listaString.addFirst("ID ("+this.getNombre()+")");
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
    
	public ArrayList<String[]> getcolxrow(String cadena){
		System.out.println("\ncadena  :  "+cadena);
		/*sacar las filas de la cadena*/
		String[] filas = cadena.split(";");
		/*prints*/
//		Esquema.printArray(filas,"filas");
		return getcolxrow("", new ListaString(), filas, null);
	}
	
	
	private ArrayList<String[]> getcolxrow(String stringR, ListaString C, String[] filas, String JOIN){
		printArray(filas, "filas");
		//condicion de parada, si las filas son iguales a cero.
		if (filas.length==0) {
			//con las columnas
			ArrayList<String> A = new ArrayList<>();
			int l=C.getLargo();
			Nodo<String> tmp = C.getHead();
			while (tmp!=null) {
				A.add(tmp.getNodo());
				tmp=tmp.getNext();
			}
			//con las filas
			ArrayList<String[]> R = new ArrayList<>();
			String[] catenation = stringR.split(";");
			System.out.println(catenation);
			for (int e=0; e<catenation.length; e++) {
				String[] r = catenation[e].split(",");
				System.out.println(catenation[e].split(","));
				R.add(r);
				}
			
			R.add(stringR.split(";"));
			System.out.println("num filas -> "+R.size());
			/*prints*/
			System.out.println("\rtamaño listastring -> "+l);
			System.out.println("num colm -> "+A.size());
			System.out.println("num filas -> "+R.size());
			return R;
		}else {
			//si las filas no son iguales a cero entonces...
			String[] linkedKey = filas[0].split(":");
			//condicion para saber si es de un join o no.			
			if (JOIN==null) {
				if (linkedKey.length ==1) { 
					String[] vrfNumberROWS = linkedKey[0].substring(1, filas[0].length()-1).split(",");
					for (int j=0; j<vrfNumberROWS.length; j++) {
						/*sacar por pares (dato con su respectiva columna)*/
						Esquema.printArray(vrfNumberROWS, "vrfNumberROWSsolo >>");
						String[] parColumnaxDato = vrfNumberROWS[j].split("=");
						//important variables...
						String nombreColumna = parColumnaxDato[0];
						String datoColumna =  parColumnaxDato[1];
						C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
						stringR = stringR+","+datoColumna; //columnas en el string FILAS
						System.out.println("\r>> SOLO INFORMATION");
						System.out.println("\tnombre : "+nombreColumna);
						System.out.println("\tdato : "+datoColumna+"\n");
					}
					return getcolxrow(stringR, C, trimArray(filas, 1, filas.length), null);
				}else {
					/*prints*/
	//				Esquema.printArray(linkedKey,"linkedKey");
					//si existe un join dentro...
					if (linkedKey.length ==2) {
						//agregar nombre del join
						C.addFirst(linkedKey[1].split("=")[0]);
						stringR = stringR+",_";
						/*agregar primero lo que est[a antes del join*/
						System.out.println(linkedKey[0]);
						String[] vrfNumberCOLUMNS = linkedKey[0].substring(1, linkedKey[0].length()-1).split(",");
						for (int j=0; j<vrfNumberCOLUMNS.length; j++) {
							/*sacar por pares (dato con su respectiva columna)*/
							Esquema.printArray(vrfNumberCOLUMNS, "vrfNumberROWS_beforeJoin >>");
							String[] parColumnaxDato = vrfNumberCOLUMNS[j].split("=");
							//important variables...
							String nombreColumna = parColumnaxDato[0];
							String datoColumna =  parColumnaxDato[1];
							C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
							stringR = stringR+","+datoColumna; //columnas en el string FILAS
							/*prints*/
							System.out.println("\r>> SOLO INFORMATION");
							System.out.println("\tnombre : "+nombreColumna);
							System.out.println("\tdato : "+datoColumna+"\n");
							
						}
						String linked = linkedKey[1].split(",")[1];
						linked= linked.substring(0, linked.length()-1);
						System.out.println("liiiiiinked joiin2:  "+linked);
						return getcolxrow(stringR, C, filas, linked); //se envia el join en forma recursiva.
					}
				}
			}else {
				if (linkedKey.length ==1) { 
					String[] vrfNumberROWS = JOIN.substring(1, JOIN.length()-1).split(",");
					for (int j=0; j<vrfNumberROWS.length; j++) {
						/*sacar por pares (dato con su respectiva columna)*/
						Esquema.printArray(vrfNumberROWS, "vrfNumberROWSreplicaDeJoin >>");
						String[] parColumnaxDato = vrfNumberROWS[j].split("=");
						//important variables...
						String nombreColumna = parColumnaxDato[0];
						String datoColumna =  parColumnaxDato[1];
						C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
						stringR = stringR+","+datoColumna; //columnas en el string FILAS
						System.out.println("\r>> SOLO INFORMATION");
						System.out.println("\tnombre : "+nombreColumna);
						System.out.println("\tdato : "+datoColumna+"\n");
					}return getcolxrow(stringR, C, trimArray(filas, 1, filas.length), null);
				}else {
					/*prints*/
//					Esquema.printArray(linkedKey,"linkedKey");
					//si existe un join dentro...
					if (linkedKey.length ==2) {
						/*agregar primero lo que esta antes del join*/
						String[] vrfNumberCOLUMNS_lastjoin = JOIN.substring(1).split(",");
						for (int j=0; j<vrfNumberCOLUMNS_lastjoin.length; j++) {
							/*sacar por pares (dato con su respectiva columna)*/
							Esquema.printArray(vrfNumberCOLUMNS_lastjoin, "vrfNumberROWS_lastjoin >>");
							String[] parColumnaxDato = vrfNumberCOLUMNS_lastjoin[j].split("=");
							//important variables...
							String nombreColumna = parColumnaxDato[0];
							String datoColumna =  parColumnaxDato[1];
							C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
							stringR = stringR+","+datoColumna; //columnas en el string FILAS
							System.out.println("\r>> SOLO INFORMATION");
							System.out.println("\tnombre : "+nombreColumna);
							System.out.println("\tdato : "+datoColumna+"\n");
						}
						//agregar nombre del join
						C.addFirst(linkedKey[1].split("=")[0]);
						stringR = stringR+",_";
						/*ahora el que esta antes del join actual.*/
						System.out.println(linkedKey[0]);
						String[] vrfNumberCOLUMNS_currentjoin = linkedKey[0].substring(1, linkedKey[0].length()-1).split(",");
						for (int j=0; j<vrfNumberCOLUMNS_currentjoin.length; j++) {
							/*sacar por pares (dato con su respectiva columna)*/
							Esquema.printArray(vrfNumberCOLUMNS_currentjoin, "vrfNumberROWS_currentjoin >>");
							String[] parColumnaxDato = vrfNumberCOLUMNS_currentjoin[j].split("=");
							//important variables...
							String nombreColumna = parColumnaxDato[0];
							String datoColumna =  parColumnaxDato[1];
							C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
							stringR = stringR+","+datoColumna; //columnas en el string FILAS
							/*prints*/
							System.out.println("\r>> SOLO INFORMATION");
							System.out.println("\tnombre : "+nombreColumna);
							System.out.println("\tdato : "+datoColumna+"\n");
						}
						String linked = linkedKey[1].split(",")[1];
						linked= linked.substring(0, linked.length()-1);
						System.out.println("liiiiiinked joiin2:  "+linked);
						return getcolxrow(stringR, C, trimArray(filas, 1, filas.length), linked); //se envia el join en forma recursiva, las filas no se aumentan.
					}
				}
			}
		}
		return null;
	}
	
	public static String[] trimArray(String[] array, int init, int last) {
		String[] trim=  new String[last-init];
		if (array.length==1) {
			return new String[0];
		}
		int contador = 0;
		for (int i=0; i<=last-1; i++) {
			if (i>=init) {
				trim[contador]=array[i];
				contador++;
			}
		}
//		printArray(trim,"trim");
		return trim;
	}

	public static void printArray(String[] array, String nombre) {
		if (array.length==0)return;
		String str= "";
		System.out.print("\n"+nombre+" : ");
		for (int j=0; j<=array.length-1; j++) {
			if (str.isEmpty()) { 
				str = array[j].toString();
				continue;}
			str = str +"   +   "+ array[j].toString();
			}
		System.out.println(str);
		System.out.print("\n");
	}

//	private static ArrayTuple<String[]> getcolxrow(ArrayList<String> columnas,ArrayList<String[]> filas) {
//		ArrayTuple<String[]> arraytuple = new ArrayTuple<String[]>();
//		arraytuple.setAll(columnas, filas);
//		arraytuple.print();
//		return arraytuple;
//		
//	}

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
