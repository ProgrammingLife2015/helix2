package tudelft.ti2806.pl3.visualization.position.wrapper;

import java.util.List;

public abstract class CombineWrapper extends NodePositionWrapper {
	protected List<NodePositionWrapper> nodeList;
	
	/**
	 * CombineWrapper is an abstract class which should not be constructed
	 * directly.
	 * 
	 * <p>
	 * When a class extending this class is constructed, the given list should
	 * fullfill the following conditions. <br>
	 * Preconditions:<br>
	 * <ul>
	 * <li>The first node in the list should at least contain all incoming nodes
	 * which come from outside the graph.<br>
	 * <li>The last node in the list should at least contain all outgoing nodes
	 * which go outside the graph.<br>
	 * <li>All incoming nodes from outside the graph should never be an outgoing
	 * node within the list. And the outgoing nodes from outside the graph
	 * should never be an incoming node within the list.
	 * </ul>
	 * 
	 * @param nodeList
	 *            a list of nodes
	 */
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
