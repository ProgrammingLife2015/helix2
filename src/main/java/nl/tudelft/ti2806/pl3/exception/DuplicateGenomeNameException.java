package nl.tudelft.ti2806.pl3.exception;

/**
 * Exception thrown when an error occurs due to two or more genome on the graph
 * using the same name.
 *
 * @author Sam Smulders
 */
@SuppressWarnings("serial")
public class DuplicateGenomeNameException extends Exception {

    public DuplicateGenomeNameException(String discription, String reason) {
        super(discription + "\n" + reason);
    }

}
