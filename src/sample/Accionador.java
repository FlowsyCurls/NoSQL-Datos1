package sample;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import Listas.Esquema;

public class Accionador {

    public Datos realizar_accion(Datos datos){
        String accion=datos.getAccion();
        if (accion.equals("crear esquema")){datos=this.crearEsquema(datos);}
        else if (accion.equals("eliminar esquema")){datos=this.eliminarEsquema(datos);}
        else if (accion.equals("insertar datos")){datos=this.insertardatos(datos);}
        else if (accion.equals("eliminar datos")){datos=this.eliminardatos(datos);}
        else if (accion.equals("buscar datos")){datos=this.buscardatos(datos);}
        else if (accion.equals("buscar datos por join")){datos=this.buscardatosporjoin(datos);}
        else if (accion.equals("buscar datos por indice")){datos=this.buscardatosporindice(datos);}
        else if (accion.equals("crear indice")){datos=this.crearindice(datos);}
        else if (accion.equals("eliminar indice")){datos=this.eliminarindice(datos);}
        return datos;
    }
    private Datos crearEsquema(Datos datos) {
        try {
        Esquema esquema = new Esquema(datos.getDatos());
        if (Server.esquemas.contiene(esquema.getNombre())) {
            datos.setRespuesta("nombre ya utilizado");
        } else {
            Server.esquemas.addLast(esquema);
            datos.setRespuesta("esquema creado");
        }
        System.out.println("se termina de crear esquema");
    }catch (NumberFormatException e){
            datos.setRespuesta("el tamano solo recibe enteros");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("No existe esquema de join indicado");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("Hay columnas repetidas");
        }
        return datos;
    }
    private Datos eliminarEsquema(Datos datos){
        try {
            Server.esquemas.eliminar(datos.getNombre());
            datos.setRespuesta("esquema eliminado");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("esquema usado");
        }

        return datos;
    }
    private Datos insertardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        try {
            esquema.anadirfila(datos.getDatos());
            datos.setRespuesta("datos anadidos");
        } catch (TamanoException e) {
            datos.setRespuesta("tamano invalido");
        } catch (DatoNoExistenteException e) {
            datos.setRespuesta("no existe dato en join");
            datos.setNombre(e.getMessage());
        }catch (NumberFormatException e){
            datos.setRespuesta("tipo incorrecto");
            datos.setDato(e.getMessage().substring(19,e.getMessage().length()-1));
        } catch (DatosUsadosException e) {
            datos.setRespuesta("El dato del ID ya existe");
        }
        return datos;
    }
    private Datos eliminardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        try {
            esquema.eliminarfila(datos.getDato());
            datos.setRespuesta("datos eliminados");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("datos utilizados");
        }

        return datos;
    }
    private Datos buscardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscardatos(datos.getDato(),datos.getColumna()));
            datos.setRespuesta("datos enviados");
        }catch (StringIndexOutOfBoundsException e){
            datos.setRespuesta("no se encontraron datos");
        }

        return datos;
    }
    private Datos buscardatosporjoin(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscardatosjoin(datos.getNombre_joins(),datos.getColumna(),datos.getDato()));
            datos.setRespuesta("datos enviados");
        }catch (StringIndexOutOfBoundsException e){
            datos.setRespuesta("no se encontraron datos");
        }
        return datos;
    }
    private Datos buscardatosporindice(Datos datos){
        return datos;
    }
    private Datos crearindice(Datos datos){
        return datos;
    }
    private Datos eliminarindice(Datos datos){
        return datos;
    }

}
