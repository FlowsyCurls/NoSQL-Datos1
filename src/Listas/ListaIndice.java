package Listas;

import sample.IndiceBoolean;

public class ListaIndice {
    int largo=0;
    Nodo<IndiceBoolean> head= null;


    public void addLast (IndiceBoolean e){
        if (this.head==null){
            this.head= new Nodo<IndiceBoolean>(e);
            largo++;
        }
        else {
            Nodo<IndiceBoolean> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }

    public void eliminar(String palabra){
        if (this.head.getNodo().columna.equals(palabra)){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            Nodo<IndiceBoolean> tmp=this.head;
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

    public void addFirst(IndiceBoolean e) {
        Nodo<IndiceBoolean> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }

    public IndiceBoolean get(int index){
        Nodo<IndiceBoolean> tmp = this.head;
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
    public IndiceBoolean buscarindice (String s){
        int n=0;
        IndiceBoolean indiceBoolean =new IndiceBoolean("");
        Nodo<IndiceBoolean> tmp=this.head;
        while (n<this.largo){
            if (tmp.getNodo().columna.equals(s)){
                indiceBoolean =tmp.getNodo();
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return indiceBoolean;

    }


    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <IndiceBoolean> getHead() {
        return head;
    }

    public void setHead (Nodo<IndiceBoolean> head) {
        this.head = head;
    }
    public void empty() {
        this.head = null;
        largo = 0;
    }

}
