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
	private String idString;

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
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
		list.addAll(dataNodeList);
	}
}
