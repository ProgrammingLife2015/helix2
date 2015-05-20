package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.data.filter.Filter;
import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class GraphController {
	private GraphModel model;
	private GraphView view;
	private double currentZoomLevel;
	private long currentZoomCenter;
	private List<Filter<DataNode>> filters = new ArrayList<>();
	
	/**
	 * Initialise an instance of GraphControler.<br>
	 * Also initialises the {@link GraphModel} and {@link GraphView}.
	 * 
	 * @param view
	 *            the view to use
	 * @param model
	 *            the model to use
	 */
	public GraphController(GraphView view, GraphModel model) {
		this.model = model;
		this.view = view;
		model.produceGraph(new ArrayList<>());
		//view.setGraphData(model.getGraphData());
		view.init();
	}
	
	/**
	 * Moves the view to a new center position.
	 * 
	 * @param newZoomCenter
	 *            the new center of zoom
	 */
	public void moveView(long newZoomCenter) {
		currentZoomCenter = newZoomCenter;
		view.moveView(currentZoomCenter);
	}
	
	/**
	 * Changes the zoom, and if necessary, filters are applied.
	 * 
	 * @param newZoomLevel
	 *            the new level of zoom to apply
	 */
	public void changeZoom(double newZoomLevel) {
		if (Math.round(newZoomLevel) != Math.round(currentZoomLevel)
				&& filters.size() != 0) {
			model.produceGraph(filters);
		}
		currentZoomLevel = newZoomLevel;
		view.zoom(newZoomLevel);
	}

	public GraphView getView() {
		return view;
	}

	public Component getPanel() {
		return view.getPanel();
	}

	public double getCurrentZoomLevel() {
		return currentZoomLevel;
	}

	public long getCurrentZoomCenter() {
		return currentZoomCenter;
	}
}
