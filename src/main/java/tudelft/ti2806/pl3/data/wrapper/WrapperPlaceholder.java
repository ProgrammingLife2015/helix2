package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class WrapperPlaceholder extends Wrapper {
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
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
	}

	@Override
	public void calculateX() {
	}

}
