package Errores;

import java.io.IOException;

public class DatoNoExistenteException extends IOException {
    public DatoNoExistenteException() {
    }

    public DatoNoExistenteException(String message) {
        super(message);
    }
}
