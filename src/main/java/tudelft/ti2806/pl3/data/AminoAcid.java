package tudelft.ti2806.pl3.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public enum AminoAcid {
	Phe, Leu, Ile, Met, Val, Ser, Pro, Thr, Ala, Tyr, His, Gln, Asn, Lys, Asp, Glu, Cys, Trp,
	Arg, Gly, Ochre, Amber, Opal;
	
	private static final int CODON_SIZE = 3;
	private static final int CODONS = (int) Math.pow(Gene.values().length - 1,
			CODON_SIZE);
	private static final AminoAcid[] TRANSLATION_TABLE = fillTranslationTable();
	private static final String TRANSLATION_TABLE_FILE = "data/translationTable";
	
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
		System.out.println(CODONS + " - " + CODON_SIZE);
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
			Scanner scanner = new Scanner(new File(TRANSLATION_TABLE_FILE));
			for (int i = 0; i < CODONS; i++) {
				String[] data = scanner.nextLine().split(" ");
				if (!data[0].startsWith("#")) {
					table[Gene.getCodon(Gene.getGeneString(data[0]))]
							= valueOf(data[1]);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
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
	public static AminoAcid[] getAcids(Gene[] gene) {
		int size = gene.length / 3;
		AminoAcid[] array = new AminoAcid[size];
		for (int i = 0; i < size; i++) {
			array[i] = get(Gene.getCodon(gene, i * 3));
		}
		return array;
	}
}
