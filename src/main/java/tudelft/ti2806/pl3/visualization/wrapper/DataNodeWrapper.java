package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Set;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class DataNodeWrapper extends NodeWrapper {

	private List<DataNode> dataNodeList;
	private final NodeWrapper originalNode;
	private String idString;

	public DataNodeWrapper(List<DataNode> dataNodeList, NodeWrapper originalNode) {
		this.dataNodeList = dataNodeList;
		this.originalNode = originalNode;
	}

	public List<DataNode> getDataNodeList() {
		return dataNodeList;
	}

	public NodeWrapper getOriginalNode() {
		return originalNode;
	}

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
		return idString;
	}

	public void setIdString(String idString) {
		this.idString = idString;
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
	public void collectDataNodes(List<DataNode> list) {
		list.addAll(dataNodeList);
	}
}
