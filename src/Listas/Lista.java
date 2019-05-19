package Listas;

import java.util.HashSet;
public class Lista<t>{
	public NodoL<t> first;
	public int tamaño;
	
	public NodoL<t> getNodo() {
		return first;
	}
	public Lista() {
		this.first=null;
		tamaño=0;
	}
	public void addlist(t dato, int cont) {
		if (first==null) {
			first=new NodoL<t>(dato,cont);
		}else {
			NodoL<t> temp=first;
			while (temp.getNext()!=null) {
				temp=temp.getNext();
				}
			temp.next=new NodoL<t>(dato,cont);
		}
		tamaño++;
	}
	public t Search(int referencia) {
		int cont=0;
		NodoL<t> temp=first;
		while (cont<tamaño) {
			if (cont==referencia) {
				return temp.getNodo();
			}else {
				cont++;
				temp=temp.next;
			}
		}
		return null;
	}
	public boolean verDupl(ListaTables filas,String key){  
		HashSet<t> s=new HashSet<>(); 
		return Duplicado(filas, s, key);  
	}  
	private boolean Duplicado(ListaTables filas, HashSet<t> s,String key) {
		int parada=filas.getLargo();
		for (int i=0;parada>i;i++) {
			if (s.contains(filas.buscar(i).get(key).toString())){
				return false;
			}s.add((t) filas.buscar(i).get(key).toString());
		}return true;
	}
	
	public class NodoL<T>{
	    private T nodo=null;
	    public int num;
	    public NodoL <T> next=null;
	    
	    public NodoL(T nodo,int num){
	        this.nodo= nodo;
	        this.num=num;
	        this.next= null;
	    }
	    public T getNodo() {
	        return nodo;
	    }
	    public void setNodo(T nodo) {
	        this.nodo = nodo;
	    }
	    public NodoL<T> getNext() {
	        return next;
	    }
	    public void setNext(NodoL<T> next) {
	        this.next = next;
	    }
	}
}
