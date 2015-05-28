package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

public class SingleWrapper extends Wrapper {
	
	private Wrapper target;
	
	public SingleWrapper(Wrapper target) {
		this.target = target instanceof SingleWrapper ? ((SingleWrapper) target).getNode() : target;
	}
	
	@Override
	public long getWidth() {
		return target.getWidth();
	}
	
	@Override
	public String getIdString() {
		return target.getIdString();
	}

	@Override public int getId() {
		return target.getId();
	}

	@Override
	public Set<Genome> calculateGenome() {
		return target.getGenome();
	}
	
	public Wrapper getNode() {
		return target;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
		target.collectDataNodes(list);
	}
}
