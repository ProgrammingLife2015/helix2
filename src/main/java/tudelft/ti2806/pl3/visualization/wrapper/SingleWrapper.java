package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;

public class SingleWrapper extends NodeWrapper {
	
	private NodeWrapper target;
	
	public SingleWrapper(NodeWrapper target) {
		this.target = target;
	}

	@Override
	public long getXStart() {
		return target.getXStart();
	}
	
	@Override
	public long getXEnd() {
		return target.getXEnd();
	}
	
	@Override
	public long getWidth() {
		return target.getWidth();
	}
	
	@Override
	public String getIdString() {
		return target.getIdString();
	}
	
	@Override
	public List<Genome> getGenome() {
		return target.getGenome();
	}
	
	public NodeWrapper getNode() {
		return target;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, NodeWrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNodeInterface> list) {
		target.collectDataNodes(list);
	}
}
