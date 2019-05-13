package Arboles;

public class ArbolB<t extends Comparable<t>,v>  {
    private static final int M = 4;//maximo de hijo por hojas, que sea mayor de 2 
    private Node root; 
    private int height;
    private int n;          
    private static final class Node {//el nodo para el tipo de datos
        private int m;                             // num de hijos
        private Entry[] children = new Entry[M];   // el array de los hijos
        private Node(int cont) {//hacemos un nodo con "n" cantidad de hijos 
            m = cont;
        }
    }
    private static class Entry<t extends Comparable<t>,v>  {//nodos internos , solo usan t y su siguiente valor
        private t key;//nodos externos, solo usan t y su v
        private final v val;
        private Node next;   
        public Entry(t key,v val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }
    public ArbolB() {//instanciamos el ArbolB
        root = new Node(0);
    }
    public v search(t key) {//es  diferente a otros metodos de busqueda
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }
    private v search(Node temp, t key, int altura) {
        Entry[] children = temp.children;
        if (altura == 0) {
            for (int j = 0; j < temp.m; j++) {//nodo externo
                if (eq(key,children[j].key)) return (v) children[j].val;//me envia la refe
            }
        }else {
            for (int j = 0; j < temp.m; j++) {//nodo interno
                if (j+1 == temp.m || less(key,children[j+1].key))//se envian los datos asi para no castearlos
                    return search(children[j].next, key,altura-1);
            }
        }
        return null;//nada si no esta o no la encuentra
    }
    public void insert(t key,v val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height); 
        n++;
        if (u == null) return;
        Node t = new Node(2);// tenemos que hacer un split al root
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node temp,t key,v refe, int altura) {
        int j;
        Entry t = new Entry(key, refe, null);
        if (altura == 0) {//Nodo externo
            for (j = 0; j < temp.m; j++) {
                if (less(key,temp.children[j].key)) break;
            }
        }else {
            for (j = 0; j < temp.m; j++) {//Nodo interno
                if ((j+1 == temp.m) || less(key, temp.children[j+1].key)) {
                    Node aux = insert(temp.children[j++].next, key, refe,altura-1);
                    if (aux == null) return null;
                    t.key = aux.children[0].key;
                    t.next = aux;
                    break;
                }
            }
        }
        for (int i = temp.m; i > j; i--)
            temp.children[i] = temp.children[i-1];
        temp.children[j] = t;
        temp.m++;
        return(temp.m < M)? null:split(temp);//tenemos que hacer un corte, al nodo por la mitad
    }
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;//cortamos el nodo en la mitad
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }
    private boolean less(Comparable k1,Comparable k2) {
        return k1.compareTo(k2) < 0;//funciones de comparacion de las keys,le ponemos Comparable, en vez de t para no castear
    }
    private boolean eq(Comparable k1,Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}
