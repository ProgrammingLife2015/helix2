package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.collapse.CollapseOnInterest;
import tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap.Unwrap;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer {

	private FilteredGraphModel filteredGraphModel;
	private NodeWrapper collapsedNode;
	private List<DataNodeWrapper> dataNodeWrapperList;

	private double zoomLevel = 10;



	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;

	}

	public List<DataNodeWrapper> getDataNodeWrapperList() {
		return dataNodeWrapperList;
	}

	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	public double getZoomLevel() {
		return zoomLevel;
	}

	public void produceDataNodeWrapperList() {
		CollapseOnInterest collapseOnInterest = new CollapseOnInterest(100);
		collapseOnInterest.calculate(collapsedNode, null);
		
		Unwrap unwrap = new Unwrap(collapsedNode);
		dataNodeWrapperList = unwrap.getDataNodeWrappers();

		setChanged();
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == filteredGraphModel) {
			collapsedNode = filteredGraphModel.getCollapsedNode();
			produceDataNodeWrapperList();
		}
	}
}
