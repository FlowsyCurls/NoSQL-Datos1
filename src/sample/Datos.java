package sample;

import Listas.ListaString;

public class Datos {
    private String accion,respuesta;
    private String nombre,columna,dato,datos, cambio, nombre_join;
    private String indice;
    private ListaString constructores;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public ListaString getConstructores() {
        return constructores;
    }

    public void setConstructores(ListaString constructores) {
        this.constructores = constructores;
    }

    public String getNombre_join() {
        return nombre_join;
    }

    public void setNombre_join(String nombre_join) {
        this.nombre_join = nombre_join;
    }
}
