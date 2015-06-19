package tudelft.ti2806.pl3.data.graph;

import org.junit.Before;
import org.junit.Test;
import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.label.EndGeneLabel;
import tudelft.ti2806.pl3.data.label.GeneLabel;
import tudelft.ti2806.pl3.data.label.StartGeneLabel;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by tombrouws on 28/05/15.
 */
public class AnnotationsTest {
	private final File simpleNodeGraphFile = new File("data/testdata/TestNodeGraphFile");
	private final File simpleEdgeGraphFile = new File("data/testdata/TestEdgeGraphFile");
	private GraphDataRepository gd;
	private GeneData geneData;

	/**
	 * Runs before each test.
	 *
	 * @throws Exception
	 *             if an exception is thrown
	 */
	@Before
	public void setUp() throws Exception {
		geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
		gd = new GraphDataRepository();
		gd.parseGraph(simpleNodeGraphFile, simpleEdgeGraphFile, geneData);
	}

	@Test
	public void testGeneStart() throws Exception {
		StartGeneLabel l = new StartGeneLabel("RVBD_2744c.5 alanine rich protein", 3057261);
		assertTrue(gd.getNodeByNodeId(35).getLabelList().contains(l));
	}

	@Test
	public void testGeneEnd() throws Exception {
		EndGeneLabel l = new EndGeneLabel("RVBD_2744c.5 alanine rich protein", 3058073);
		assertTrue(gd.getNodeByNodeId(1).getLabelList().contains(l));
	}

	@Test
	public void testGene() throws Exception {
		GeneLabel l = new GeneLabel("RVBD_2744c.5 alanine rich protein");
		assertTrue(gd.getNodeByNodeId(35).getLabelList().contains(l));
	}
}
