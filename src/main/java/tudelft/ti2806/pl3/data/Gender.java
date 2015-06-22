package tudelft.ti2806.pl3.data;

/**
 * Created by tombrouws on 15/06/15.
 */
public enum Gender {
	MALE, FEMALE;
	
	/**
	 * Parses a string to {@code Gender}.
	 * 
	 * @param string
	 *            the string to parse
	 * @return the gender parsed
	 */
	public static Gender parse(String string) {
		if (string.equalsIgnoreCase("Male")) {
			return MALE;
		}
		return FEMALE;
	}
}
