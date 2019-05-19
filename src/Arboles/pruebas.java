package Arboles;

import java.util.Hashtable;

public class pruebas {
	public static void main(String[]args) {
		persona aldo1=new persona(1,"aldo");
		persona aldo2=new persona(2,"aldo1");
		persona aldo3=new persona(3,"aldo2");

		AVL<Integer, persona> ejemplo = new AVL<>();
		//         el "1" es la llave
		ejemplo.insert(1,aldo1);
		ejemplo.insert(2,aldo2);
		ejemplo.insert(3,aldo3);
		System.out.println("primer print 3,2,1");
		System.out.print(ejemplo.search(3)+"\n");
		System.out.print(ejemplo.search(2)+"\n");
		System.out.print(ejemplo.search(1)+"\n");
		System.out.println("un segundo print de 3,2,1");
		System.out.print(ejemplo.search(3)+"\n");
		System.out.print(ejemplo.search(2)+"\n");
		System.out.print(ejemplo.search(1)+"\n");
		
		System.out.print("Original:"+aldo1+"\n");
		System.out.print("Original:"+aldo2+"\n");
		System.out.print("Original:"+aldo3+"\n"+"\n");
		
		//persona a = new persona(22,"pepe");
		//ejemplo.buscarDato(1)=a;
		//System.out.print(a.nombre);
		//System.out.print(ejemplo.buscarDato(1)+"+n");
		//System.out.print(ejemplo.buscarDato(2)+"\n");
		//System.out.print(ejemplo.buscarDato(1)+"\n");
		
	}
}
