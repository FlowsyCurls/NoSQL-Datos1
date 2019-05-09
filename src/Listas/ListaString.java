package Listas;

import java.util.ArrayList;

public class ListaString {
    int largo;
    Nodo<String> head;

    public ListaString() {
        largo=0;
        head=null;
    }

    public void addFirst(String e) {
        Nodo<String> n = new Nodo<String>(e);
        n.next=this.head;
        this.head=n;
        largo+=1;
    }
    public void eliminar(String palabra){
        if (this.head.getNodo().equals(palabra)){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            Nodo<String>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().equals(palabra)){
                    tmp.next=tmp.next.next;
                    largo-=1;
                    break;
                }
                else {
                    tmp=tmp.next;
                }
            }
        }
    }
    public String buscar (int n){
        Nodo<String>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public Boolean contiene(String string){
        Boolean contiene=false;
        Nodo<String>tmp=this.head;
        int n=0;
        while (n<this.largo){
            System.out.println(tmp.getNodo()+","+string);
            if (tmp.getNodo().equals(string)){
                contiene=true;
                break;
            }
            tmp=tmp.next;
            n++;
        }
        System.out.println(contiene);
        return contiene;
    }
    public void concatenarlistas(ListaString listaString){
        int cont=listaString.getLargo()-1;
        while (cont>=0){
            this.addFirst(listaString.buscar(cont));
            cont--;
        }
    }
    public ArrayList<String> getArraycolumnas(ListaString string){
    	ArrayList<String> array =  new ArrayList<>();
    	Nodo<String> e = string.getHead();
    	while (e!=null) {
    		array.add(e.getNodo());
    		e = e.getNext();
    		continue;
    	}
		return array;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <String> getHead() {
        return head;
    }

    public void setHead(Nodo <String> head) {
        this.head = head;
    }

	public String[] getStringArraycolumnas(ListaString string) {
		String[] c = new String[string.largo];
    	Nodo<String> e = string.getHead();
    	String str = "";
    	for (int i=0; i<string.largo; i++) {
    		c[i]= e.getNodo();
    		e = e.getNext();
    		str= str+","+c[i];
    		continue;
    	}
		System.out.println("array "+str);
		return c;		
	}

}

