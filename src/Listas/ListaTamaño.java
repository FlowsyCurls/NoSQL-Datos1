package Listas;

public class ListaTamaño {
    int largo;
    Nodo<Tamaño> head= null;


    public void addLast (Tamaño e){
        if (this.head==null){
            this.head= new Nodo(e);
            largo++;
        }
        else {
            Nodo tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo(e);
            largo++;
        }
    }

    public int buscartamaño (String s){
        int n=0;
        int tamaño=-1;
        Nodo<Tamaño>tmp=this.head;
        while (n<this.largo){
            if (tmp.getNodo().nombre.equals(s)){
                tamaño=tmp.getNodo().longitud;
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return tamaño;
    }


    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

}
