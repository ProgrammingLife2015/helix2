package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class WrapperClone extends Wrapper {

	private List<DataNode> dataNodeList;
	private final Wrapper originalNode;

	public WrapperClone(List<DataNode> dataNodeList, Wrapper originalNode) {
		this.dataNodeList = dataNodeList;
		this.originalNode = originalNode;
	}

	public List<DataNode> getDataNodeList() {
		return dataNodeList;
	}

	public Wrapper getOriginalNode() {
		return originalNode;
	}

	@Override
	public long getBasePairCount() {
		return originalNode.getBasePairCount();
	}

	@Override
	public String getIdString() {
		return originalNode.getIdString();
	}

	@Override
	public Set<Genome> getGenome() {
		return originalNode.getGenome();
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
		list.addAll(dataNodeList);
	}

	@Override
	public void calculateX() {
		this.x = originalNode.getX();
	}

	@Override
	public int getWidth() {
		return originalNode.getWidth();
	}
}
