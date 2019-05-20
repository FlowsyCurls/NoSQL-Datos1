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
	private ArbolAA AA;
	private ArbolRB RB;
	private ArbolB B;
	private ArbolBPlus BPlus;
	private ArbolBinario Binario;
	private AVL AVL;
	
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
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setAA(ArbolAA aA) {
		AA = aA;
	}
	public void setRB(ArbolRB rB) {
		RB = rB;
	}
	public void setB(ArbolB b) {
		B = b;
	}
	public void setBPlus(ArbolBPlus bPlus) {
		BPlus = bPlus;
	}
	public void setBinario(ArbolBinario binario) {
		Binario = binario;
	}
	public void setAVL(AVL aVL) {
		AVL = aVL;
	}
	
	
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
}
