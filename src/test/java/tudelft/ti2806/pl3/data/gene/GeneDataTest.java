package tudelft.ti2806.pl3.data.gene;

import org.junit.Before;
import org.junit.Test;
import tudelft.ti2806.pl3.data.label.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GeneDataTest {

	/**
	 * GeneData data container.
	 */
	private GeneData geneData = null;

	/**
	 * Read the test file.
	 */
	@Before
	public void before() {
		try {
			geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
		} catch (IOException e) {
			fail("Could not find test file");
		}
	}

	/**
	 * Make sure two genes are read from the test file.
	 */
	@Test
	public void parseGenesSizeTest() {
		assertEquals(geneData.getGenes().size(), 2);
	}

	/**
	 * Make sure the first gene is parsed properly.
	 */
	@Test
	public void parseGenesFirstGeneTest() {
		Integer start = 3057261;
		Integer end = 3058073;
		Gene gene = new Gene("RVBD_2744c.5 alanine rich protein", start, end);
		assertTrue(geneData.getGenes().get(0).equals(gene));
		assertTrue(geneData.getGeneStart().get(start).equals(gene));
		assertTrue(geneData.getGeneEnd().get(end).equals(gene));
	}

	/**
	 * Make sure the parse method for a single line is correct.
	 */
	@Test
	public void parseSingleGeneTest() {
		ArrayList<Gene> genes = new ArrayList<>();
		Map<String, Label> labelMap = new HashMap<>();
		Map<Integer, Gene> geneStart = new HashMap<>();
		Map<Integer, Gene> geneEnd = new HashMap<>();

		String line = "MT_H37RV_BRD_V5\tnull\tCDS\t3057261\t3058073\t0.0\t-\t.\tcalhounClass=Gene;Name=alanine rich "
				+ "protein;ID=7000008459999039;displayName=RVBD_2744c.5 alanine rich protein";
		Integer start = 3057261;
		Integer end = 3058073;
		Gene gene = new Gene("RVBD_2744c.5 alanine rich protein", start, end);
		GeneData.parseGene(line, genes, geneStart, geneEnd, labelMap);
		assertTrue(genes.get(0).equals(gene));
		assertTrue(geneStart.get(start).equals(gene));
		assertTrue(geneEnd.get(end).equals(gene));
	}

}