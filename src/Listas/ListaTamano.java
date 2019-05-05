package Listas;

public class ListaTamano {
    int largo;
    Nodo<Tamano> head= null;


    public void addLast (Tamano e){
        if (this.head==null){
            this.head= new Nodo<Tamano>(e);
            largo++;
        }
        else {
            Nodo<Tamano> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<Tamano>(e);
            largo++;
        }
    }

    public int buscartamano (String s){
        int n=0;
        int tamano=-1;
        Nodo<Tamano>tmp=this.head;
        while (n<this.largo){
            if (tmp.getNodo().nombre.equals(s)){
                tamano=tmp.getNodo().longitud;
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return tamano;
    }
    public String buscarnombre (int n){
        Nodo<Tamano>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo().getNombre();
    }

    public Boolean contiene(String string){
        Boolean contiene=false;
        Nodo<Tamano>tmp=this.head;
        int n=0;
        while (n<this.largo){
            if (tmp.getNodo().getNombre().equals(string)){
                contiene=true;
                break;
            }
            tmp=tmp.next;
            n++;
        }
        return contiene;
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
