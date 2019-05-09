package Errores;

import java.io.IOException;

public class TamanoException extends IOException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TamanoException() {
    }

    public TamanoException(String message) {
        super(message);
    }
}
