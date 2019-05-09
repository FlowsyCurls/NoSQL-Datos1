package Errores;

import java.io.IOException;

public class DatosUsadosException extends IOException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatosUsadosException() {
    }

    public DatosUsadosException(String message) {
        super(message);
    }
}
