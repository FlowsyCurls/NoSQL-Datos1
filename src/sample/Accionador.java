package sample;

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
    private Datos crearEsquema(Datos datos){
        Esquema esquema=new Esquema(datos.getDatos());
        if (Server.esquemas.contiene(esquema.getNombre())){
            datos.setRespuesta("nombre ya utilizado");
        }
        else {
            Server.esquemas.addLast(esquema);
            datos.setRespuesta("esquema creado");
        }
        System.out.println("se termina de crear esquema");
        return datos;
    }
    private Datos eliminarEsquema(Datos datos){
        Server.esquemas.eliminar(datos.getNombre());
        datos.setRespuesta("esquema eliminado");
        return datos;
    }
    private Datos insertardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        esquema.añadirfila(datos.getDatos());
        datos.setRespuesta("datos añadidos");
        return datos;
    }
    private Datos eliminardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        esquema.eliminarfila(datos.getDato());
        datos.setRespuesta("datos eliminados");
        return datos;
    }
    private Datos buscardatos(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        datos.setDatos(esquema.buscardatos(datos.getDato(),datos.getColumna()));
        datos.setDatos("datos enviados");
        return datos;
    }
    private Datos buscardatosporjoin(Datos datos){
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        datos.setDatos(esquema.buscardatosjoin(datos.getNombre_join(),datos.getColumna(),datos.getDato()));
        datos.setDatos("datos enviados");
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
