package Arboles;

public class NodoArbol<t extends Comparable<t>>{
	public NodoArbol<t> left;
	public NodoArbol<t> right;
	public t element;
	public int altura;
	public Object refe;
	
	public NodoArbol(t element, Object refe){
		this.element=element;
		left=right=null;
		altura=0;
		this.refe=refe;
	}
	
	public Object getRefe() {
		return refe;
	}
	public void setRefe(Object refe) {
		this.refe = refe;
	}

	public boolean AP(t dato) {
		return dato.equals(element)? true:false;
	}
}
