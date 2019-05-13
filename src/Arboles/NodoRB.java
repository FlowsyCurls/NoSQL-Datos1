package Arboles;

public class NodoRB<t extends Comparable<t>,v> {
    public static final int BLACK = 0;
    public static final int RED = 1;
	public t element;
	public v refe;
	public NodoRB<t,v> parent,left,right;
    public int numLeft = 0;//num de elementos del nodo left
    public int numRight = 0;//num de elementos del nodo right
    public int color;
    
    public NodoRB(){
        color = BLACK;
        numLeft= numRight = 0;
        parent=left=right = null;
    }//constructor
    public NodoRB(t key, v refe){
    	parent=left=right = null;
        this.element = key;
        this.refe=refe;
	}     
}
