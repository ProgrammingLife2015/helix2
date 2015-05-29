package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public abstract class EmptyEdgesAbstract {

	protected List<Wrapper> nodes;
	protected WrappedGraphData wrappedGraphData;

	public void loadWrappedGraphData(String file) throws FileNotFoundException {
		File nodesFile = new File("data/testdata/emptyEdges/" + file + ".node.graph");
		File edgesFile = new File("data/testdata/emptyEdges/" + file + ".edge.graph");
		GraphDataRepository graphDataRepository = new GraphDataRepository();
		graphDataRepository.parseGraph(nodesFile, edgesFile);
		wrappedGraphData = new WrappedGraphData(graphDataRepository.getNodes(), graphDataRepository.getEdges());
		EdgeUtil.removeAllEmptyEdges(wrappedGraphData);
		nodes = wrappedGraphData.getPositionedNodes();
	}

}
