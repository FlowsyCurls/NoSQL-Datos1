package pruebas;

import java.util.ArrayList;

import Listas.ArrayTuple;
import Listas.Esquema;
import Listas.ListaString;
import Listas.Nodo;

public class MainSplit {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		
		String todos1 = "{dato2=222, dato1=perro};{dato2=222, dato1=gato}";
		String todos2 = "{DATO1=raton, ESQUEMA1=gato}:ESQUEMA1={dato2=222, DATO1=gato};{DATO1=liebre, ESQUEMA1=perro}:ESQUEMA1={dato2=222, DATO1=perro}";
	   	String todos3 = "{Esquema2=raton, DAto1=lobo}:Esquema2={Dato1=raton, Esquema1=gato}:Esquema1={dato2=222, dato1=gato};{Esquema2=liebre, DAto1=zorro}:Esquema2={Dato1=liebre, Esquema1=perro}:Esquema1={dato2=222, dato1=perro}";
	   	
//		System.out.println("datos  :  "+todos3);
//		String[] filas = todos3.split(";");
	   	
		String[] a = new String[5];
		
    	a[0] = "a"; a[1] = "b"; a[2] = "c"; a[3] = "d"; a[4] = "e"; 
    	printArray(a, "fila");
		a = trimArray(a,1,a.length);
		printArray(a, "fila");
	}
	
	public static String[] trimArray(String[] array, int init, int last) {
		String[] trim=  new String[last-init];
		int contador = 0;
		for (int i=0; i<=last-1; i++) {
			System.out.println("i "+i);
			System.out.println("contador "+contador);
			if (i>=init) {
				trim[contador]=array[i];
				System.out.println(trim[contador]);
				System.out.println(trim.length);

				contador++;
			}
		}return trim;
	}
//		System.out.print(trim.length);

	public static void printArray(String[] array, String nombre) {
		String str= "";
		System.out.print("\n"+nombre+" : ");
		for (int j=0; j<=array.length-1; j++) {
			System.out.println(j);
			if (str.isEmpty()) { 
				str = array[j].toString();
				continue;}
			str = str +"   +   "+ array[j].toString();
			}
		System.out.println(str);
		System.out.print("\n");
		
//			str = str +" "+ array[j];
//		}System.out.print("\n");
//		return str;
	}
	
	
	
	
	
	
	public ArrayTuple<String[]> getcolxrow(String cadena){
		System.out.println("\ncadena  :  "+cadena);
		/*sacar las filas de la cadena*/
		String[] filas = cadena.split(";");
		/*prints*/
//		Esquema.printArray(filas,"filas");
		return getcolxrow(cadena, "", new ListaString(), filas, null);
	}
	
	
	private ArrayTuple<String[]> getcolxrow(String cadena, String stringR, ListaString C, String[] filas, String JOIN){
		if (filas.length==0) {
			//con las columnas
			ArrayList<String> A = new ArrayList<>();
			int l=C.getLargo();
			Nodo<String> tmp = C.getHead();
			while (tmp!=null) {
				A.add(tmp.getNodo());
				tmp=tmp.getNext();
			}
			//con las filas
			ArrayList<String[]> R = new ArrayList<>();
			System.out.println(stringR.split(";"));
			R.add(stringR.split(";"));
			/*prints*/
//			System.out.println("tamaño listastring -> "+l);
//			System.out.println("num colm -> "+A.size());
//			System.out.println("num filas -> "+R.size());
			return getcolxrow(A, R); 
		}else {
			String[] verify_joins = filas[0].split(":");
			if (JOIN.equals(null)) {
				if (verify_joins.length ==1) { 
					String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
					for (int j=0; j<vrfNumberROWS.length; j++) {
						/*sacar por pares (dato con su respectiva columna)*/
						Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
						String[] parColumnaxDato = vrfNumberROWS[j].split("=");
						//important variables...
						String nombreColumna = parColumnaxDato[0];
						String datoColumna =  parColumnaxDato[1];
						C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
						stringR = stringR+datoColumna; //columnas en el string FILAS
						System.out.println("\n>> SOLO INFORMATION");
						System.out.println("\tnombre : "+nombreColumna);
						System.out.println("\tdato : "+datoColumna+"\n");
					}
					return getcolxrow(filas[1], stringR, C, trimArray(filas, 1, filas.length), null);
				}else {
					String[] verify_joins = filas[0].split(":");
					/*prints*/
	//				Esquema.printArray(verify_joins,"verify_joins");
					//si existe un join dentro...
					if (verify_joins.length ==2) {
						/*agregar primero lo que est[a antes del join*/
						String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
						for (int j=0; j<vrfNumberROWS.length; j++) {
							/*sacar por pares (dato con su respectiva columna)*/
							Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
							String[] parColumnaxDato = vrfNumberROWS[j].split("=");
							//important variables...
							String nombreColumna = parColumnaxDato[0];
							String datoColumna =  parColumnaxDato[1];
							C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
							stringR = stringR+datoColumna; //columnas en el string FILAS
							/*prints*/
							System.out.println("\n>> SOLO INFORMATION");
							System.out.println("\tnombre : "+nombreColumna);
							System.out.println("\tdato : "+datoColumna+"\n");
						}return getcolxrow(filas[0], stringR, C, fila, verify_joins[1]);
					}
				}
			}else {
				if (verify_joins.length ==1) { 
					String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
					for (int j=0; j<vrfNumberROWS.length; j++) {
						/*sacar por pares (dato con su respectiva columna)*/
						Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
						String[] parColumnaxDato = vrfNumberROWS[j].split("=");
						//important variables...
						String nombreColumna = parColumnaxDato[0];
						String datoColumna =  parColumnaxDato[1];
						C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
						stringR = stringR+datoColumna; //columnas en el string FILAS
						System.out.println("\n>> SOLO INFORMATION");
						System.out.println("\tnombre : "+nombreColumna);
						System.out.println("\tdato : "+datoColumna+"\n");
					}return getcolxrow(filas[1], stringR, C, trimArray(filas, 1, filas.length), null);
				}else {
					String[] verify_joins = filas[0].split(":");
					/*prints*/
//					Esquema.printArray(verify_joins,"verify_joins");
					//si existe un join dentro...
					if (verify_joins.length ==2) {
						/*agregar primero lo que est[a antes del join*/
						String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
						for (int j=0; j<vrfNumberROWS.length; j++) {
							/*sacar por pares (dato con su respectiva columna)*/
							Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
							String[] parColumnaxDato = vrfNumberROWS[j].split("=");
							//important variables...
							String nombreColumna = parColumnaxDato[0];
							String datoColumna =  parColumnaxDato[1];
							C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
							stringR = stringR+datoColumna; //columnas en el string FILAS
							/*prints*/
							System.out.println("\n>> SOLO INFORMATION");
							System.out.println("\tnombre : "+nombreColumna);
							System.out.println("\tdato : "+datoColumna+"\n");
						}return getcolxrow(filas[0], stringR, C, fila, verify_joins[1]);
					}
				}
			}
		}
	}

	
//	
//	
//	public ArrayTuple<String[]> getcolxrow(String cadena){
//		System.out.println("\ncadena  :  "+cadena);
//		/*sacar las filas de la cadena*/
//		String[] filas = cadena.split(";");
//		/*prints*/
////		Esquema.printArray(filas,"filas");
//		return getcolxrow(cadena, "", new ListaString(), filas);
//	}
//	
//	private ArrayTuple<String[]> getcolxrow(String cadena, String stringR, ListaString C, String[] filas){
//		if (filas.length==0) {
//			//con las columnas
//			ArrayList<String> A = new ArrayList<>();
//			int l=C.getLargo();
//			Nodo<String> tmp = C.getHead();
//			while (tmp!=null) {
//				A.add(tmp.getNodo());
//				tmp=tmp.getNext();
//			}
//			//con las filas
//			ArrayList<String[]> R = new ArrayList<>();
//			System.out.println(stringR.split(";"));
//			R.add(stringR.split(";"));
//			/*prints*/
////			System.out.println("tamaño listastring -> "+l);
////			System.out.println("num colm -> "+A.size());
////			System.out.println("num filas -> "+R.size());
//			return getcolxrow(A, R); 
//		}else {
//			String[] verify_joins = filas[0].split(":");
//			/*prints*/
////			Esquema.printArray(verify_joins,"verify_joins");
//			//si existe un join dentro...
//			if (verify_joins.length ==2) {
//				/*agregar primero lo que est[a antes del join*/
//				String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
//				for (int j=0; j<vrfNumberROWS.length; j++) {
//					/*sacar por pares (dato con su respectiva columna)*/
//					Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
//					String[] parColumnaxDato = vrfNumberROWS[j].split("=");
//					//important variables...
//					String nombreColumna = parColumnaxDato[0];
//					String datoColumna =  parColumnaxDato[1];
//					C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
//					stringR = stringR+datoColumna; //columnas en el string FILAS
//					System.out.println("\n>> SOLO INFORMATION");
//					System.out.println("\tnombre : "+nombreColumna);
//					System.out.println("\tdato : "+datoColumna+"\n");
//					/*prints*/
////					System.out.println("ColumnaNombre " + nombreColumna);
////					System.out.println("ColumnaDato "+datoColumna);
//				}
//				/*agregar nombre de columna al array.
//				 * agregar espacio vacio a las filas.
//				Hacer recursion nuevamente con el join*/
//				String[] join = verify_joins[1].split("=", 2);
//				String joinName = join[0];
//				String joinData = join[1];
//				C.addFirst(joinName);
//				/*prints*/
//				System.out.println("\n>> JOIN INFORMATION");
//				System.out.println("\tnombre : "+joinName);
//				System.out.println("\tjoin : "+joinData+"\n");
//				return getcolxrow(joinData,stringR+";", C, trimArray(filas, 1, filas.length));
//			}if (verify_joins.length ==1) {
//				String[] vrfNumberROWS = verify_joins[0].substring(1, filas[0].length()-1).split(",");
//				for (int j=0; j<vrfNumberROWS.length; j++) {
//					/*sacar por pares (dato con su respectiva columna)*/
//					Esquema.printArray(vrfNumberROWS, "vrfNumberROWS >>");
//					String[] parColumnaxDato = vrfNumberROWS[j].split("=");
//					//important variables...
//					String nombreColumna = parColumnaxDato[0];
//					String datoColumna =  parColumnaxDato[1];
//					C.addFirst(nombreColumna); //columnas en el arraylist COLUMNAS
//					stringR = stringR+datoColumna; //columnas en el string FILAS
//					System.out.println("\n>> SOLO INFORMATION");
//					System.out.println("\tnombre : "+nombreColumna);
//					System.out.println("\tdato : "+datoColumna+"\n");
//					/*prints*/
////					System.out.println("ColumnaNombre " + nombreColumna);
////					System.out.println("ColumnaDato "+datoColumna);
//				}
//			}return getcolxrow(filas[1], stringR, C, trimArray(filas, 1, filas.length));
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
