package Errores;

import java.io.IOException;

public class TamañoException extends IOException {

    public TamañoException() {
    }

    public TamañoException(String message) {
        super(message);
    }
}
