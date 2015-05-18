package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.Node;

import java.util.Collection;

public interface GraphModelInterface {
	
	void produceGraph(Collection<Filter<Node>> filters);

	AbstractGraphData getGraphData();
}
