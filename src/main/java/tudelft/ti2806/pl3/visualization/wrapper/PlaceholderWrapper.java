package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

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
	public Set<Genome> getGenome() {
		return null;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, NodeWrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNodeInterface> list) {
	}

}
