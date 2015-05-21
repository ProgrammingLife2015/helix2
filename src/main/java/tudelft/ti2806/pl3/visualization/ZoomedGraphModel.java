package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.collapse.CollapseOnInterest;
import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.CalculateAddMaxOfWrapped;
import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.CalculateSizeInterest;
import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.CalculateWrapPressureInterest;
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
	private final int pressureMultiplier = 10;

	private CalculateWrapPressureInterest pressureInterest;
	private CalculateAddMaxOfWrapped addMaxOfWrapped;
	private CalculateSizeInterest sizeInterest;
	//private CalculateGroupInterest groupInterest;

	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;
		pressureInterest = new CalculateWrapPressureInterest(pressureMultiplier);
		addMaxOfWrapped = new CalculateAddMaxOfWrapped();
		sizeInterest = new CalculateSizeInterest();
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
		pressureInterest.calculate(collapsedNode, null);
		addMaxOfWrapped.calculate(collapsedNode, null);
		sizeInterest.calculate(collapsedNode, null);
		CollapseOnInterest collapseOnInterest = new CollapseOnInterest(100);
		collapseOnInterest.calculate(collapsedNode, null);

		Unwrap unwrap = new Unwrap(collapsedNode);
		dataNodeWrapperList = unwrap.getDataNodeWrappers();
		// TODO: calc x-pos
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
