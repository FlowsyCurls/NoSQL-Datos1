package Listas;

public class SimpleNode_Comparable<T extends Comparable<T>>{
    private T nodo=null;
    public SimpleNode_Comparable <T> next=null;

    public SimpleNode_Comparable(T nodo){
        this.nodo= nodo;
        this.next= null;
    }

    public SimpleNode_Comparable() {
    }

    public T getNodo() {
        return nodo;
    }

    public void setNodo(T nodo) {
        this.nodo = nodo;
    }

    public SimpleNode_Comparable <T> getNext() {
        return next;
    }

    public void setNext(SimpleNode_Comparable <T> next) {
        this.next = next;
    }
}
