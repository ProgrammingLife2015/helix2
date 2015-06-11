package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.operation.GetFirstCombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.Unwrap;
import tudelft.ti2806.pl3.data.wrapper.operation.unwrap.UnwrapOnCollapse;
import tudelft.ti2806.pl3.util.EdgeUtil;
import tudelft.ti2806.pl3.util.observable.LoadingObservable;
import tudelft.ti2806.pl3.util.observers.LoadingObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This Model contains the data after a zoom has been performed.
 * <p>
 * It listens to the {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel} for changes. If this model has updated
 * his data, this class will also update his data and notify the view.
 * </p>
 * <p>
 * We can also just alter the zoom level on this class and then it will recalculate the data and notify the view.
 * </p>
 * <p>
 * Recalculation of the data means, that it takes the collapsedNode from the
 * {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel} and filters them based on the interest value. Then it
 * unwraps the collapsed node and notifies the view.
 * </p>
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer, LoadingObservable {
	/**
	 * Minimum distance between nodes in pixels, can be overruled by {@link #MIN_NODE_COUNT}.
	 */
	private static final float MIN_NODE_DISTANCE = 10f;
	private FilteredGraphModel filteredGraphModel;
	private Wrapper collapsedNode;
	private List<WrapperClone> dataNodeWrapperList;
	private ArrayList<LoadingObserver> loadingObservers = new ArrayList<>();
	
	private int zoomLevel = 1;
	private int graphWidth;
	
	/**
	 * Construct a new ZoomedGraphModel, with a reference to the {@link tudelft.ti2806.pl3.data.filter.Filter}.
	 *
	 * @param filteredGraphModel
	 *            The {@link tudelft.ti2806.pl3.visualization.FilteredGraphModel}
	 */
	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;
	}
	
	public List<WrapperClone> getDataNodeWrapperList() {
		return this.dataNodeWrapperList;
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
		return this.zoomLevel;
	}
	
	/**
	 * Produces the data needed to display the graph.
	 * 
	 * <p>
	 * It first construct a list of all interests in the graph<br>
	 * It will then determine the amount of nodes to display<br>
	 * It will then unwrap these nodes and calculate the weight of all edges
	 * then it will notify the view.
	 */
	public void produceDataNodeWrapperList() {
		notifyLoadingObservers(true);
		float condition = Math.min((this.graphWidth * MIN_NODE_DISTANCE)
				/ (ScreenSize.getInstance().getWidth() * this.zoomLevel),
				new GetFirstCombineWrapper().compute(this.collapsedNode).getCollapse());
		
		Unwrap unwrap = new UnwrapOnCollapse(condition);
		unwrap.compute(collapsedNode);
		dataNodeWrapperList = unwrap.getWrapperClones();
		EdgeUtil.setEdgeWeight(dataNodeWrapperList);
		
		setChanged();
		notifyObservers();
		notifyLoadingObservers(false);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o == this.filteredGraphModel) {
			this.collapsedNode = this.filteredGraphModel.getCollapsedNode();
			this.graphWidth = this.collapsedNode.getWidth();
			produceDataNodeWrapperList();
		}
	}

	public List<Genome> getGenomes() {
		return filteredGraphModel.getGenomes();
	}
	
	public Wrapper getWrappedCollapsedNode() {
		return this.collapsedNode;
	}
	
	@Override
	public void addLoadingObserver(LoadingObserver loadingObservable) {
		this.loadingObservers.add(loadingObservable);
	}
	
	@Override
	public void addLoadingObserversList(ArrayList<LoadingObserver> loadingObservers) {
		for (LoadingObserver loadingObserver : loadingObservers) {
			addLoadingObserver(loadingObserver);
		}
	}
	
	@Override
	public void deleteLoadingObserver(LoadingObserver loadingObservable) {
		this.loadingObservers.remove(loadingObservable);
	}
	
	@Override
	public void notifyLoadingObservers(Object arguments) {
		for (LoadingObserver loadingObserver : this.loadingObservers) {
			loadingObserver.update(this, arguments);
		}
	}
}
