package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.LoadingObservable;
import tudelft.ti2806.pl3.LoadingObserver;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.operation.collapse.CollapseOnInterest;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.Unwrap;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.UnwrapOnCollapse;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This Model contains the data after a zoom has been performed.
 * <p>
 * It listens to the {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel}
 * for changes. If this model has updated his data, this class will also update
 * his data and notify the view.
 * </p>
 * <p>
 * We can also just alter the zoom level on this class and then it will
 * recalculate the data and notify the view.
 * </p>
 * <p>
 * Recalculation of the data means, that it takes the collapsedNode from the
 * {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel} and filters them
 * based on the interest value. Then it unwraps the collapsed node and notifies
 * the view.
 * </p>
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer,
		LoadingObservable {
	
	private static final int MIN_NODE_COUNT = 20;
	private FilteredGraphModel filteredGraphModel;
	private Wrapper collapsedNode;
	private List<WrapperClone> dataNodeWrapperList;
	private ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	
	private int zoomLevel = 1;
	
	/**
	 * Construct a new ZoomedGraphModel, with a reference to the
	 * {@link tudelft.ti2806.pl3.data.filter.Filter}.
	 *
	 * @param filteredGraphModel
	 *            The
	 *            {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel}
	 */
	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;
	}
	
	public List<WrapperClone> getDataNodeWrapperList() {
		return dataNodeWrapperList;
	}
	
	/**
	 * Sets the zoom level, only if the zoom level is larger then 0.
	 *
	 * @param zoomLevel
	 *            the new zoom level
	 */
	public void setZoomLevel(int zoomLevel) {
		if (zoomLevel > 0) {
			this.zoomLevel = zoomLevel;
		}
	}
	
	public int getZoomLevel() {
		return zoomLevel;
	}
	
	/**
	 * Produces the data needed to display the graph.
	 * 
	 * <p>
	 * It first construct a list of all interests in the graph<br>
	 * It will then determine the amount of nodes to display<br>
	 * It will then use the {@link CollapseOnInterest} operation to collapse all
	 * uninteresting nodes<br>
	 * It will then unwrap these nodes and notify the view
	 */
	public void produceDataNodeWrapperList() {
		notifyLoadingObservers(true);
		int nodeCount = Math.min(MIN_NODE_COUNT * zoomLevel, filteredGraphModel
				.getCalculateCollapse().getCollapses().size() - 1);
		Unwrap unwrap = new UnwrapOnCollapse(filteredGraphModel
				.getCalculateCollapse().getCollapses().get(nodeCount));
		unwrap.compute(collapsedNode);
		dataNodeWrapperList = unwrap.getWrapperClones();
		
		setChanged();
		notifyObservers();
		notifyLoadingObservers(false);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == filteredGraphModel) {
			collapsedNode = filteredGraphModel.getCollapsedNode();
			produceDataNodeWrapperList();
		}
	}
	
	public Wrapper getWrappedCollapsedNode() {
		return collapsedNode;
	}
	
	@Override
	public void addLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.add(loadingObservable);
	}
	
	@Override
	public void addLoadingObserversList(
			ArrayList<LoadingObserver> loadingObservers) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			addLoadingObserver(loadingObserver);
		}
	}
	
	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObservable) {
		loadingObservers.remove(loadingObservable);
	}
	
	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			loadingObserver.update(this, arguments);
		}
	}
}
