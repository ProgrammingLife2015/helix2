package tudelft.ti2806.pl3;

/**
 * Our main repository for all shared data.
 * Created by Boris Mattijssen on 06-05-15.
 */
public class Repository {
	private static Repository ourInstance = new Repository();

	/**
	 * Get the singleton instance.
	 * @return the singleton instance
	 */
	public static Repository getInstance() {
		return ourInstance;
	}

	// private ArrayList<Node> nodes = null;
	// private ArrayList<Node> edges = null;

	/**
	 * Instantiate the repository.
	 */
	private Repository() {

	}
}
