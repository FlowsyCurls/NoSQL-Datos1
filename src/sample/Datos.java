package sample;

import Listas.ListaString;

public class Datos {
    private String accion,respuesta;
    private String nombre,columna,dato,datos, cambio;
    private String indice;
    private ListaString nombre_joins,constructores;

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

    public ListaString getNombre_joins() {
        return nombre_joins;
    }
    public void setNombre_joins(ListaString nombre_joins) {
        this.nombre_joins = nombre_joins;
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
}
