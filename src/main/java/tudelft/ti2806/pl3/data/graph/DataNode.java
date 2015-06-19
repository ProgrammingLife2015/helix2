package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.label.Label;

import java.util.ArrayList;
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
	private final int nodeId;
	private final Set<Genome> source;
	private Set<Genome> currentGenomeSet;
	private final int refStartPoint;
	private final int refEndPoint;
	private final String content;
	private final List<Label> labelList;
	
	/**
	 * Initialise a {@code SingleNode}.
	 *
	 * @param nodeId
	 *            the id of the node
	 * @param source
	 *            the names of the genomes where this piece is coming from
	 * @param refStartPoint
	 *            the start index on the genome
	 * @param refEndPoint
	 *            the end index on the genome
	 * @param contentOfTheNode
	 *            the size of this {@code Node}
	 */
	public DataNode(int nodeId, Set<Genome> source, int refStartPoint,
			int refEndPoint, String contentOfTheNode) {
		this.nodeId = nodeId;
		if (source == null) {
			// TODO: Bad data, throw exception
			this.source = null;
			this.currentGenomeSet = null;
		} else {
			this.source = new HashSet<>(source);
			this.currentGenomeSet = new HashSet<>(source);
		}
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		if (contentOfTheNode == null) {
			this.content = null;
		} else {
			this.content = contentOfTheNode;
		}
		this.labelList = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "SingleNode [nodeId=" + nodeId + ", source=" + source.toString()
				+ ", refStartPoint=" + refStartPoint + ", refEndPoint="
				+ refEndPoint + ", content.length=" + content.length()
				+ ", labelList.size=" + labelList.size() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + content.hashCode();
		result = prime * result + nodeId;
		result = prime * result + refEndPoint;
		result = prime * result + refStartPoint;
		result = prime * result + source.hashCode();
		result = prime * result + labelList.hashCode();
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
		if (!content.equals(other.content)) {
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
		if (!((DataNode) obj).labelList.equals(labelList)) {
			return false;
		}
		return true;
	}
	
	public void addLabel(Label label) {
		labelList.add(label);
	}
	
	public int getId() {
		return nodeId;
	}
	
	public Set<Genome> getSource() {
		return source;
	}
	
	public int getRefStartPoint() {
		return refStartPoint;
	}
	
	public int getRefEndPoint() {
		return refEndPoint;
	}
	
	public long getBasePairCount() {
		return content.length();
	}
	
	public Set<Genome> getCurrentGenomeSet() {
		return currentGenomeSet;
	}
	
	public void setCurrentGenomeList(Set<Genome> currentGenomeSet) {
		this.currentGenomeSet = currentGenomeSet;
	}
	
	public List<Label> getLabelList() {
		return labelList;
	}
	
	public String getContent() {
		return this.content;
	}
}
