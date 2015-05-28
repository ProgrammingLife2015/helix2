package tudelft.ti2806.pl3.visualisation;

import org.junit.Before;
import org.junit.Test;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.visualization.FilteredGraphModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Boris Mattijssen on 27-05-15.
 */
public abstract class EmptyEdgesAbstract {

	protected List<Wrapper> nodes;
	protected WrappedGraphData wrappedGraphData;

	public void loadWrappedGraphData(String file) throws FileNotFoundException {
		File nodesFile = new File("data/testdata/emptyEdges/" + file + ".node.graph");
		File edgesFile = new File("data/testdata/emptyEdges/" + file + ".edge.graph");
		GraphDataRepository graphDataRepository = GraphDataRepository.parseGraph(nodesFile, edgesFile);
		wrappedGraphData = new WrappedGraphData(graphDataRepository.getNodes(), graphDataRepository.getEdges());
		FilteredGraphModel.removeAllEmptyEdges(wrappedGraphData);
		nodes = wrappedGraphData.getPositionedNodes();
	}

}
