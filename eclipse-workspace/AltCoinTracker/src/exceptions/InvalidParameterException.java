package exceptions;

public class InvalidParameterException extends AltCoinException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 714091788735255823L;

	public InvalidParameterException(String message) {
		super("Parámetro inválido! - " + message);
	}
}
