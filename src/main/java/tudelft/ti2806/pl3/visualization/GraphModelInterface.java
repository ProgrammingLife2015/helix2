package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.node.Node;

import java.util.List;

public interface GraphModelInterface {
	
	void produceGraph(List<Filter<Node>> filters);
	
	AbstractGraphData getGraphData();
}
