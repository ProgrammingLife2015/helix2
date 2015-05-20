package tudelft.ti2806.pl3.visualization;

import tudelft.ti2806.pl3.visualization.wrapper.WrappedGraphData;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Boris Mattijssen on 20-05-15.
 */
public class ZoomedGraphModel extends Observable implements Observer {

	private FilteredGraphModel filteredGraphModel;
	private WrappedGraphData wrappedGraphData;

	public ZoomedGraphModel(FilteredGraphModel filteredGraphModel) {
		this.filteredGraphModel = filteredGraphModel;
	}

	public WrappedGraphData getWrappedGraphData() {
		return wrappedGraphData;
	}

	private void produceWrappedGraphData() {
		// TODO: select what to collapse
		// TODO: unwrap
		// TODO: calc x-pos
		notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == filteredGraphModel) {
			wrappedGraphData = filteredGraphModel.getWrappedGraphData();
			produceWrappedGraphData();
		}
	}
}
