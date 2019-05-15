package Listas;


public class NodoList <T> {
    int largo;
    Nodo<T> head= null;


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
    	}System.out.println("NO EXISTE INDICE");
    	return null;
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
