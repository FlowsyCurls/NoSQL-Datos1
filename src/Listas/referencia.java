package Listas;
import java.util.Hashtable;

import Arboles.AVL;
import Arboles.ArbolAA;
import Arboles.ArbolB;
import Arboles.ArbolBPlus;
import Arboles.ArbolBinario;
import Arboles.ArbolRB;

public class referencia {
	public ArbolAA<String,Hashtable> setArbolAA(ArbolAA<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
	public ArbolB<String,Hashtable> setArbolB(ArbolB<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
	public ArbolBinario<String,Hashtable> setArbolBinario(ArbolBinario<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
	public ArbolBPlus<String,Hashtable> setArbolBPlus(ArbolBPlus<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
	public ArbolRB<String,Hashtable> setArbolRB(ArbolRB<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
	public AVL<String,Hashtable> setArbolAVL(AVL<String,Hashtable> datos,ListaTables filas,String Key) {
		int parada=filas.largo;
		for (int i=0;parada>i;i++){
			datos.insert(filas.buscar(i).get(Key).toString(), filas.buscar(i));
		}return datos;
	}
}
