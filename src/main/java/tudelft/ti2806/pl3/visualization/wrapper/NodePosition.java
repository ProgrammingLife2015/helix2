package tudelft.ti2806.pl3.visualization.wrapper;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.node.DataNodeInterface;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * NodePosition is used to store the position of nodes and the edges of nodes.
 * This responsibility is separated from {@link DataNodeInterface} so that the
 * same node can have different positions over different views without cloning
 * the expensive Node instances which contain the original data.
 * 
 * @author Sam Smulders
 *
 */
public class NodePosition extends NodeWrapper {
	private DataNodeInterface node;
	private long startX = -1;
	
	/**
	 * Construct a list with connected and fully initialised
	 * {@code NodePosition}s.
	 * 
	 * @param nodeList
	 *            the {@link List}<{@link DataNodeInterface}> of which the new
	 *            {@link List}< {@link NodePosition}> is constructed from
	 * @param edgeList
	 *            the {@link List}<{@link Edge}> with the connections between
	 *            the newly created {@link NodePosition}s
	 * @return a {@link List}<{@link NodePosition}>, constructed from the
	 *         {@code nodeList} and {@code edgeList}
	 */
	public static List<NodePosition> newNodePositionList(
			List<DataNodeInterface> nodeList, List<Edge> edgeList) {
		// Construct list
		Map<Integer, NodePosition> map = new HashMap<Integer, NodePosition>();
		for (DataNodeInterface node : nodeList) {
			map.put(node.getId(), new NodePosition(node));
		}
		
		// Add connections from the edge list
		for (Edge edge : edgeList) {
			NodePosition from = map.get(edge.getFromId());
			NodePosition to = map.get(edge.getToId());
			from.outgoing.add(to);
			to.incoming.add(from);
		}
		
		// Calculate the x positions and the number previous node count
		List<NodePosition> list = new ArrayList<NodePosition>(map.values());
		for (NodePosition node : list) {
			node.calculateStartX();
			node.calculatePreviousNodesCount();
		}
		return list;
	}
	
	/**
	 * Recursive method for calculating the axis start.
	 * 
	 * @return the calculated startX value.
	 */
	public long calculateStartX() {
		if (this.getXStart() != -1) {
			return this.getXStart();
		}
		long max = 0;
		for (NodeWrapper incomingNode : incoming) {
			max = Math.max(max, ((NodePosition) incomingNode).calculateStartX()
					+ incomingNode.getWidth());
		}
		this.startX = max;
		return max;
	}
	
	protected NodePosition(DataNodeInterface node) {
		this.node = node;
	}
	
	public DataNodeInterface getNode() {
		return node;
	}
	
	public long getXStart() {
		return startX;
	}
	
	public long getXEnd() {
		return startX + getWidth();
	}
	
	public long getWidth() {
		return node.getWidth();
	}
	
	@Override
	public String getIdString() {
		return node.getId() + "";
	}
	
	@Override
	public Set<Genome> getGenome() {
		Set<Genome> list = new HashSet<Genome>();
		for (Genome genome : node.getSource()) {
			list.add(genome);
		}
		return list;
	}

	@Override
	public void calculate(WrapperOperation wrapperOperation, NodeWrapper container) {
		wrapperOperation.calculate(this, container);
	}
}
