package nl.tudelft.ti2806.pl3.visualisation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2806.pl3.data.Genome;
import nl.tudelft.ti2806.pl3.data.filter.Filter;
import nl.tudelft.ti2806.pl3.data.filter.GenomeFilter;
import nl.tudelft.ti2806.pl3.data.gene.GeneData;
import nl.tudelft.ti2806.pl3.data.graph.DataNode;
import nl.tudelft.ti2806.pl3.data.graph.Edge;
import nl.tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import nl.tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.util.WrapUtil;
import nl.tudelft.ti2806.pl3.util.EdgeUtil;
import nl.tudelft.ti2806.pl3.visualization.FilteredGraphModel;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilteredGraphModelTest {

    private List<DataNode> resultNodes;
    private List<Edge> resultEdges;
    private final String genomeToFilter = "B";

    @Before
    public void before() throws IOException {
        File nodesFile = new File("data/testdata/genomeFilter.node.graph");
        File edgesFile = new File("data/testdata/genomeFilter.edge.graph");
        GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
        GraphDataRepository graphDataRepository = new GraphDataRepository();
        graphDataRepository.parseGraph(nodesFile, edgesFile, geneData);
        FilteredGraphModel filteredGraphModel = new FilteredGraphModel(graphDataRepository);

        List<String> genomes = new ArrayList<>();
        genomes.add(genomeToFilter);
        List<Filter<DataNode>> filters = new ArrayList<>();
        filters.add(new GenomeFilter(genomes));
        filteredGraphModel.setFilters(filters);

        resultNodes = graphDataRepository.getNodeListClone();
        filteredGraphModel.filter(resultNodes);
        resultEdges = graphDataRepository.getEdgeListClone();
    }

    @Test
    public void testSize() {
        assertEquals(4, resultNodes.size());
    }

    @Test
    public void testContents() {
        for (DataNode node : resultNodes) {
            assertEquals(1, node.getCurrentGenomeSet().size());
            Genome genome = (Genome) node.getCurrentGenomeSet().toArray()[0];
            assertTrue(genome.getIdentifier().equals(genomeToFilter));
        }
    }

    @Test
    public void testDeadEdges() {
        EdgeUtil.removeAllDeadEdges(resultEdges, resultNodes);
        assertEquals(5, resultEdges.size());
        assertTrue(new Edge(resultNodes.get(0), resultNodes.get(1)).equals(resultEdges.get(0)));
        assertTrue(new Edge(resultNodes.get(1), resultNodes.get(2)).equals(resultEdges.get(1)));
        assertTrue(new Edge(resultNodes.get(0), resultNodes.get(2)).equals(resultEdges.get(2)));
        assertTrue(new Edge(resultNodes.get(2), resultNodes.get(3)).equals(resultEdges.get(3)));
        assertTrue(new Edge(resultNodes.get(0), resultNodes.get(3)).equals(resultEdges.get(4)));
    }

    @Test
    public void testEmptyEdges() {
        EdgeUtil.removeAllDeadEdges(resultEdges, resultNodes);
        WrappedGraphData wrappedGraphData = new WrappedGraphData(resultNodes, resultEdges);
        EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
        List<Wrapper> nodes = wrappedGraphData.getPositionedNodes();
        assertEquals(1, nodes.get(0).getOutgoing().size());
        assertEquals(1, nodes.get(1).getOutgoing().size());
        assertEquals(1, nodes.get(2).getOutgoing().size());
        assertEquals(0, nodes.get(3).getOutgoing().size());
        assertEquals(nodes.get(1), nodes.get(0).getOutgoing().get(0));
        assertEquals(nodes.get(2), nodes.get(1).getOutgoing().get(0));
        assertEquals(nodes.get(3), nodes.get(2).getOutgoing().get(0));
    }

    @Test
    public void testEmptyEdgesIncoming() {
        EdgeUtil.removeAllDeadEdges(resultEdges, resultNodes);
        WrappedGraphData wrappedGraphData = new WrappedGraphData(resultNodes, resultEdges);
        EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
        List<Wrapper> nodes = wrappedGraphData.getPositionedNodes();
        assertEquals(0, nodes.get(0).getIncoming().size());
        assertEquals(1, nodes.get(1).getIncoming().size());
        assertEquals(1, nodes.get(2).getIncoming().size());
        assertEquals(1, nodes.get(3).getIncoming().size());
        assertEquals(nodes.get(0), nodes.get(1).getIncoming().get(0));
        assertEquals(nodes.get(1), nodes.get(2).getIncoming().get(0));
        assertEquals(nodes.get(2), nodes.get(3).getIncoming().get(0));
    }

    @Test
    public void testCollapse() {
        EdgeUtil.removeAllDeadEdges(resultEdges, resultNodes);
        WrappedGraphData wrappedGraphData = new WrappedGraphData(resultNodes, resultEdges);
        EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
        Wrapper collapsedNode = WrapUtil.collapseGraph(wrappedGraphData).getPositionedNodes().get(0);
        assertTrue(collapsedNode instanceof HorizontalWrapper);
        assertEquals(4, ((HorizontalWrapper) collapsedNode).getNodeList().size());
    }
}