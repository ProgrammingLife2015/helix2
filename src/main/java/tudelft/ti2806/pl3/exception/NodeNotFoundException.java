package tudelft.ti2806.pl3.exception;

/**
 * Created by Boris Mattijssen on 30-05-15.
 */
public class NodeNotFoundException extends Exception {

	/**
	 * Construct the exception with a given message.
	 *
	 * @param s
	 * 		The message
	 */
	public NodeNotFoundException(String s) {
		super(s);
	}
}
