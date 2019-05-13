package Arboles;

public class ArbolAA<t extends Comparable<t>,v>{
	private NodoAA<t,v> root;
	private int cont=0;
	
	public ArbolAA() {
		root=null;
	}
	public void insert(t dato,v refe){
		NodoAA<t,v> temp = root;
		NodoAA<t,v> copy = null;
        while (temp!= null){
            copy= temp;
            if (dato.compareTo(copy.element)>0)
                temp = temp.right;
            else
                temp=temp.left;//cuando termina de meterlo
        }
        temp= new NodoAA<t,v> ();//le da los valores al nodo
        temp.refe=refe;
        temp.element = dato;
        temp.padre = copy;
        if (copy == null)
            root = temp;
        else if (dato.compareTo(copy.element)>0)
            copy.right = temp;
        else
            copy.left = temp;
        Splay(temp);// se va a hacer el ajuste de los giros
        cont++;
    }
	public void leftPadre(NodoAA<t,v> aux, NodoAA<t,v> temp){
        if ((aux== null) || (temp== null) || (temp.left != aux) || (aux.padre !=temp))
            throw new RuntimeException("caso invalido");
        if (temp.padre != null){
            if (temp == temp.padre.left) {
            	temp.padre.left =aux;}
            else {
            	temp.padre.right =aux;}
        }if (aux.right != null)
        	 aux.right.padre = temp;
        aux.padre = temp.padre;
        temp.padre = aux;
        temp.left = aux.right;//cambia le nodo izquierdo enviado con su padre 
        aux.right = temp;
    }
	public void rightPadre(NodoAA<t,v> aux, NodoAA<t,v> temp){
        if ((aux == null) || (temp== null) || (temp.right != aux) || (aux.padre !=temp))
            throw new RuntimeException("caso invalido");
        if (temp.padre != null){
            if(temp == temp.padre.left)
            	temp.padre.left = aux;
            else
            	temp.padre.right = aux;
        }if (aux.left != null)
        	aux.left.padre = temp;
        aux.padre = temp.padre;
        temp.padre = aux;
        temp.right = aux.left;//cambia le nodo derecho enviado con su padre 
        aux.left =temp;
    }
	private void Splay(NodoAA<t,v> temp){
        while (temp.padre != null){
        	NodoAA<t,v> Parent = temp.padre;
        	NodoAA<t,v> GrandParent = Parent.padre;
            if (GrandParent == null){
                if (temp== Parent.left)
                    leftPadre (temp, Parent);
                else
                	rightPadre(temp, Parent);                 
            }else{
                if (temp== Parent.left){
                    if (Parent == GrandParent.left){
                    	leftPadre(Parent, GrandParent);
                    	leftPadre(temp, Parent);
                    }else{
                    	leftPadre(temp, temp.padre);
                        rightPadre(temp, temp.padre);
                    }
                }else{
                    if (Parent == GrandParent.left){
                    	rightPadre(temp, temp.padre);
                        leftPadre(temp, temp.padre);
                    }else {
                    	rightPadre(Parent, GrandParent);
                    	rightPadre(temp, Parent);
                    }
                }
            }
        }
        root = temp;
    }
	 public v search(t dato){
         return findNode(dato);
     }
 
     private v findNode(t dato){
         NodoAA<t,v> temp = root;
         while (temp != null){
             if (dato.compareTo(temp.element)>0)//es igual que un arbolbinario, para su busqueda
                 temp= temp.right;
             else if (dato.compareTo(temp.element)<0)
                 temp= temp.left;
             else {
                 return temp.refe;//me retorna le refe
             }
         }return null;//si no esta nada
     }
}
