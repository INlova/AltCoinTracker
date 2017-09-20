package exceptions;

public class AltCoinException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6703724382594843925L;

	public AltCoinException(String message) {
		System.out.println("ERROR - " + message);
		//TODO: LOG!!
		//TODO: MAIL!!
	}
}
