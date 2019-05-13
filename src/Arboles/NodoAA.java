package Arboles;

public class NodoAA<t extends Comparable<t>,v>{
	NodoAA<t,v> right,left,padre;
	t element;
	v refe;
	public NodoAA(){
         this.element=null;
         this.refe=null;
         right=left=padre=null;
     } 
	public NodoAA(t element,v refe) {
		this.element=element;
		this.refe=refe;
		right=left=padre=null;
	}
}
