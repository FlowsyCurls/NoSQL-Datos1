package Listas;


public class NodoList <T> {
    int largo;
    Nodo<T> head= null;


    public void addLast (T e){
        if (this.head.getNodo()==null){
            this.head= new Nodo(e);
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
    public void addFirst(T e) {
        Nodo<T> n = new Nodo<>(e);
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

    public Nodo <T> getHead() {
        return head;
    }

    public void setHead (Nodo<T> head) {
        this.head = head;
    }
}
