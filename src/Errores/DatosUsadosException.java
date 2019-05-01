package Errores;

import java.io.IOException;

public class DatosUsadosException extends IOException {
    public DatosUsadosException() {
    }

    public DatosUsadosException(String message) {
        super(message);
    }
}
