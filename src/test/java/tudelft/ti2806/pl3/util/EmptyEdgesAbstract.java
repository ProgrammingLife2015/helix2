package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public abstract class EmptyEdgesAbstract {

	List<Wrapper> nodes;
	WrappedGraphData wrappedGraphData;

	void loadWrappedGraphData(String file) throws IOException {
		File nodesFile = new File("data/testdata/emptyEdges/" + file + ".node.graph");
		File edgesFile = new File("data/testdata/emptyEdges/" + file + ".edge.graph");
		GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");
		GraphDataRepository graphDataRepository = new GraphDataRepository();
		graphDataRepository.parseGraph(nodesFile, edgesFile, geneData);
		wrappedGraphData = new WrappedGraphData(graphDataRepository);
		EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
		nodes = wrappedGraphData.getPositionedNodes();
	}

}
