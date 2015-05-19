package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class PlaceholderWrapper extends NodeWrapper {
	@Override
	public long getXStart() {
		return 0;
	}

	@Override
	public long getXEnd() {
		return 0;
	}

	@Override
	public long getWidth() {
		return 0;
	}

	@Override
	public String getIdString() {
		return null;
	}

	@Override
	public List<Genome> getGenome() {
		return null;
	}

	@Override
	public void calculate(WrapperOperation wrapperSequencer, NodeWrapper container) {

	}

	@Override
	public void collectDataNodes(List<DataNodeInterface> list) {
	}

	@Override
	public NodeWrapper shallowClone() {
		return null;
	}

	@Override
	public NodeWrapper deepClone() {
		return null;
	}

}
