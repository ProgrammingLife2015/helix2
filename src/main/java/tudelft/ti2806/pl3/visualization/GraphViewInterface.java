package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;

import tudelft.ti2806.pl3.data.graph.AbstractGraphData;

public interface GraphViewInterface extends ViewInterface {
	/**
	 * Moves the view to the given position on the x axis.
	 * 
	 * @param newCenter
	 *            the new center of view
	 */
	void moveView(long newCenter);
	
	/**
	 * Changes the zoom level and apply it.
	 * 
	 * @param zoomLevel
	 *            the new zoom level
	 */
	void zoom(double zoomLevel);
	
	/**
	 * Generates a Graph from the current graphData.
	 * 
	 * @return a graph with all nodes from the given graphData
	 */
	Graph generateGraph();
	
	void setGraphData(AbstractGraphData abstractGraphData);
	
	void init();
}
