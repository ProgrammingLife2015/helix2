package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.position.WrappedGraphData;

import java.util.List;

public interface GraphModelInterface {
	
	void produceGraph(List<Filter<DataNodeInterface>> filters);
	
	WrappedGraphData getGraphData();
}
