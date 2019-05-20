package sample;

import Errores.DatoNoExistenteException;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
import Listas.Esquema;
import Listas.ListaString;
import Listas.NombreArbol;

public class Accionador {

    public Datos realizar_accion(Datos datos) {
        String accion = datos.getAccion();
        if (accion.equals("crear esquema")) {
            datos = this.crearEsquema(datos);
        } else if (accion.equals("eliminar esquema")) {
            datos = this.eliminarEsquema(datos);
        } else if (accion.equals("insertar datos")) {
            datos = this.insertardatos(datos);
        } else if (accion.equals("eliminar datos")) {
            datos = this.eliminardatos(datos);
        } else if (accion.equals("buscar datos")) {
            datos = this.buscardatos(datos);
        } else if (accion.equals("buscar datos por join")) {
            datos = this.buscardatosporjoin(datos);
        } else if (accion.equals("buscar datos por indice")) {
            datos = this.buscardatosporindice(datos);
        } else if (accion.equals("crear indice")) {
            datos = this.crearindice(datos);
        } else if (accion.equals("eliminar indice")) {
            datos = this.eliminarindice(datos);
        } else if (accion.equals("Cambiar nombre de Esquema")) {
            datos = this.cambiarnombreesquema(datos);
        } else if (accion.equals("cambiar dato")) {
            datos = this.cambiardato(datos);
        } else if (accion.equals("enviar esquemas")) {
            datos = this.enviarEsquemas(datos);
        } else if (accion.equals("guardar datos")) {
            datos = this.guardarDatos(datos);
        } else if (accion.equals("buscar todos los datos")) {
            datos = this.buscartodoslosdatos(datos);
        } else if (accion.equals("buscar datos para edit")) {
            datos = this.buscardatosparaedit(datos);
        }
        return datos;
    }

    private Datos buscardatosparaedit(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscardatosparaedit());
            datos.setRespuesta("datos enviados");
        } catch (StringIndexOutOfBoundsException e) {
            datos.setRespuesta("no se encontraron datos");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("el esquema esta vacio");
        }
        return datos;
    }

    private Datos crearEsquema(Datos datos) {
        try {
            if (Server.esquemas.contiene(datos.getDatos().split(",")[0])) {
                datos.setRespuesta("nombre ya utilizado");
            } else {
                Esquema esquema = new Esquema(datos.getDatos());
                Server.esquemas.addLast(esquema);
                datos.setRespuesta("esquema creado");
            }
            System.out.println("se termina de crear esquema");
        } catch (NumberFormatException e) {
            datos.setRespuesta("el tamano solo recibe enteros");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("No existe esquema de join indicado");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("Hay columnas repetidas");
        }
        return datos;
    }

    private Datos eliminarEsquema(Datos datos) {
        try {
            Server.esquemas.eliminar(datos.getNombre());
            datos.setRespuesta("esquema eliminado");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("esquema usado");
        }

        return datos;
    }

    private Datos insertardatos(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            esquema.anadirfila(datos.getDatos());
            datos.setRespuesta("datos anadidos");
        } catch (TamanoException e) {
            datos.setRespuesta("tamano invalido");
        } catch (DatoNoExistenteException e) {
            datos.setRespuesta("no existe dato en join");
            datos.setNombre(e.getMessage());
        } catch (NumberFormatException e) {
            datos.setRespuesta("tipo incorrecto");
            datos.setDato(e.getMessage().substring(19, e.getMessage().length() - 1));
        } catch (DatosUsadosException e) {
            datos.setRespuesta("El dato del ID ya existe");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("no existen datos en esquema de join");
        }
        return datos;
    }

    private Datos eliminardatos(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            esquema.eliminarfila(datos.getDato());
            datos.setRespuesta("datos eliminados");
        } catch (DatosUsadosException e) {
            datos.setRespuesta("datos utilizados");
        }

        return datos;
    }

    private Datos buscardatos(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscardatos(datos.getDato(), datos.getColumna()));
            datos.setRespuesta("datos enviados");
        } catch (StringIndexOutOfBoundsException e) {
            datos.setRespuesta("no se encontraron datos");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("el esquema esta vacio");
        }
        return datos;
    }

    private Datos buscartodoslosdatos(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscartodos());
            datos.setRespuesta("datos enviados");
        } catch (StringIndexOutOfBoundsException e) {
            datos.setRespuesta("no se encontraron datos");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("el esquema esta vacio");
        }
        return datos;
    }

    private Datos buscardatosporjoin(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDatos(esquema.buscardatosjoin(datos.getNombre_join(), datos.getColumna(), datos.getDato()));
            datos.setRespuesta("datos enviados");
        } catch (StringIndexOutOfBoundsException e) {
            datos.setRespuesta("no se encontraron datos");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("el esquema esta vacio");
        }
        return datos;
    }

    private Datos cambiarnombreesquema(Datos datos) {
        Server.esquemas.cambiarnombreEsquema(datos.getNombre(), datos.getCambio());
        datos.setRespuesta("Nombre cambiado");
        return datos;
    }

    private Datos cambiardato(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            if (!datos.getDato().equals(datos.getColumna())) {
                esquema.cambiardato(datos.getDato(), datos.getColumna(), datos.getCambio());
                datos.setRespuesta("dato cambiado");
            } else {
                datos.setRespuesta("no se puede cambiar el ID");
            }
        } catch (DatoNoExistenteException e) {
            datos.setRespuesta("la fila no existe");
        } catch (NumberFormatException e) {
            datos.setRespuesta("tipo incorrecto");
            datos.setDato(e.getMessage().substring(19, e.getMessage().length() - 1));
        }
        return datos;
    }

    private Datos enviarEsquemas(Datos datos) {
        datos.setConstructores(Server.esquemas.crearlistaconstructores());
        datos.setRespuesta("constructores enviados");
        return datos;
    }

    @SuppressWarnings("unused")
    private Datos guardarDatos(Datos datos) {
        ListaString constructer = Server.esquemas.crearlistaconstructores();
        ListaString listadatos = Server.esquemas.crearlistadatos();
        return datos;
    }

    private Datos buscardatosporindice(Datos datos) {
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        try {
            datos.setDato(esquema.buscarporindice(datos));
            datos.setRespuesta("dato encontrado");
        } catch (DatoNoExistenteException e) {
            datos.setRespuesta("el dato no existe");
        } catch (EsquemaNuloException e) {
            datos.setRespuesta("esquema esta vacio");
        }
        return datos;
    }

    private Datos crearindice(Datos datos) {
        //Busco en el server su lista de esquemas, para buscar el esquema donde quiero hacer un arbol de indices y si no tiene datos repetidos se hace
        Esquema esquema = Server.esquemas.buscar(datos.getNombre());
        if (esquema.repetidos(datos.getColumna())) {
            datos.setRespuesta("existen datos repetidos");
        } else {//yo le espesifique que sea un ArbolB, pero eso datos lo tiene que declarar
            esquema.Meter_refe(this.pasaraEnum(datos.getIndice()), datos.getColumna());
            datos.setRespuesta("indice creado");
        }
        return datos;
    }

    private Datos eliminarindice(Datos datos) {
        Esquema esquema=Server.esquemas.buscar(datos.getNombre());
        esquema.deleteIndice(datos.getColumna());
        datos.setRespuesta("Indice eliminado");
        //Server.esquemas.buscar("nombreesquema").deleteIndice("nombre de la columna");
        return datos;
    }


    public NombreArbol pasaraEnum(String indice) {
        NombreArbol nombreaarbol;
        if (indice.equals("ArbolAA")) {
            nombreaarbol = NombreArbol.ArbolAA;
        } else if (indice.equals("ArbolBinario")) {
            nombreaarbol = NombreArbol.ArbolBinario;
        } else if (indice.equals("ArbolB")) {
            nombreaarbol = NombreArbol.ArbolB;
        } else if (indice.equals("ArbolBPlus")) {
            nombreaarbol = NombreArbol.ArbolBPlus;
        } else if (indice.equals("AVL")) {
            nombreaarbol = NombreArbol.AVL;
        } else {
            nombreaarbol = NombreArbol.ArbolRB;
        }
        return nombreaarbol;
    }

}

