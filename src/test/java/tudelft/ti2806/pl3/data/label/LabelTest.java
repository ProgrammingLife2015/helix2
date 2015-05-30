package tudelft.ti2806.pl3.data.label;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tombrouws on 30/05/15.
 */
public class LabelTest {

	/**
	 * Label constructor test
	 */
	@Test
	public void constructorTest() {
		GeneLabel geneLabel = new GeneLabel(null);
		assertTrue(geneLabel.getText().equals(""));
	}

	/**
	 * Test if label equals itself
	 */
	@Test
	public void equalsTest() {
		GeneLabel geneLabel = new GeneLabel("Gene1");
		StartGeneLabel startGeneLabel = new StartGeneLabel("Gene2", 10);
		EndGeneLabel endGeneLabel = new EndGeneLabel("Gene3", 20);

		assertTrue(geneLabel.equals(geneLabel));
		assertTrue(startGeneLabel.equals(startGeneLabel));
		assertTrue(endGeneLabel.equals(endGeneLabel));
	}

	/**
	 * Test if label equals null
	 */
	@Test
	public void equalsNullTest() {
		GeneLabel geneLabel = new GeneLabel("Gene1");
		StartGeneLabel startGeneLabel = new StartGeneLabel("Gene2", 10);
		EndGeneLabel endGeneLabel = new EndGeneLabel("Gene3", 20);

		assertFalse(geneLabel.equals(null));
		assertFalse(startGeneLabel.equals(null));
		assertFalse(endGeneLabel.equals(null));
	}

	/**
	 * Test if label equals another label
	 */
	@Test
	public void equalsAnotherTest() {
		GeneLabel geneLabel1 = new GeneLabel("Gene1");
		GeneLabel geneLabel2 = new GeneLabel("Gene1");
		StartGeneLabel startGeneLabel1 = new StartGeneLabel("Gene2", 10);
		StartGeneLabel startGeneLabel2 = new StartGeneLabel("Gene2", 10);
		EndGeneLabel endGeneLabel1 = new EndGeneLabel("Gene3", 20);
		EndGeneLabel endGeneLabel2 = new EndGeneLabel("Gene3", 20);

		assertTrue(geneLabel1.equals(geneLabel2));
		assertTrue(startGeneLabel1.equals(startGeneLabel2));
		assertTrue(endGeneLabel1.equals(endGeneLabel2));
	}

	/**
	 * Test if label equals another label
	 */
	@Test
	public void equalsAnotherFalseTest() {
		GeneLabel geneLabel1 = new GeneLabel("Gene1");
		GeneLabel geneLabel2 = new GeneLabel("Gene2");
		StartGeneLabel startGeneLabel1 = new StartGeneLabel("Gene2", 10);
		StartGeneLabel startGeneLabel2 = new StartGeneLabel("Gene3", 12);
		EndGeneLabel endGeneLabel1 = new EndGeneLabel("Gene3", 20);
		EndGeneLabel endGeneLabel2 = new EndGeneLabel("Gene4", 20);

		assertFalse(geneLabel1.equals(geneLabel2));
		assertFalse(startGeneLabel1.equals(startGeneLabel2));
		assertFalse(endGeneLabel1.equals(endGeneLabel2));
	}

	/**
	 * Test if label hashes to the same value as a copy of the gene
	 */
	@Test
	public void hashCodeTestTrue() {
		GeneLabel geneLabel1 = new GeneLabel("Gene1");
		GeneLabel geneLabel2 = new GeneLabel("Gene1");
		StartGeneLabel startGeneLabel1 = new StartGeneLabel("Gene2", 10);
		StartGeneLabel startGeneLabel2 = new StartGeneLabel("Gene2", 10);
		EndGeneLabel endGeneLabel1 = new EndGeneLabel("Gene3", 20);
		EndGeneLabel endGeneLabel2 = new EndGeneLabel("Gene3", 20);

		assertTrue(geneLabel1.hashCode() == geneLabel2.hashCode());
		assertTrue(startGeneLabel1.hashCode() == startGeneLabel2.hashCode());
		assertTrue(endGeneLabel1.hashCode() == endGeneLabel2.hashCode());
	}

	/**
	 * Test if label hashes to the same value as a copy of the gene
	 */
	@Test
	public void hashCodeTestFalse() {
		GeneLabel geneLabel1 = new GeneLabel("Gene1");
		GeneLabel geneLabel2 = new GeneLabel("Gene2");
		StartGeneLabel startGeneLabel1 = new StartGeneLabel("Gene2", 10);
		StartGeneLabel startGeneLabel2 = new StartGeneLabel("Gene3", 10);
		EndGeneLabel endGeneLabel1 = new EndGeneLabel("Gene3", 20);
		EndGeneLabel endGeneLabel2 = new EndGeneLabel("Gene3", 15);

		assertFalse(geneLabel1.hashCode() == geneLabel2.hashCode());
		assertFalse(startGeneLabel1.hashCode() == startGeneLabel2.hashCode());
		assertFalse(endGeneLabel1.hashCode() == endGeneLabel2.hashCode());
	}

	/**
	 * Test the name getter & setter
	 */
	@Test
	public void nameTest() {
		GeneLabel geneLabel1 = new GeneLabel("Gene1");
		assertTrue(geneLabel1.getText().equals("Gene1"));

		geneLabel1.setText("Gene2");
		assertTrue(geneLabel1.getText().equals("Gene2"));
		assertFalse(geneLabel1.getText().equals("Gene1"));
	}

	/**
	 * Test the reference getter
	 */
	@Test
	public void refTest() {
		StartGeneLabel startGeneLabel1 = new StartGeneLabel("Gene2", 10);
		EndGeneLabel endGeneLabel1 = new EndGeneLabel("Gene3", 20);

		assertTrue(startGeneLabel1.getStartRef() == 10);
		assertTrue(endGeneLabel1.getEndRef() == 20);

		assertFalse(startGeneLabel1.getStartRef() == 15);
		assertFalse(endGeneLabel1.getEndRef() == 25);
	}
}
