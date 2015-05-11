package tudelft.ti2806.pl3.data;

public enum BasePair {
	N(0, -1), T(1, 0), U(2, 0), C(3, 1), A(4, 2), G(5, 3);
	
	private final int value;
	public final byte storeByte;
	private static BasePair[] storeByteToBasePair = new BasePair[] { N, T, U, C, A, G };
	public static final int MAX_GENE = 4;
	
	BasePair(int storeByte, int value) {
		this.value = value;
		this.storeByte = (byte) storeByte;
	}
	
	/**
	 * Translates a three bit string of quaternary numbers into a six bit string
	 * of binary numbers.
	 *
	 * @param basePair
	 *            the string of basePairs
	 * @return the encoding of the three given basePair bits <br>
	 *         -1 if the codon contains an unknown basePair (N)
	 */
	public static int getCodon(BasePair[] basePair) {
		if (basePair[0] == N || basePair[1] == N || basePair[2] == N) {
			return -1;
		}
		return (basePair[0].value << 4) + (basePair[1].value << 2)
				+ basePair[2].value;
	}
	
	/**
	 * Translates the first three bits of the basePair string from the given
	 * index.
	 *
	 * @param basePair
	 *            the array of basePairs to be read
	 * @param index
	 *            index from where to read
	 * @return encoding of the three basePair bits <br>
	 *         -1 if the codon contains an unknown basePair (N)
	 */
	public static int getCodon(BasePair[] basePair, int index) {
		return getCodon(new BasePair[] { basePair[index], basePair[index + 1],
				basePair[index + 2] });
	}
	
	/**
	 * Reads a string and translates it into a byte array.
	 *
	 * @param string
	 *            string of basePair characters
	 * @return an array of basePairs
	 */
	public static byte[] getBasePairString(String string) {
		String[] charArray = string.split("");
		byte[] array = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			array[i] = valueOf(charArray[i]).storeByte;
		}
		return array;
	}
	
	/**
	 * Reads a string and translates it into a basePair array.
	 *
	 * @param string
	 *            string of basePair characters
	 * @return an array of basePairs
	 */
	public static BasePair[] toEnumString(String string) {
		byte[] str = getBasePairString(string);
		BasePair[] result = new BasePair[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = getBasePair(str[i]);
		}
		return result;
	}
	
	private static BasePair getBasePair(byte pair) {
		return storeByteToBasePair[pair];
	}
	
	/**
	 * Converts a {@code BasePair} array saved as a byte[] into a {@link String}
	 * .
	 * 
	 * @param array
	 *            the array to convert
	 * @return the converted string
	 */
	public static String toString(byte[] array) {
		String result = "";
		for (byte pair : array) {
			result += storeByteToBasePair[pair];
		}
		return result;
	}
}
