package Listas;

public class Tamano {
    public String nombre;
    public int longitud;

    public Tamano(String nombre, int longitud) {
        this.nombre = nombre;
        this.longitud = longitud;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
