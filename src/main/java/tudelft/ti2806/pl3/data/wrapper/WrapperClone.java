package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.label.Label;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class WrapperClone extends Wrapper {

	private Set<DataNode> dataNodes;
	private Set<Label> labels;
	private final Wrapper originalNode;

	public WrapperClone(Set<DataNode> dataNodeList, Wrapper originalNode) {
		this.dataNodes = dataNodeList;
		this.originalNode = originalNode;
	}

	public Set<DataNode> getDataNodes() {
		return dataNodes;
	}

	@Override
	public void collectLabels(Set<Label> labels) {
		dataNodes.forEach(n -> labels.addAll(n.getLabelList()));
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

	@Override public int getId() {
		return originalNode.getId();
	}

	@Override
	public Set<Genome> getGenome() {
		return originalNode.getGenome();
	}

	@Override
	public Set<Label> getLabels() {
		if (labels == null) {
			labels = new HashSet<>();
			for (DataNode node : dataNodes) {
				labels.addAll(node.getLabelList());
			}
		}
		return labels;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, Wrapper container) {
		wrapperOperation.calculate(this, container);
	}

	@Override
	public void collectDataNodes(Set<DataNode> set) {
		set.addAll(dataNodes);
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
