package Arboles;

import Arboles.NodoArbol;

public class AVL<t extends Comparable<t>> {
	private NodoArbol<t> root=null;
	
	public boolean isEmpty() {
		return root==null;
	}
	public void insert(t dato, Object refe) {//el objeto que sera la referencia
		this.root=this.insert(dato, this.root,refe);
	}
	private NodoArbol<t> insert (t dato, NodoArbol<t> temp, Object refe ){
		if (temp==null) {
			return new NodoArbol<t>(dato,refe);
		}else if (dato.compareTo(temp.element)<0) {
			temp.left = insert(dato,temp.left,refe);
			// casos para el giro
			if (altura(temp.left)-altura(temp.right)==2) {//si da 2 tengo que rotarlo
				if (dato.compareTo(temp.element)<0) {
					temp=rotacionHIzquierda(temp);
				}else {
					temp=rotacionDobleHIzquirda(temp);
				}
			}
		}else if (dato.compareTo(temp.element)>0) {
			temp.right = insert (dato,temp.right,refe);
			if (altura(temp.left)-altura(temp.right)==2) {//si da 2 tengo que rotarlo
				if (dato.compareTo(temp.element)>0) {
					temp=rotacionHDerecho(temp);
				}else {
					temp=rotacionDobleHDerecho(temp);
				}
			}
		}return temp;
	}
	public int altura(NodoArbol<t> dato) {
		return dato==null? -1 :dato.altura;   //if dato==null retorna -1 
	}                                         //else altura del nodo
	private NodoArbol<t> rotacionHIzquierda(NodoArbol<t> temp) {
		NodoArbol<t> aux= temp.left;
		temp.left=aux.right;        // me base en las lineas de codigo que el profe dio en la clase
		aux.right=temp;
		temp.altura=cambio_altura(altura(temp.left), altura(temp.right))+1; 
		aux.altura= cambio_altura(altura(aux.left),temp.altura)+1; 
		return aux;
	}
	private NodoArbol<t> rotacionHDerecho(NodoArbol<t> temp){
		NodoArbol<t> aux= temp.right;
		temp.right=aux.left;
		aux.left=temp;
		temp.altura=cambio_altura(altura(temp.left), altura(temp.right))+1; //estas 2 varas las busque en internet 
		aux.altura= cambio_altura(altura(aux.right),temp.altura)+1; //  no se como se hacia
		return aux;
	}
	private NodoArbol<t> rotacionDobleHIzquirda (NodoArbol<t> temp){
		temp.left=rotacionHIzquierda(temp.left);//le hago la primera rotacion con su nodo izquierdo
		return rotacionHIzquierda(temp);// le hago la otra rotacion
	}
	private NodoArbol<t>rotacionDobleHDerecho (NodoArbol<t> temp){
		temp.right=rotacionHIzquierda(temp.right);// 3/4 de lo mismo solo que con el derecho
		return rotacionHIzquierda(temp);
	}
	private static int cambio_altura(int izquierda,int derecha) {
		return izquierda>derecha? izquierda:derecha;
	}
	public Object buscarDato(t dato) {
		return buscarDato_aux(dato,root);//El metodo de buscar datos, en teoria deberida de seguir funcionando
	}
	private Object buscarDato_aux(t dato, NodoArbol<t> temp) {
		if (temp.element.equals(dato)) {
			return temp.getRefe();//me retorna la referencia, si no esta el indice de busqueda en el arbol da false
		}else if (dato.compareTo(temp.element)< 0) {
			if (temp.left==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.left);
			}
		}else{
			if (temp.right==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.right);
			}
		}
	}
	public void inOrden() {
		inOrden1(root);
	}
	private void inOrden1(NodoArbol<t> temp) {
		if (temp != null) {
			inOrden1(temp.left);
			System.out.print(temp.element+",");
			inOrden1(temp.right);
		}
	}
}
