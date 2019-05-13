package Arboles;

public class ArbolBinario<t extends Comparable<t>,v>{
	private NodoArbol_refe<t,v> root=null;
	public int tamano=0;
	
	public boolean isEmpty() {
		return root==null;
	}
	public void insert(t dato, v refe) {
		this.root=this.insert(dato,refe,this.root);
	}
	
	private NodoArbol_refe<t,v> insert(t dato,v refe, NodoArbol_refe<t,v> current){
		if (current==null) {
			tamano++;
			return new NodoArbol_refe<t,v>(dato,refe);
		}if (dato.compareTo(current.element)< 0) {
			current.left =insert(dato,refe,current.left);
		}else if (dato.compareTo(current.element)> 0){
			current.right =insert(dato,refe,current.right);
		}return current;
	}
	public void preOrden() {
		preOrden(root);
	}
	private void preOrden(NodoArbol_refe<t,v> temp){
		if (temp != null) {
			System.out.print(temp.element+"\n");
			preOrden(temp.left);
			preOrden(temp.right);
		}
	}
	public void inOrden() {
		inOrden1(root);
	}
	private void inOrden1(NodoArbol_refe<t,v> temp) {
		if (temp != null) {
			inOrden1(temp.left);
			System.out.print(temp.element+",");
			inOrden1(temp.right);
		}
	}
	public void postOrden() {
		postOrden(root);
	}
	private void postOrden(NodoArbol_refe<t,v> temp) {
		if (temp != null) {
			postOrden(temp.left);
			postOrden(temp.right);
			System.out.print(temp.element+"\n");
		}
	}
	public v search(t dato) {
		return buscarDato_aux(dato,root);
	}
	private v buscarDato_aux(t dato, NodoArbol_refe<t,v> temp) {
		if (temp.element.equals(dato)) {
			return temp.refe;
		}else if (dato.compareTo(temp.element)< 0) {
			return temp.left==null? null:buscarDato_aux(dato,temp.left);
		}else{
			return temp.right==null? null:buscarDato_aux(dato,temp.right); 
		}
	}
}
