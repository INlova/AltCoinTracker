package exceptions;

public class FailedGetException extends AltCoinException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3256216393940247862L;

	public FailedGetException(String message) {
		super("Falla en consulta! - " + message);
	}
}
