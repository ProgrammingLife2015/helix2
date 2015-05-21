package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.collapse.CollapseOnInterest;
import tudelft.ti2806.pl3.visualization.wrapper.operation.interest.ConstructInterestList;
import tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap.Unwrap;

import java.util.*;

/**
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer {

	private FilteredGraphModel filteredGraphModel;
	private NodeWrapper collapsedNode;
	private List<DataNodeWrapper> dataNodeWrapperList;

	private int zoomLevel = 0;

	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;

	}

	public List<DataNodeWrapper> getDataNodeWrapperList() {
		return dataNodeWrapperList;
	}

	public void setZoomLevel(int zoomLevel) {
		if(zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		}
	}
	public int getZoomLevel() {
		return zoomLevel;
	}

	public void produceDataNodeWrapperList() {
		ConstructInterestList constructInterestList = new ConstructInterestList();
		constructInterestList.calculate(collapsedNode, null);
		constructInterestList.getInterests().sort(Collections.reverseOrder());
		int numberOfNodes = (int) (Math.pow(2,zoomLevel)*10);
		CollapseOnInterest collapseOnInterest = new CollapseOnInterest(constructInterestList.getInterests().get(numberOfNodes));
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
