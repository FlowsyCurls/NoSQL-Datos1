package Listas;

import sample.Indice;

public class ListaIndice {
    int largo;
    Nodo<Indice> head= null;


    public void addLast (Indice e){
        if (this.head==null){
            this.head= new Nodo<Indice>(e);
            largo++;
        }
        else {
            Nodo <Indice> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }

    public void eliminar(String palabra){
        if (this.head.getNodo().equals(palabra)){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            Nodo<Indice>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().columna.equals(palabra)){
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

    public void addFirst(Indice e) {
        Nodo<Indice> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }

    public Indice get(int index){
        Nodo<Indice> tmp = this.head;
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
    public Indice buscarindice (String s){
        int n=0;
        Indice indice=new Indice("");
        Nodo<Indice>tmp=this.head;
        while (n<this.largo){
            if (tmp.getNodo().columna.equals(s)){
                indice=tmp.getNodo();
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return indice;

    }


    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <Indice> getHead() {
        return head;
    }

    public void setHead (Nodo<Indice> head) {
        this.head = head;
    }
    public void empty() {
        this.head = null;
        largo = 0;
    }

}
