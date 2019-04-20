package Listas;

import java.util.Hashtable;

public class ListaTables {
    int largo;
    Nodo<Hashtable> head= null;


    public void addLast (Hashtable e){
        if (this.head==null){
            this.head= new Nodo(e);
            largo++;
        }
        else {
            Nodo <Hashtable> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }
    public void addFirst(Hashtable e) {
        Nodo<Hashtable> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }



    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <Hashtable> getHead() {
        return head;
    }

    public void setHead (Nodo<Hashtable> head) {
        this.head = head;
    }
}
