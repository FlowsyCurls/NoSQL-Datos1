package Listas;

import java.util.ArrayList;

public class ArrayTuple {
	


	private ArrayList<String> columnas = new ArrayList<>();
	private ArrayList<String> filas = new ArrayList<>();
	
	public ArrayTuple() {}
	
	public void addAll(String columnas, String filas) {
		this.columnas.add(columnas);
		this.filas.add(filas);
	}
	
	public ArrayList<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<String> columnas) {
		this.columnas = columnas;
	}

	public ArrayList<String> getFilas() {
		return filas;
	}

	public void setFilas(ArrayList<String> filas) {
		this.filas = filas;
	}
	
}
