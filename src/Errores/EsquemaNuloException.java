package Errores;

import java.io.IOException;

public class EsquemaNuloException extends IOException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EsquemaNuloException() {
    }

    public EsquemaNuloException(String message) {
        super(message);
    }
}
