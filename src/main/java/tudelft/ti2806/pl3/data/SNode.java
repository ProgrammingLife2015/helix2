package tudelft.ti2806.pl3.data;

import java.util.Arrays;

public class SNode implements Node {
	protected int nodeId;
	protected String[] source;
	protected int refStartPoint;
	protected int refEndPoint;
	protected Gene[] content;
	
	// protected List<Node> incommingConnections = new ArrayList<Node>();
	// protected List<Node> outgoingConnections = new ArrayList<Node>();
	
	/**
	 * Initialise a {@code Node}.
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
	public SNode(int nodeId, String[] source, int refStartPoint,
			int refEndPoint, Gene[] contentOfTheNode) {
		this.nodeId = nodeId;
		this.source = source;
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		this.content = contentOfTheNode;
	}
	
	// public void addIncomming(Node from) {
	// incommingConnections.add(from);
	// }
	
	// public void addOutgoing(Node to) {
	// outgoingConnections.add(to);
	// }
	
	@Override
	public String toString() {
		return "SingleNode [nodeId=" + nodeId + ", source=" + Arrays.toString(source)
				+ ", refStartPoint=" + refStartPoint + ", refEndPoint="
				+ refEndPoint + ", content=" + Arrays.toString(content) + "]";
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
		SNode other = (SNode) obj;
		if (!Arrays.equals(content, other.content)) {
			return false;
		}
		if (nodeId != other.nodeId) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + nodeId;
		result = prime * result + refEndPoint;
		result = prime * result + refStartPoint;
		result = prime * result + Arrays.hashCode(source);
		return result;
	}

	@Override
	public int getNodeId() {
		return nodeId;
	}

	@Override
	public String[] getSource() {
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
	public Gene[] getContent() {
		return content;
	}
	
	// public List<Node> getIncommingConnections() {
	// return incommingConnections;
	// }
	//
	// public List<Node> getOutgoingConnections() {
	// return outgoingConnections;
	// }
	
}
