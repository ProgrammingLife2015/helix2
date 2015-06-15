package nl.tudelft.ti2806.pl3.data.graph;

import nl.tudelft.ti2806.pl3.data.gene.GeneData;
import nl.tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import nl.tudelft.ti2806.pl3.data.label.EndGeneLabel;
import nl.tudelft.ti2806.pl3.data.label.GeneLabel;
import nl.tudelft.ti2806.pl3.data.label.StartGeneLabel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by tombrouws on 28/05/15.
 */
public class AnnotationsTest {
    private File simpleNodeGraphFile = new File("data/testdata/TestNodeGraphFile");
    private File simpleEdgeGraphFile = new File("data/testdata/TestEdgeFraphFile");
    GraphDataRepository gd;
    GeneData geneData;

    /**
     * Runs before each test.
     *
     * @throws Exception
     *         if an exception is thrown
     */
    @Before
    public void setUp() throws Exception {
        geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
        gd = new GraphDataRepository();
        gd.parseGraph(simpleNodeGraphFile, simpleEdgeGraphFile, geneData);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGeneStart() throws Exception {
        StartGeneLabel l = new StartGeneLabel("Corndogp002", 2609451);
        assertTrue(gd.getNodeByNodeId(35).getLabelList().contains(l));
    }

    @Test
    public void testGeneEnd() throws Exception {
        EndGeneLabel l = new EndGeneLabel("Corndogp002", 2609453);
        assertTrue(gd.getNodeByNodeId(1).getLabelList().contains(l));
    }

    @Test
    public void testGene() throws Exception {
        GeneLabel l = new GeneLabel("Corndogp002");
        assertTrue(gd.getNodeByNodeId(35).getLabelList().contains(l));
    }
}
