package Errores;

import java.io.IOException;

public class DatoNoExistenteException extends IOException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatoNoExistenteException() {
    }

    public DatoNoExistenteException(String message) {
        super(message);
    }
}
