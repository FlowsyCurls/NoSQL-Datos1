package sample;

public class IndiceBoolean {
    public String columna;
    public Boolean tienearbolAA=false;
    public Boolean tienearbolB=false;
    public Boolean tienearbolBPlus=false;
    public Boolean tienearbolBinario=false;
    public Boolean tieneAvl=false;

    public IndiceBoolean(String columna) {
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

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public Boolean getTienearbolAA() {
        return tienearbolAA;
    }

    public void setTienearbolAA(Boolean tienearbolAA) {
        this.tienearbolAA = tienearbolAA;
    }

    public Boolean getTienearbolB() {
        return tienearbolB;
    }

    public void setTienearbolB(Boolean tienearbolB) {
        this.tienearbolB = tienearbolB;
    }

    public Boolean getTienearbolBPlus() {
        return tienearbolBPlus;
    }

    public void setTienearbolBPlus(Boolean tienearbolBPlus) {
        this.tienearbolBPlus = tienearbolBPlus;
    }

    public Boolean getTienearbolBinario() {
        return tienearbolBinario;
    }

    public void setTienearbolBinario(Boolean tienearbolBinario) {
        this.tienearbolBinario = tienearbolBinario;
    }

    public Boolean getTieneAvl() {
        return tieneAvl;
    }

    public void setTieneAvl(Boolean tieneAvl) {
        this.tieneAvl = tieneAvl;
    }
}
