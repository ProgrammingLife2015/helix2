package nl.tudelft.ti2806.pl3.util;

import nl.tudelft.ti2806.pl3.data.gene.GeneData;
import nl.tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import nl.tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.util.EdgeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public abstract class EmptyEdgesAbstract {

    protected List<Wrapper> nodes;
    protected WrappedGraphData wrappedGraphData;

    public void loadWrappedGraphData(String file) throws IOException {
        File nodesFile = new File("data/testdata/emptyEdges/" + file + ".node.graph");
        File edgesFile = new File("data/testdata/emptyEdges/" + file + ".edge.graph");
        GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
        GraphDataRepository graphDataRepository = new GraphDataRepository();
        graphDataRepository.parseGraph(nodesFile, edgesFile, geneData);
        wrappedGraphData = new WrappedGraphData(graphDataRepository.getNodes(), graphDataRepository.getEdges());
        EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
        nodes = wrappedGraphData.getPositionedNodes();
    }

}
