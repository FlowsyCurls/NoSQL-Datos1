package Errores;

import java.io.IOException;

public class EsquemaNuloException extends IOException {
    public EsquemaNuloException() {
    }

    public EsquemaNuloException(String message) {
        super(message);
    }
}
