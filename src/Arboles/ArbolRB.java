package Arboles;
public class ArbolRB<t extends Comparable<t>,v>{
	private NodoRB<t,v> base= new NodoRB<t,v>();
	private NodoRB<t,v> root = base;

    public void RedBlackTree() {
        root.left = base;
        root.right = base;
        root.parent = base;
    }
	private void leftRotate(NodoRB<t,v> temp){
		leftRotateFixup(temp);//ajusta los valores del numleft y numright del nodo
		NodoRB<t,v> aux;//comienza el giro a la izquirda
		aux= temp.right;
		temp.right = aux.left;
		// verificar si left existe
		if (!isNil(aux.left)) {
			aux.left.parent = temp;}
		aux.parent = temp.parent;
		// ver si su padre es nulo
		if (isNil(temp.parent)) {
			root = aux;}
		//ver si el left de temp es su padre
		else if (temp.parent.left == temp) {
			temp.parent.left =aux;}
		//ver si right es su padre
		else {
			temp.parent.right = aux;}
		// giro izquieda terminado
		aux.left = temp;
		temp.parent =aux;
	}
	private void leftRotateFixup(NodoRB<t,v> temp){//areglo del nodo left, areglando sus numleft and numright afecatos por el giro
		if (isNil(temp.left) && isNil(temp.right.left)){
			temp.numLeft = 0;
			temp.numRight = 0;//caso1 solo temp 
			temp.right.numLeft = 1;
		}
		else if (isNil(temp.left) && !isNil(temp.right.left)){// caso2 para su temp left y left.right, adicion caso 1
			temp.numLeft = 0;
			temp.numRight = 1 + temp.right.left.numLeft + temp.right.left.numRight;
			temp.right.numLeft = 2 + temp.right.left.numLeft + temp.right.left.numRight;
		}
		// Case 3: x.left also exists in addition to Case 1
		else if (!isNil(temp.left) && isNil(temp.right.left)){//caso3 para su temp left y right.left, adicion caso1
			temp.numRight = 0;
			temp.right.numLeft = 2 + temp.left.numLeft + temp.left.numRight;
		}
		else{
			temp.numRight = 1 + temp.right.left.numLeft +temp.right.left.numRight;
			temp.right.numLeft = 3 + temp.left.numLeft + temp.left.numRight +//caso4 para temp left y right.left, adicion caso1
			temp.right.left.numLeft + temp.right.left.numRight;
		}
	}
	private void rightRotate(NodoRB<t,v> temp){//ajusta los valores del numleft y numright del nodo
		rightRotateFixup(temp);
		NodoRB<t,v> aux = temp.left;
		temp.left = aux.right;//comienza el giro right
        if (!isNil(aux.right)) {// verificar si right existe
        	aux.right.parent = temp;}
        aux.parent = temp.parent;
        if (isNil(temp.parent))// verifica si su padre existe
            root =aux;
        else if (temp.parent.right == temp) {//temp es un nodo.right desu padre
        	temp.parent.right = aux;}
        else {// temp es un nodo.left de su padre
        	temp.parent.left = aux;}
        aux.right = temp;
        temp.parent = aux;
	}
	private void rightRotateFixup(NodoRB<t,v> temp){//areglo del nodo right, areglando sus numleft and numright afecatos por el giro
		if (isNil(temp.right) && isNil(temp.left.right)){
			temp.numRight = 0;//caso1 solo para temp y su left and left.left existen
			temp.numLeft = 0;
			temp.left.numRight = 1;
		}
		else if (isNil(temp.right) && !isNil(temp.left.right)){
			temp.numRight = 0;//caso2 temp.left.right y adicion caso1 
			temp.numLeft = 1 + temp.left.right.numRight + temp.left.right.numLeft;
			temp.left.numRight = 2 + temp.left.right.numRight + temp.left.right.numLeft;
		}
		// Case 3: y.right also exists in addition to Case 1
		else if (!isNil(temp.right) && isNil(temp.left.right)){
			temp.numLeft = 0;//caso3 para temp.right y adicion caso1
			temp.left.numRight = 2 + temp.right.numRight +temp.right.numLeft;
		}
		else{//caso5 temp.right and temp.left.right y adicion caso1
			temp.numLeft = 1 + temp.left.right.numRight +temp.left.right.numLeft;
			temp.left.numRight = 3 +temp.right.numRight +temp.right.numLeft +temp.left.right.numRight + temp.left.right.numLeft;
		}

	}
    public void insert(t dato,v refe) {
        insert(new NodoRB<t,v>(dato, refe), refe);
    }
	private void insert(NodoRB<t,v> temp,v refe) {
			//tener una referencia  y un molde
		    NodoRB<t,v> aux =base;
		    NodoRB<t,v> current= root;
			// va hasta que no sea null
			while (!isNil(current)){
				aux= current;
				if (temp.element.compareTo(current.element) < 0){//left
					// actualizando el current.numleft
					current.numLeft++;
					current = current.left;
				}
				else{//right
					//actualizando el current.numright
					current.numRight++;
					current = current.right;
				}
			}
			//mantener los padres de temp
			temp.parent = aux;
			//dependiendo del valor de aux.element, temp se ira left or right
			if (isNil(aux)) {
				root = temp;}
			else if (temp.element.compareTo(aux.element) < 0) {
				aux.left = temp;}
			else {
				aux.right = temp;}
			//pone los datos del nodo
			temp.left = base;
			temp.right = base;
			temp.color = NodoRB.RED;
			temp.refe=refe;
			//llama a ver si se tiene que modificar algo del arbol
			insertFixup(temp);
	}
	// verfica si despues de meter un valor, no se rompio ningula propiedad del arbolRB
	private void insertFixup(NodoRB<t,v> temp){
		NodoRB<t,v> aux=base;
		while (temp.parent.color == NodoRB.RED){
			if (temp.parent == temp.parent.parent.left){
				aux= temp.parent.parent.right;
				if (aux.color == NodoRB.RED){// Case 1 si es rojo
					temp.parent.color = NodoRB.BLACK;
					aux.color = NodoRB.BLACK;
					temp.parent.parent.color = NodoRB.RED;
					temp = temp.parent.parent;
				}
				else if (temp == temp.parent.right){//caso 2 si es negro y temp.right  tiene hijo
					temp = temp.parent;
					leftRotate(temp);//girar entorno a su padre
				}
				else{//caso3 es negro y temp.left tiene hijo
					temp.parent.color = NodoRB.BLACK;//recolor y giro entorno a su abuelo
					temp.parent.parent.color = NodoRB.RED;
					rightRotate(temp.parent.parent);
				}
			}
			else {// si temp padre, es el right de su padres...
				aux= temp.parent.parent.left;
				// Case1 si es rojo cambio de color
				if (aux.color == NodoRB.RED){
					temp.parent.color = NodoRB.BLACK;
					aux.color = NodoRB.BLACK;
					temp.parent.parent.color = NodoRB.RED;
					temp = temp.parent.parent;
				}
				else if (temp == temp.parent.left){//caso 2 si es negro y temp.left tiene hijo
					temp = temp.parent;//gira right entorno a su padre
					rightRotate(temp);
				}
				else{//caso3 si es negro y su temp.right tiene hijo
					temp.parent.color = NodoRB.BLACK;//recolor y gira entorno a su abuelo
					temp.parent.parent.color = NodoRB.RED;
					leftRotate(temp.parent.parent);
				}
			}
		}
	root.color = NodoRB.BLACK;
	}
	private boolean isNil(NodoRB<t,v> node){
		// regrese el apropiado valor
		return node==base;
	}
	public v search(t dato) {
		return buscarDato_aux(dato,root);
	}
	private v buscarDato_aux(t dato,NodoRB<t,v> temp) {
		if (temp.element.equals(dato)) {
			return temp.refe;
		}else if (dato.compareTo(temp.element)< 0) {
			return temp.left==null?null: buscarDato_aux(dato,temp.left);
		}else{
			return temp.right==null?null: buscarDato_aux(dato,temp.right);
		}
	}
}
