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

    public Hashtable buscar (int n) {
        Nodo<Hashtable> tmp = this.head;
        while (n > 0) {
            tmp = tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public Hashtable buscar (String dato, String nombre){
        Hashtable fila=null;
        int cont=0;
        Nodo<Hashtable> tmp=this.head;
        while (cont<this.largo){
            if (tmp.getNodo().get(nombre).equals(convertir(dato,nombre))){
                fila=tmp.getNodo();
                break;
            }
            tmp=tmp.next;
            cont++;
        }
        return fila;
    }
    public void eliminar(String dato,String nombre){
        Nodo <Hashtable> tmp= this.head;
        if (tmp.getNodo().get(nombre).equals(convertir(dato,nombre))){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            while (tmp.next!=null){
                if (tmp.next.getNodo().get(nombre).equals(convertir(dato,nombre))){
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


    public boolean existe(String dato,String nombre){
        Boolean existe=false;
        int cont=0;
        Nodo<Hashtable> tmp=this.head;
        while (cont<this.largo){
            if (tmp.getNodo().get(nombre).equals(convertir(dato,nombre))){
                existe=true;
                break;
            }
            tmp=tmp.next;
            cont++;
        }
        return existe;
    }

    public Object convertir(String dato,String nombre){
        Hashtable base=this.head.getNodo();
        Object tipo=null;
        if (base.get(nombre) instanceof String) {
            tipo=dato;
        }
        else if (base.get(nombre) instanceof Integer) {
            tipo=Integer.parseInt(dato);
        }
        else if (base.get(nombre) instanceof Double) {
            tipo=Double.parseDouble(dato);
        }
        else if (base.get(nombre) instanceof Long) {
            tipo=Long.parseLong(dato);
        }
        else if (base.get(nombre) instanceof Float) {
            tipo=Float.parseFloat(dato);
        }
        return tipo;
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
