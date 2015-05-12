package tudelft.ti2806.pl3.data.graph.node;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mathieu Post on 30-4-15.
 * 
 */
public class CombinedNode implements Node {
	private List<Node> nodeList;
	
	/**
	 * An collection of {@link SNodes} which can be used as a single SNode.
	 * 
	 * @param edgeList
	 *            a connected and sorted list of edges.
	 */
	public CombinedNode(List<Edge> edgeList) {
		this.nodeList = new ArrayList<>(edgeList.size());
		
		this.nodeList.add(edgeList.get(0).getFrom());
		for (int i = 0; i < edgeList.size(); i++) {
			this.nodeList.add(edgeList.get(i).getTo());
		}
	}
	
	public Node getFirst() {
		return nodeList.get(0);
	}
	
	public Node getLast() {
		return nodeList.get(nodeList.size() - 1);
	}
	
	public List<Node> getNodeList() {
		return nodeList;
	}
	
	@Override
	public int getId() {
		return getFirst().getId();
	}
	
	@Override
	public Genome[] getSource() {
		return getFirst().getSource();
	}
	
	@Override
	public int getRefStartPoint() {
		return getFirst().getRefStartPoint();
	}
	
	@Override
	public int getRefEndPoint() {
		return getLast().getRefEndPoint();
	}
	
	// @Override
	// public byte[] getContent() {
	// byte[] res = new byte[0];
	// for (Node node : nodeList) {
	// byte[] next = node.getContent();
	// int resLen = res.length;
	// int nextLen = next.length;
	// byte[] geneArray = new byte[resLen + nextLen];
	// System.arraycopy(res, 0, geneArray, 0, resLen);
	// System.arraycopy(node.getContent(), 0, geneArray, resLen, nextLen);
	// res = geneArray;
	// }
	// return res;
	// }
	
	@Override
	public String toString() {
		return "CombinedNode [nodeId=" + getId() + ", source="
				+ Arrays.toString(getSource()) + ", refStartPoint="
				+ getRefStartPoint() + ", refEndPoint=" + getRefEndPoint()
				+ "]";
	}
	
	@Override
	public long getWidth() {
		long max = 0;
		for (Node node : nodeList) {
			max = Math.max(node.getWidth(), max);
		}
		return max;
	}
}
