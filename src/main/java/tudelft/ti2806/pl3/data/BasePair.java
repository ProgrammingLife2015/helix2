package tudelft.ti2806.pl3.data;

public enum BasePair {
	N(0, -1), T(1, 0), U(2, 0), C(3, 1), A(4, 2), G(5, 3);
	
	private int value;
	public byte storeByte;
	private static byte maxStoreByte = 5;
	private static BasePair[] storeByteToGene = new BasePair[maxStoreByte];
	public static final int MAX_GENE = 4;
	
	BasePair(int storeByte, int value) {
		this.value = value;
		this.storeByte = (byte) storeByte;
	}
	
	/**
	 * Translates a three bit string of quaternary numbers into a six bit string
	 * of binary numbers.
	 * 
	 * @param gene
	 *            the string of genes
	 * @return the encoding of the three given gene bits <br>
	 *         -1 if the codon contains an unknown gene (N)
	 */
	public static int getCodon(BasePair... gene) {
		if (gene[0] == N || gene[1] == N || gene[2] == N) {
			return -1;
		}
		return (gene[0].value << 4) + (gene[1].value << 2) + gene[2].value;
	}
	
	/**
	 * Translates the first three bits of the gene string from the given index.
	 * 
	 * @param gene
	 *            the array of genes to be read
	 * @param index
	 *            index from where to read
	 * @return encoding of the three gene bits
	 * @return -1 if the codon contains an unknown gene (N)
	 */
	public static int getCodon(BasePair[] gene, int index) {
		return getCodon(gene[index], gene[index + 1], gene[index + 2]);
	}
	
	/**
	 * Reads a string and translates it into a gene array.
	 * 
	 * @param string
	 *            string of gene characters
	 * @return an array of genes
	 */
	public static byte[] getGeneString(String string) {
		String[] charArray = string.split("");
		byte[] array = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			array[i] = valueOf(charArray[i]).storeByte;
		}
		return array;
	}
	
	public static BasePair[] toEnumString(String string) {
		byte[] str = getGeneString(string);
		BasePair[] result = new BasePair[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = getGene(str[i]);
		}
		return result;
	}
	
	private static BasePair getGene(byte b) {
		return storeByteToGene[b];
	}
}
