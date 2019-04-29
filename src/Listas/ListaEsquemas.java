package Listas;

public class ListaEsquemas {
    int largo;
    Nodo<Esquema> head= null;


    public void addLast (Esquema e){
        if (this.head==null){
            this.head= new Nodo(e);
            largo++;
        }
        else {
            Nodo <Esquema> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<>(e);
            largo++;
        }
    }
    public void addFirst(Esquema e) {
        Nodo<Esquema> n = new Nodo<>(e);
        n.next=this.head;
        head=n;
        largo++;
    }

    public Esquema buscar (int n){
        Nodo<Esquema>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public Esquema buscar(String nombreesquema){
        Esquema esquema=null;
        Nodo<Esquema>tmp=this.head;
        int n=0;
        while (n<this.largo){
            if (tmp.getNodo().getNombre().equals(nombreesquema)){
                esquema=tmp.getNodo();
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return esquema;
    }
    public void eliminar(String nombre){
        Esquema esquema=this.buscar(nombre);
        System.out.println(esquema);
        if (esquema.existejoin()){}
        else {
            if (this.head.getNodo()==esquema){
                this.head=this.head.next;
                this.largo--;
            }
            else {
                Nodo<Esquema>tmp=this.head;
                while (tmp.next!=null){
                    if (tmp.next.getNodo()==esquema){
                        tmp.next=tmp.next.next;
                        this.largo--;
                        break;
                    }
                    else {
                        tmp=tmp.next;
                    }
                }
            }
        }
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo <Esquema> getHead() {
        return head;
    }

    public void setHead (Nodo<Esquema> head) {
        this.head = head;
    }
}
