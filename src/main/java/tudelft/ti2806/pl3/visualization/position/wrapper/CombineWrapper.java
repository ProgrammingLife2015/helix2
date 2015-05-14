package tudelft.ti2806.pl3.visualization.position.wrapper;

import java.util.List;

public abstract class CombineWrapper extends NodePositionWrapper {
	protected List<NodePositionWrapper> nodeList;
	
	public CombineWrapper(List<NodePositionWrapper> nodeList) {
		this.nodeList = nodeList;
	}
	
	public NodePositionWrapper getFirst() {
		return nodeList.get(0);
	}
	
	public NodePositionWrapper getLast() {
		return nodeList.get(nodeList.size() - 1);
	}
	
	public List<NodePositionWrapper> getNodeList() {
		return nodeList;
	}
	
	@Override
	public String getIdString() {
		String str = "{";
		for (NodePositionWrapper node : nodeList) {
			str += "[" + node.getIdString() + "]";
		}
		return str + "}";
	}
}
