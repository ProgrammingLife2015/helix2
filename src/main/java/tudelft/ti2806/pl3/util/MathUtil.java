package tudelft.ti2806.pl3.util;

public class MathUtil {
	private MathUtil() {
	}
	
	/**
	 * Gives the factorial of the given number.
	 * 
	 * @param number
	 *            the given number
	 * @return number!<br>
	 *         2 ^ 31 - 1 if an integer can't contain the result
	 */
	public static int integerFactorial(int number) {
		if (number == 0) {
			return 1;
		}
		if (number > 12) {
			return Integer.MAX_VALUE;
		}
		return number * integerFactorial(number - 1);
	}
}
