package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.graph.node.DataNode;

import java.util.List;

public abstract class CombineWrapper extends NodeWrapper {
	protected List<NodeWrapper> nodeList;
	/**
	 * True if the wrapper its containing nodes should not be shown.
	 */
	private boolean collapsed;
	
	/**
	 * CombineWrapper is an abstract class which should not be constructed
	 * directly.
	 * 
	 * <p>
	 * When a class extending this class is constructed, the given list should
	 * fullfill the following conditions. <br>
	 * Preconditions:<br>
	 * <ul>
	 * <li>The list should be sorted on previousNodesCount, with the smallest
	 * value first and the largest last.
	 * <li>All incoming nodes from outside the graph should never be an outgoing
	 * node within the list. And the outgoing nodes from outside the graph
	 * should never be an incoming node within the list.
	 * </ul>
	 * 
	 * @param nodeList
	 *            a list of nodes
	 * @param collapsed
	 *            indication whether this node should be collapsed or not
	 */
	public CombineWrapper(List<NodeWrapper> nodeList, boolean collapsed) {
		this.nodeList = nodeList;
		this.collapsed = collapsed;
	}

	/**
	 * Construct and set collapsed to false.
	 * @see {@link CombineWrapper(List<NodeWrapper>, boolean)}
	 * @param nodeList
	 *          a list of nodes
	 */
	public CombineWrapper(List<NodeWrapper> nodeList) {
		this.nodeList = nodeList;
		this.collapsed = false;
	}
	
	public NodeWrapper getFirst() {
		return nodeList.get(0);
	}
	
	public NodeWrapper getLast() {
		return nodeList.get(nodeList.size() - 1);
	}
	
	public List<NodeWrapper> getNodeList() {
		return nodeList;
	}
	
	public boolean isCollapsed() {
		return collapsed;
	}
	
	public void setCollapse(boolean collapsed) {
		this.collapsed = collapsed;
	}
	
	@Override
	public String getIdString() {
		String str = "{";
		for (NodeWrapper node : nodeList) {
			str += "[" + node.getIdString() + "]";
		}
		return str + "}";
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
		nodeList.forEach(n -> n.collectDataNodes(list));
	}
	
}
