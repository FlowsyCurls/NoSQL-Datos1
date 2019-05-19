package sample;

public class Indice {
    public String columna;
    public Boolean tienearbolAA=false;
    public Boolean tienearbolB=false;
    public Boolean tienearbolBPlus=false;
    public Boolean tienearbolBinario=false;
    public Boolean tieneAvl=false;

    public Indice(String columna) {
        this.columna = columna;
    }

    public Boolean Estoyvacio(){// si esto es true se deberia borrar de la lista o saber que no tiene indices ya esa columna
        if (!tienearbolAA && !tienearbolB && !tienearbolBinario && !tienearbolBPlus && !tieneAvl){
            return true;
        }else {return false;}
    }
    public Boolean Noexiste(){//si al buscar y llamar esto retorna true es que no existe el indice
        if (columna.equals("")){return true;}
        else {return false;}
        }

}
