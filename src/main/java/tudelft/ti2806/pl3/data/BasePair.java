package tudelft.ti2806.pl3.data;

public enum BasePair {
	N(-1), T(0), U(0), C(1), A(2), G(3);
	
	private final int value;
	public static final int MAX_GENE = 4;
	
	BasePair(int value) {
		this.value = value;
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
	 * Reads a string and translates it into a basePair array.
	 *
	 * @param string
	 *            string of basePair characters
	 * @return an array of basePairs
	 */
	public static BasePair[] toEnumString(String string) {
		String[] chars = string.split("");
		BasePair[] result = new BasePair[chars.length];
		for (int i = 0; i < chars.length; i++) {
			result[i] = BasePair.valueOf(chars[i]);
		}
		return result;
	}
}
