package tudelft.ti2806.pl3.data;

import java.util.Scanner;

public enum AminoAcid {
	Phe, Leu, Ile, Met, Val, Ser, Pro, Thr, Ala, Tyr, His, Gln, Asn, Lys, Asp, Glu, Cys, Trp,
	Arg, Gly, Ochre, Amber, Opal;
	
	private static final int CODON_SIZE = 3;
	private static final int CODONS = (int) Math.pow(BasePair.MAX_GENE,
			CODON_SIZE);
	private static final AminoAcid[] TRANSLATION_TABLE = fillTranslationTable();
	private static final String TRANSLATION_TABLE_FILE = "translationTable";
	
	/**
	 * Get the {@code AminoAcid} with the given codon.
	 * 
	 * @param index
	 *            the codon code of the {@code AminoAcid}
	 * @return the {@code AminoAcid} with the given codon <br>
	 *         null if the given index is -1
	 */
	public static AminoAcid get(int index) {
		if (index == -1) {
			return null;
		}
		return TRANSLATION_TABLE[index];
	}
	
	/**
	 * Read the translation table file and store it in a table.
	 * 
	 * @return the array with for each possible codon the resulting
	 *         {@code AminoAcid}
	 */
	private static AminoAcid[] fillTranslationTable() {
		AminoAcid[] table = new AminoAcid[CODONS];
		try {
			Scanner scanner = new Scanner(AminoAcid.class.getClassLoader().getResourceAsStream(TRANSLATION_TABLE_FILE), "UTF-8");
			for (int i = 0; i < CODONS; i++) {
				String[] data = scanner.nextLine().split(" ");
				if (!data[0].startsWith("#")) {
					table[BasePair.getCodon(BasePair.toEnumString(data[0]))]
							= valueOf(data[1]);
				}
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	/**
	 * Reads a gene array and returns the written acids.
	 * 
	 * @param gene
	 *            an array of the genes to be read
	 * @return an array of all acids read
	 */
	public static AminoAcid[] getAcids(BasePair[] gene) {
		int size = gene.length / 3;
		AminoAcid[] array = new AminoAcid[size];
		for (int i = 0; i < size; i++) {
			array[i] = get(BasePair.getCodon(gene, i * 3));
		}
		return array;
	}
}
