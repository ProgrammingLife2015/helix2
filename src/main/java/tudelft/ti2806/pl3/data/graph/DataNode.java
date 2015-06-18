package tudelft.ti2806.pl3.data.graph;

import tudelft.ti2806.pl3.data.BasePair;
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
	protected final int nodeId;
	protected final Set<Genome> source;
	protected Set<Genome> currentGenomeSet;
	protected final int refStartPoint;
	protected final int refEndPoint;
	protected final String content;
	
	protected List<Label> labelList;
	private int nCounter;

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
	 * @param labelList
	 *            the list of labels to add
	 */
	public DataNode(int nodeId, Set<Genome> source, int refStartPoint,
			int refEndPoint, String contentOfTheNode, List<Label> labelList) {
		this.nodeId = nodeId;
		this.source = new HashSet<>(source);
		this.currentGenomeSet = new HashSet<>(source);
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		this.content = contentOfTheNode;
		if (labelList == null) {
			this.labelList = new ArrayList<>();
		} else {
			this.labelList = labelList;
		}

		nCounter = 0;
		char n = BasePair.N.name().charAt(0);
		for (int i = 0; i < content.length(); i++ ) {
			if (content.charAt(i) == n) {
				nCounter++;
			}
		}
	}
	
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
		this(nodeId, source, refStartPoint, refEndPoint, contentOfTheNode, null);
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
		return nodeId;
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

	public long getNCount() {
		return nCounter;
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
