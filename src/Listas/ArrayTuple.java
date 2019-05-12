package Listas;

import java.util.ArrayList;

public class ArrayTuple <T>{
	


	private  ArrayList<String> columnas = new ArrayList<>();
	private  ArrayList<String> filas = new ArrayList<>();
	
	public ArrayTuple() {}
	
	public void setAll( ArrayList<String> columnas,  ArrayList<String> r) {
		this.columnas = columnas;
		this.filas = r;
	}
	
	public ArrayList<String> getColumnas() {
		return columnas;
	}

	public ArrayList<String> getFilas() {
		return filas;
	}

	@SuppressWarnings("unchecked")
	public void print() {
		ArrayList<String> filas= this.filas;
		for (int i =0; i<=columnas.size()-1;i++) {
			System.out.println("\n\nColumna-"+i+" "+columnas.get(i));
			for (int k =0; k<=filas.size()-1; k++) {
				System.out.print("Fila-"+i+"  ");
				for (int j =0; j<=filas.get(0).length()-1;j++) {
					System.out.print(filas.get(k));
				}
			}
		}
	}

	public void addAll(String nombre, T dato) {
		this.columnas.add(nombre);
		this.filas.add((String) dato);
	}
	
}
		
