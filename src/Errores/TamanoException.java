package Errores;

import java.io.IOException;

public class TamanoException extends IOException {

    public TamanoException() {
    }

    public TamanoException(String message) {
        super(message);
    }
}
