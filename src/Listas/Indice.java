package Listas;

import Arboles.AVL;
import Arboles.ArbolAA;
import Arboles.ArbolB;
import Arboles.ArbolBPlus;
import Arboles.ArbolBinario;
import Arboles.ArbolRB;
public class Indice {
	private String nombre;
	private ArbolAA AA=null;
	private ArbolRB RB=null;
	private ArbolB B=null;
	private ArbolBPlus BPlus=null;
	private ArbolBinario Binario=null;
	private AVL AVL=null;
	
	public Indice(String nombre,NombreArbol dato) {
		this.nombre=nombre;
		if (dato==NombreArbol.ArbolAA) {
			AA=new ArbolAA();
		}else if(dato==NombreArbol.ArbolRB) {
			RB=new ArbolRB();
		}else if(dato==NombreArbol.ArbolB) {
			B=new ArbolB();
		}else if(dato==NombreArbol.ArbolBPlus) {
			BPlus=new ArbolBPlus();
		}else if(dato==NombreArbol.ArbolBinario) {
			Binario=new ArbolBinario();
		}else {
			AVL=new AVL();
		}
	}
	public boolean estoyvacio() {
		if (((AA == null) && (RB == null) && (B == null) && (BPlus == null) && (Binario == null) && (AVL == null))) {
			return true;
		}
		else {return false;}
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;}



	public ArbolAA generateAA() {
		return AA =new ArbolAA();
	}
	public ArbolRB generateRB() {
		return RB = new ArbolRB();
	}
	public ArbolB generateB() {
		return B =new ArbolB();
	}
	public ArbolBPlus generateBPlus() {
		return BPlus = new ArbolBPlus();
	}
	public ArbolBinario generateBinario() {
		return Binario = new ArbolBinario();
	}
	public Arboles.AVL generateAVL() {
		return AVL = new AVL();
	}



	public String getNombre() {
		return nombre;
	}
	public ArbolAA getAA() {
		return AA;
	}
	public ArbolRB getRB() {
		return RB;
	}
	public ArbolB getB() {
		return B;
	}
	public ArbolBPlus getBPlus() {
		return BPlus;
	}
	public ArbolBinario getBinario() {
		return Binario;
	}
	public AVL getAVL() {
		return AVL;
	}

	public void setAA(ArbolAA AA) {
		this.AA = AA;
	}

	public void setRB(ArbolRB RB) {
		this.RB = RB;
	}

	public void setB(ArbolB b) {
		B = b;
	}

	public void setBPlus(ArbolBPlus BPlus) {
		this.BPlus = BPlus;
	}

	public void setBinario(ArbolBinario binario) {
		Binario = binario;
	}

	public void setAVL(Arboles.AVL AVL) {
		this.AVL = AVL;
	}
}
