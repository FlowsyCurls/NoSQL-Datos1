package Listas;

import java.util.ArrayList;

import Errores.DatosUsadosException;

public class ListaEsquemas {
    int largo;
    Nodo<Esquema> head= null;
    private ArrayList<Esquema> buscados = new ArrayList<Esquema>();

    public void addLast (Esquema e){
        if (this.head==null){
            this.head= new Nodo<Esquema>(e);
            largo++;
        }
        else {
            Nodo <Esquema> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }
    public void addFirst(Esquema e) {
        Nodo<Esquema> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }

    public Esquema buscar (int n){
        Nodo<Esquema>tmp=this.head;
        while (n>0){
        	if (tmp.next == null)return null;
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public Esquema buscar(String nombreesquema){
            Esquema esquema=null;
            Nodo<Esquema>tmp=this.head;
            int n=0;
            while (n<this.largo){
                if (tmp.getNodo().getNombre().equals(nombreesquema)){
                    esquema=tmp.getNodo();
                    break;
                }
                tmp=tmp.next;
                n++;
            }
            return esquema;
    }
    
    public ArrayList<Esquema> buscarcoincidencias(String detalle){
        Esquema esquema=null;
        Nodo<Esquema>tmp=this.head;
        int n=0;
        while (n<this.largo){
            if (tmp.getNodo().getNombre().contains(detalle)){
                esquema=tmp.getNodo();
                this.buscados.add(esquema);
            }
            tmp=tmp.next;
            n++;
        }
        return this.buscados;
    }
    
    public void eliminar(String nombre) throws DatosUsadosException {
        Esquema esquema=this.buscar(nombre);
        System.out.println(esquema);
        if (esquema.existejoin()){
            throw new DatosUsadosException();
        }
        else {
            if (this.head.getNodo()==esquema){
                this.head=this.head.next;
                this.largo--;
            }
            else {
                Nodo<Esquema>tmp=this.head;
                while (tmp.next!=null){
                    if (tmp.next.getNodo()==esquema){
                        tmp.next=tmp.next.next;
                        this.largo--;
                        break;
                    }
                    else {
                        tmp=tmp.next;
                    }
                }
            }
        }
    }
    public Boolean contiene(String string){
        Boolean contiene=false;
        Nodo<Esquema>tmp=this.head;
        int n=0;
        while (n<this.largo){
            if (tmp.getNodo().getNombre().equals(string)){
                contiene=true;
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return contiene;
    }

    public ListaString obtenerconstructores(){
        ListaString constructores=new ListaString();
        int cont=this.largo-1;
        while (cont>=0){
            String constructor=this.buscar(cont).crearconstructor();
            constructores.addFirst(constructor);
            cont--; 
        }
        return constructores;
    }
    public void cambiarnombreEsquema(String nombre, String nuevonombre){
        this.buscar(nombre).setNombre(nuevonombre);
        int cont=0;
        while (cont<this.largo){
            Esquema esquema=this.buscar(cont);
            if (esquema.getMijoins().contiene(nombre)){
                esquema.getMijoins().eliminar(nombre);
                esquema.getMijoins().addFirst(nuevonombre);
                if (esquema.getFilas().getLargo()!=0){
                    esquema.cambiarnombrecolumna(nombre,nuevonombre);
                }
            }
            if (esquema.getJoinde().contiene(nombre)){
                esquema.getJoinde().eliminar(nombre);
                esquema.getJoinde().addFirst(nuevonombre);
            }
            cont++;
        }
    }

    public ListaString crearlistaconstructores(){
        ListaString lista= new ListaString();
        int cont=this.largo-1;
        while (cont>=0){
            lista.addFirst(this.buscar(cont).crearconstructor());
            cont--; 
        }
        return lista;
    }

    public ListaString crearlistadatos(){
        ListaString lista= new ListaString();
        int cont=this.largo-1;
        while (cont>=0){
            lista.concatenarlistas(this.buscar(cont).crearconstructoresdatos());
            lista.addFirst(this.buscar(cont).getNombre());
            cont--; 
        }
        return lista;
    }
    
    @SuppressWarnings("unused")
	private ArrayList<String> crearlistadatosArray(ListaString lista) {
    	ArrayList<String> array =  new ArrayList<>();
        Nodo<String> str = lista.getHead();
    	while (str!=null) {
    		array.add(str.getNodo());
    		str = str.getNext();
    		continue;
    	}
		return array;
    }


    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <Esquema> getHead() {
        return head;
    }

    public void setHead (Nodo<Esquema> head) {
        this.head = head;
    }
    
    public void emptyList() {
    	this.head = null; this.largo = 0;
    }
	public ArrayList<Esquema> getArrayesquemas(ListaEsquemas le) {
    	ArrayList<Esquema> array =  new ArrayList<>();
    	Nodo<Esquema> e = le.getHead();
    	while (e!=null) {
    		array.add(e.getNodo());
    		e = e.getNext();
    		continue;
    	}
		return array;
    }
}
