package Listas;

public class SimpleList_Comparable <T extends Comparable<T>> {
    int largo=0;
    Nodo<T> head= null;

    public SimpleList_Comparable() {
        largo=0;
        head=null;
    }


    public void addLast (T e){
        if (this.head==null){
            this.head= new Nodo<T>(e);
            largo++;
        }
        else {
            Nodo <T> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }
    
	public void removeLast() {
		if (this.head==null) {return;}
		else if (this.head.next == null){ this.head=null; largo--; return;}
		Nodo <T> tmp= this.head;
        while (tmp.next.next!= null) {
            tmp = tmp.next;
        }tmp.next = null; largo--;
	}
	
    public void addFirst(T e) {
        Nodo<T> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }
	public boolean contains(T string) {
    	Nodo<T> tmp = this.head;
    	while (tmp != null) {
    		if (tmp.getNodo().equals(string)) {
    			return true;
    		} tmp = tmp.next;
    	}
		return false;
	}
    
    public T get(int index){
    	Nodo<T> tmp = this.head;
//		System.out.println("Largo "+largo);
    	int contador = 0;
    	while (tmp != null) {
    		if (contador==index) {
//        		System.out.println("index "+index);
    			return tmp.getNodo();
    		}
    		tmp=tmp.next;
    		contador++;
    	}
//    	System.out.println("NO EXISTE INDICE");
    	return null;
    }
    
    
    public T set(int index, T value){
    	Nodo<T> tmp = this.head;
//		System.out.println("Largo "+largo);
    	int contador = 0;
    	while (tmp != null) {
    		if (contador==index) {
//        		System.out.println("index "+index);
    			tmp.setNodo(value);
    		}
    		tmp=tmp.next;
    		contador++;
    	}
//    	System.out.println("NO EXISTE INDICE");
    	return null;
    }
    
    public Nodo<T> getNode(int index){
    	Nodo<T> tmp = this.head;
//		System.out.println("Largo "+largo);
    	int contador = 0;
    	while (tmp != null) {
    		if (contador==index) {
//        		System.out.println("index "+index);
    			return tmp;
    		}
    		tmp=tmp.next;
    		contador++;
    	}
//    		System.out.println("NO EXISTE INDICE");
    	return null;
    }
    
	public T getMax() {
        T max = get(0); 
        for (int i = 1; i < largo; i++) {
            if (get(i).compareTo(max) > 0) {
                max = get(i);
            }
        }
		return max; 
    }
    
    public void swap(int current, int prox) {
        T temp = get(current);
        set(current, get(prox));
        set(prox, temp);
    }
    
    
    public void bubbleSort() {
        for (int j = 0; j < largo-1; j++) {
//        	print();
            for (int i = 0; i < largo-j-1; i++) {
            	if (get(i).compareTo(get(i+1)) > 0) {
            		swap(i, i+1);
                }
            }
        }
    }
    
    public void selectionSort() {
        for (int i = 0; i < largo-1; i++) {
//        	print();
        	int min = i;
            for (int z = i+1; z < largo; z++) {
                if (get(z).compareTo(get(min)) < 0){
                    min = z;
                }
            }swap(min,i);   
        }
//        System.out.print("Sorted ");print();
    }
    
    /*Function to sort array using insertion sort*/
    public void insertionSort() {
        for (int i = 1; i < largo; ++i) { 
            T key = get(i); 
            int j = i-1;
//            System.out.println("\n\n\n-----------------------------");
//        	System.out.println("Parto en -->   i|"+i+" - dato|"+get(i));print();
            while (j >= 0 && key.compareTo(get(j)) < 0) {
//            	System.out.println("\t"+get(j)+" mayor que "+key);
                set(j+1, get(j)); 
                j = j-1;
            }
//            print();
//            System.out.println("\rSorted, ahora insertar -> "+key);
            set(j+1,key);
//            print();
        }
    } 
    
    public void quickSort() {
    	quickSort(0, largo-1);
    }private void quickSort(int start, int end) {
    	if (start >= end)
			return;
    	
		int middle = start+(end-start)/2;
		T pivot = get(middle);
		
		int i = start, j = end;
		while (i <= j) {
			//casos que no se hace nada, solo subir el contador de inicio.
			while (get(i).compareTo(pivot) < 0) {
//				print();
				i++;
			}
			//casos que no se hace nada, solo bajar el contador de final.
			while (get(j).compareTo(pivot) > 0) {
//				print();
				j--;
			}
			//caso de swap.
			if (i <= j) {
				swap(i,j);
				i++;
				j--;
			}
		}
		// recursively sort two sub parts
		if (start < j)
			quickSort(start, j);
		if (end > i)
			quickSort(i, end);	
    }
    public void change(int index,T value){
        Nodo<T>tmp=this.head;
        while (index>0){
            tmp=tmp.next;
            index--;
        }
        tmp.setNodo(value);
    }
    public static int getMax(SimpleList_Comparable datos) {
        int max =(int) datos.get(0); 
        int parada=datos.getLargo();
        for (int i = 1; i <parada ; i++) {
            if (datos.get(i).compareTo(max) > 0) {
                max = (int) datos.get(i);
            }
        }
		return max; 
    }
    public static void hacernull(SimpleList_Comparable<Integer> lista, int n) {
    	for (int i=0;n>i;i++)//agarra una lista y le hace "n" cantidad de nodos null
    		lista.addLast(null);	
    } 
    public static void radixsort(SimpleList_Comparable<Integer> lista){
    	int largo=lista.getLargo(); 
        int m = getMax(lista);//aqui sacamos el numero mayor de la lista, para saber cuantas pasadas hay que hacer
        for (int exp = 1; m/exp > 0; exp *= 10){//donde exp sera nuestro divisor (nos dicta cuantas pasadas hay que hacer)
        	//lista.print();
            countSort(lista, largo, exp);//y tambien para dividirlo y obtener los diferentes numeros que ocupemos
        }    
    }  
    private static void countSort(SimpleList_Comparable<Integer> lista, int largo, int exp){ 
    	SimpleList_Comparable<Integer> aux=new SimpleList_Comparable(); // necesitamos una lista igual de larga que la original, para poner los datos temporalmente
    	hacernull(aux, largo);//aqui hacemos la lista de ayuda igual de larga que la original
        int count[] = new int[10];//aqui es el array donde se pondran la cantidad de 0,1,2,3.... que aparesacan en cada pasada
        for (int i = 0; i < largo; i++) { 
            count[ (lista.get(i)/exp)%10 ]++;//donde si su numero es 1, se mete en el array en la posicion 1, y se aumenta 
        }for (int i = 1; i < 10; i++){
            count[i] += count[i - 1];//donde aqui se le suma lo que tenia y de su anterior (la posicion 0 no cuenta por eso inicia en 1) 
        }for (int i = largo - 1; i >= 0; i--){
            aux.set(count[ (lista.get(i)/exp)%10 ]- 1,lista.get(i));//aqui se complica, ya que en aux ponemos en la cantidad que tiene cont del proceso anterior
            count[(lista.get(i)/exp)%10]--; //el valor "i" de la lista original, donde en esta posicion se le resta 1, "con el ejemplo del profe se entiende mejor"
        }for (int i = 0; i < largo; i++) {
        	lista.set(i,aux.get(i));//y ya se mete el la posicion de la lista original el valor de la aux
       }
    } 
    public void mergeSort(SimpleList_Comparable<T> nodoList){
        mergesort(nodoList,0,nodoList.getLargo()-1);
    }
    private void mergesort(SimpleList_Comparable<T> A, int izq, int der){
        if (izq<der){
            int m=(izq+der)/2;
            mergesort(A,izq, m);
            mergesort(A,m+1, der);
            this.merge(A,izq, m, der);
        }
        
    }
    private void merge(SimpleList_Comparable<T> A, int izq, int m, int der){
        int i, j, k;
        SimpleList_Comparable<T> B=new SimpleList_Comparable<>();
        for (i=0; i<A.getLargo(); i++){
            B.addLast(null);
        }
        for (i=izq; i<=der; i++){ //copia ambas mitades en la lista auxiliar
            B.change(i,A.get(i));}

        i=izq; j=m+1; k=izq;
        while (i<=m && j<=der) {//copia el siguiente elemento más grande
            if (B.get(i).compareTo(B.get(j))<=0)
                A.change(k++,B.get(i++));
            else
                A.change(k++,B.get(j++));}
        while (i<=m){ //copia los elementos que quedan de la
            A.change(k++,B.get(i++));} //primera mitad (si los hay)
    }
    
    public void print() {
        Nodo<T> curr = this.head;
    	System.out.print("[ ");
        while(curr.next!=null) {
        	System.out.print(curr.getNodo()+", ");
        	curr = curr.next;
        }System.out.println(curr.getNodo()+" ]");


    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <T> getHead() {
        return head;
    }

    public void setHead (Nodo<T> head) {
        this.head = head;
    }
	public void empty() {
		 this.head = null;
		 largo = 0;
	}
}
