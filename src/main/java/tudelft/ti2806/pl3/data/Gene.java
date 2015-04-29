package tudelft.ti2806.pl3.data;

public enum Gene {
	N(-1), T(0), U(0), C(1), A(2), G(3);
	
	private int value;
	
	Gene(int value) {
		this.value = value;
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
	public static int getCodon(Gene... gene) {
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
	public static int getCodon(Gene[] gene, int index) {
		return getCodon(gene[index], gene[index + 1], gene[index + 2]);
	}
	
	/**
	 * Reads a string and translates it into a gene array.
	 * 
	 * @param string
	 *            string of gene characters
	 * @return an array of genes
	 */
	public static Gene[] getGeneString(String string) {
		String[] charArray = string.split("");
		Gene[] array = new Gene[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			array[i] = valueOf(charArray[i]);
		}
		return array;
	}
}
