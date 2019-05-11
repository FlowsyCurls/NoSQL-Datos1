package Arboles;

public class NodoArbol_refe<t extends Comparable<t>,v>{
	public NodoArbol_refe<t,v> left;
	public NodoArbol_refe<t,v> right;
	public t element;
	public int altura;
	public v refe;
	
	public NodoArbol_refe(t element,v refe){
		this.element=element;
		left=right=null;
		altura=1;
		this.refe=refe;
	}
}
