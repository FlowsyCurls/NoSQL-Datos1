package Arboles;

public class AVL_referencia<t extends Comparable<t>,v> {
	public NodoArbol_refe<t,v> root=null;
	
	public boolean isEmpty() {
		return root==null;
	}
	public void insert(t dato, v refe) {//el "v" que sera la referencia
		this.root=this.insert(dato,refe,root);
	}
	private NodoArbol_refe<t,v> insert (t dato,v refe ,NodoArbol_refe<t,v> temp){
		if (temp==null) {
			return new NodoArbol_refe<t,v>(dato,refe);
		}else if (dato.compareTo(temp.element)<0) {
			temp.left = insert(dato,refe,temp.left);
		}else  if(dato.compareTo(temp.element)>0) {
			temp.right = insert (dato,refe,temp.right);
		}
		temp.altura= Math.max(altura(temp.left), altura(temp.right))+1;
		int balanceo = getBalanceo(temp);
		
		if (balanceo>1 && dato.compareTo(temp.left.element)<0) {//Left Left
			return rotacionHDerecho(temp);	
		}if (balanceo<-1 && dato.compareTo(temp.right.element)>0) {//Right Right
			return  rotacionHIzquierda(temp);
		}if (balanceo > 1 && dato.compareTo(temp.left.element)>0) {//Left Right
			return rotacionDobleHDerecho (temp);
		} if (balanceo < -1 && dato.compareTo(temp.right.element)<0) {//Left Right
			return rotacionDobleHIzquirda(temp);
		}return temp;
	}
	public int getBalanceo(NodoArbol_refe<t,v> temp) {
		return temp==null? 0:altura(temp.left)- altura(temp.right);
	}
	public int altura(NodoArbol_refe<t,v> dato) {
		return dato==null? 0:dato.altura;   //if dato==null retorna 0
	}                                         //else altura del nodo
	private NodoArbol_refe<t,v> rotacionHIzquierda(NodoArbol_refe<t,v> temp) {
		NodoArbol_refe<t,v> aux= temp.right;
		NodoArbol_refe<t,v> aux2= aux.left;
		aux.left=temp;        // me base en las lineas de codigo que el profe dio en la clase
		temp.right=aux2;	
		temp.altura= Math.max(altura(temp.left), altura(temp.right))+1; 
		aux.altura = Math.max(altura(aux.left),altura(aux.right))+1; 
		return aux;
	}
	private NodoArbol_refe<t,v> rotacionHDerecho(NodoArbol_refe<t,v> temp){
		NodoArbol_refe<t,v> aux= temp.left;
		NodoArbol_refe<t,v> aux2= aux.right;
		aux.right=temp;
		temp.left=aux2;
		temp.altura=Math.max(altura(temp.left), altura(temp.right))+1; //estas 2 varas las busque en internet 
		aux.altura= Math.max(altura(aux.right), altura(aux.right))+1; //  no se como se hacia
		return aux;
	}
	private NodoArbol_refe<t,v> rotacionDobleHIzquirda (NodoArbol_refe<t,v> temp){
		temp.right=rotacionHDerecho(temp.right);//le hago la primera rotacion con su nodo izquierdo
		return rotacionHIzquierda(temp);// le hago la otra rotacion
	}
	private NodoArbol_refe<t,v> rotacionDobleHDerecho (NodoArbol_refe<t,v> temp){
		temp.left=rotacionHIzquierda(temp.left);// 3/4 de lo mismo solo que con el derecho
		return rotacionHDerecho(temp);
	}
	public v search(t dato) {
		return buscarDato_aux(dato,root);//El metodo de buscar datos, en teoria deberida de seguir funcionando
	}
	private v buscarDato_aux(t dato, NodoArbol_refe<t,v> temp) {
		if (temp.element.equals(dato)) {
			return temp.refe;
		}else if (dato.compareTo(temp.element)< 0) {
			return temp.left==null? null: buscarDato_aux(dato,temp.left);
		}else{
			return temp.right==null? null: buscarDato_aux(dato,temp.right);
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
}
