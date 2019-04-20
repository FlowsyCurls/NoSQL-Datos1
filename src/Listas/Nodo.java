package Listas;

public class Nodo<T>{
    private T nodo=null;
    public Nodo <T> next=null;

    public Nodo(T nodo){
        this.nodo= nodo;
        this.next= null;
    }

    public Nodo() {
    }

    public T getNodo() {
        return nodo;
    }

    public void setNodo(T nodo) {
        this.nodo = nodo;
    }

    public Nodo <T> getNext() {
        return next;
    }

    public void setNext(Nodo <T> next) {
        this.next = next;
    }
}
