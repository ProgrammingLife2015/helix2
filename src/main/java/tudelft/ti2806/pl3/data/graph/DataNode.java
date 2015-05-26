package tudelft.ti2806.pl3.data.graph;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.data.Genome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The SingleNode is a node parsed from the original data. No changes should or
 * can be made after initialising.
 * 
 * @author Sam Smulders
 *
 */
public class DataNode {
	protected final int nodeId;
	protected final Set<Genome> source;
	protected List<Genome> currentGenomeList;
	protected final int refStartPoint;
	protected final int refEndPoint;
	protected final byte[] content;
	
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
	public DataNode(int nodeId, Set<Genome> source, int refStartPoint,
			int refEndPoint, byte[] contentOfTheNode) {
		this.nodeId = nodeId;
		if (source == null) {
			// TODO: Bad data, throw exception
			this.source = null;
		} else {
			this.source = new HashSet<>(source);
		}
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		this.currentGenomeList = new ArrayList<>();
		if (contentOfTheNode == null) {
			this.content = null;
		} else {
			this.content = contentOfTheNode.clone();
		}
	}
	
	@Override
	public String toString() {
		return "SingleNode [nodeId=" + nodeId + ", source="
				+ source + ", currentGenomeList=" +  currentGenomeList + ", "
				+ "refStartPoint=" + refStartPoint
				+ ", refEndPoint=" + refEndPoint + ", content.lenght="
				+ content.length + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + nodeId;
		result = prime * result + refEndPoint;
		result = prime * result + refStartPoint;
		result = prime * result + source.hashCode();
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
		DataNode other = (DataNode) obj;
		if (nodeId != other.nodeId) {
			return false;
		}
		if (!Arrays.equals(content, other.content)) {
			return false;
		}
		if (refEndPoint != other.refEndPoint) {
			return false;
		}
		if (refStartPoint != other.refStartPoint) {
			return false;
		}
		if (!source.equals(other.source)) {
			return false;
		}
		return true;
	}
	
	public int getId() {
		return nodeId;
	}
	
	// Suppressed in the interest of space and time
	@SuppressFBWarnings({ "EI_EXPOSE_REP" })
	public Set<Genome> getSource() {
		return source;
	}
	
	public int getRefStartPoint() {
		return refStartPoint;
	}
	
	public int getRefEndPoint() {
		return refEndPoint;
	}
	
	public long getWidth() {
		return content.length;
	}

	public List<Genome> getCurrentGenomeList() {
		return currentGenomeList;
	}

	public void setCurrentGenomeList(List<Genome> currentGenomeList) {
		this.currentGenomeList = currentGenomeList;
	}
}
