package tudelft.ti2806.pl3.exeption;

public class DuplicateGenomeNameException extends Exception {
	
	public DuplicateGenomeNameException(String discription, String reason) {
		super(discription + "\n" + reason);
	}
	
}
