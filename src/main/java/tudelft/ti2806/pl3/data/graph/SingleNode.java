package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleNode implements Node {
	protected int nodeId;
	protected Genome[] source;
	protected int refStartPoint;
	protected int refEndPoint;
	protected byte[] content;
	
	/**
	 * A list of all nodes from incoming connections from this node.
	 * 
	 * @see getIncoming
	 */
	private List<Node> incoming = new ArrayList<Node>();
	
	/**
	 * {@link #incoming} is a list of all incoming connections from this node.
	 * {@link #incoming} should not be used in a filtered graph, because it may
	 * contain references to nodes which are not in the filtered graph.
	 * 
	 * <p>
	 * This field is not interesting for {@link CombinedNode}, because these
	 * nodes only appear after filtering of a graph.
	 */
	public List<Node> getIncoming() {
		return incoming;
	}
	
	/**
	 * A list of all nodes from outgoing connections from this node.
	 * 
	 * @see #getOutgoing
	 */
	private List<Node> outgoing = new ArrayList<Node>();
	
	/**
	 * {@link #outgoing} is a list of all outgoing connections from this node.
	 * {@link #outgoing} should not be used in a filtered graph, because it may
	 * contain references to nodes which are not in the filtered graph.
	 * 
	 * <p>
	 * This field is not interesting for {@link CombinedNode}, because these
	 * nodes only appear after filtering of a graph.
	 * 
	 * @return the list of all nodes from outgoing edges from this node
	 */
	public List<Node> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Initialise a {@code SingleNode}.
	 *
	 * @param nodeId
	 *            the id of the node
	 * @param source
	 *            the names of the genomes where this piece is comming from
	 * @param refStartPoint
	 *            the start index on the genome
	 * @param refEndPoint
	 *            the end index on the genome
	 * @param contentOfTheNode
	 *            the size of this {@code Node}
	 */
	public SingleNode(int nodeId, Genome[] source, int refStartPoint,
			int refEndPoint, byte[] contentOfTheNode) {
		this.nodeId = nodeId;
		this.source = source;
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		this.content = contentOfTheNode;
	}
	
	@Override
	public String toString() {
		return "SingleNode [nodeId=" + nodeId + ", source="
				+ Arrays.toString(source) + ", refStartPoint=" + refStartPoint
				+ ", refEndPoint=" + refEndPoint + ", content="
				+ Arrays.toString(content) + ", incoming=" + incoming
				+ ", outgoing=" + outgoing + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result
				+ ((incoming == null) ? 0 : incoming.hashCode());
		result = prime * result + nodeId;
		result = prime * result
				+ ((outgoing == null) ? 0 : outgoing.hashCode());
		result = prime * result + refEndPoint;
		result = prime * result + refStartPoint;
		result = prime * result + Arrays.hashCode(source);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SingleNode other = (SingleNode) obj;
		if (!Arrays.equals(content, other.content)) {
			return false;
		}
		if (incoming == null) {
			if (other.incoming != null) {
				return false;
			}
		} else if (!incoming.equals(other.incoming)) {
			return false;
		}
		if (nodeId != other.nodeId) {
			return false;
		}
		if (outgoing == null) {
			if (other.outgoing != null) {
				return false;
			}
		} else if (!outgoing.equals(other.outgoing)) {
			return false;
		}
		if (refEndPoint != other.refEndPoint) {
			return false;
		}
		if (refStartPoint != other.refStartPoint) {
			return false;
		}
		if (!Arrays.equals(source, other.source)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int getNodeId() {
		return nodeId;
	}
	
	@Override
	public Genome[] getSource() {
		return source;
	}
	
	@Override
	public int getRefStartPoint() {
		return refStartPoint;
	}
	
	@Override
	public int getRefEndPoint() {
		return refEndPoint;
	}
	
	@Override
	public byte[] getContent() {
		return content;
	}
	
}
