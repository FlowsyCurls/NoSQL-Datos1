package Listas;
import java.util.Hashtable;

import Arboles.AVL;
import Arboles.ArbolAA;
import Arboles.ArbolB;
import Arboles.ArbolBPlus;
import Arboles.ArbolBinario;
import Arboles.ArbolRB;
public class Indice<t> {
	private String nombre;
	private ArbolAA dato0;
	private ArbolRB dato1;
	private ArbolB dato2;
	private ArbolBPlus dato3;
	private ArbolBinario dato4;
	private AVL dato5;
	
	public Indice(String nombre,NombreArbol dato) {
		this.nombre=nombre;
		if (dato==NombreArbol.ArbolAA) {
			
			dato0=new ArbolAA();
		}else if(dato==NombreArbol.ArbolRB) {
			dato1=new ArbolRB();
		}else if(dato==NombreArbol.ArbolB) {
			dato2=new ArbolB();
		}else if(dato==NombreArbol.ArbolBPlus) {
			dato3=new ArbolBPlus();
		}else if(dato==NombreArbol.ArbolBinario) {
			dato4=new ArbolBinario();
		}else {
			dato5=new AVL();
		}
	}
	public String getNombre() {
		return nombre;
	}
	public ArbolAA getDato0() {
		return dato0;
	}
	public ArbolRB getDato1() {
		return dato1;
	}
	public ArbolB getDato2() {
		return dato2;
	}
	public ArbolBPlus getDato3() {
		return dato3;
	}
	public ArbolBinario getDato4() {
		return dato4;
	}
	public AVL getDato5() {
		return dato5;
	}
}
