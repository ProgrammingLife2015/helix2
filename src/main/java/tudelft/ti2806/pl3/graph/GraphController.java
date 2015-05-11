package tudelft.ti2806.pl3.graph;

import tudelft.ti2806.pl3.Controller;

import java.awt.Component;

/**
 * Controls the graph view. Created by Boris Mattijssen on 30-04-15.
 */
public class GraphController implements Controller {
	
	private GraphView view;
	
	/**
	 * Initialize view and pass the graph to it.
	 */
	public GraphController() {
		
	}
	
	@Override
	public Component getPanel() {
		return view;
	}
}
