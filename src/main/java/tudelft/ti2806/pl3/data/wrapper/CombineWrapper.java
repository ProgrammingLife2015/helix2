package tudelft.ti2806.pl3.data.wrapper;

import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.List;

public abstract class CombineWrapper extends Wrapper {
	protected List<Wrapper> nodeList;
	/**
	 * True if the wrapper its containing nodes should not be shown.
	 */
	private boolean shouldUnfold;
	
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
	 * @param shouldUnfold
	 *            indication whether this node should be unfolded or not
	 */
	public CombineWrapper(List<Wrapper> nodeList, boolean shouldUnfold) {
		this.nodeList = nodeList;
		this.shouldUnfold = shouldUnfold;
	}

	/**
	 * Construct and set shouldUnfold to false.
	 * @see {@link CombineWrapper}(List<{@link Wrapper}>, boolean)
	 * @param nodeList
	 *          a list of nodes
	 */
	public CombineWrapper(List<Wrapper> nodeList) {
		this.nodeList = nodeList;
		this.shouldUnfold = false;
	}
	
	public Wrapper getFirst() {
		return nodeList.get(0);
	}
	
	public Wrapper getLast() {
		return nodeList.get(nodeList.size() - 1);
	}
	
	public List<Wrapper> getNodeList() {
		return nodeList;
	}
	
	public boolean shouldUnfold() {
		return shouldUnfold;
	}
	
	public void setShouldUnfold(boolean collapsed) {
		this.shouldUnfold = collapsed;
	}
	
	@Override
	public String getIdString() {
		StringBuilder str = new StringBuilder("{");
		for (Wrapper node : nodeList) {
			str.append("[" + node.getIdString() + "]");
		}
		return str.toString() + "}";
	}

	@Override
	public int getId() {
		return nodeList.get(0).getId();
	}

	@Override
	public void collectDataNodes(List<DataNode> list) {
		nodeList.forEach(n -> n.collectDataNodes(list));
	}
	
}
