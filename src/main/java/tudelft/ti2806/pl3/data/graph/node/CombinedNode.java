package tudelft.ti2806.pl3.data.graph.node;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mathieu Post on 30-4-15.
 * 
 */
public class CombinedNode implements Node {
	private List<Node> nodeList;
	private int yaxisOrder = -1;
	
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
		
		calculateYaxisOrder();
	}
	
	/**
	 * Calculates the median of the list of all yaxisOrder values of the
	 * CombinedNode containing nodes.
	 * 
	 * @return the median yaxisOrder
	 */
	private void calculateYaxisOrder() {
		List<Integer> list = new ArrayList<Integer>(nodeList.size());
		for (Node node : nodeList) {
			list.add(node.getYaxisOrder());
		}
		Collections.sort(list);
		yaxisOrder = list.get(nodeList.size() / 2);
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
	
	@Override
	public byte[] getContent() {
		byte[] res = new byte[0];
		for (Node node : nodeList) {
			byte[] next = node.getContent();
			int resLen = res.length;
			int nextLen = next.length;
			byte[] geneArray = new byte[resLen + nextLen];
			System.arraycopy(res, 0, geneArray, 0, resLen);
			System.arraycopy(node.getContent(), 0, geneArray, resLen, nextLen);
			res = geneArray;
		}
		return res;
	}
	
	@Override
	public String toString() {
		return "CombinedNode [nodeId=" + getId() + ", source="
				+ Arrays.toString(getSource()) + ", refStartPoint="
				+ getRefStartPoint() + ", refEndPoint=" + getRefEndPoint()
				+ ", content=" + Arrays.toString(getContent()) + "]";
	}
	
	@Override
	public int getYaxisOrder() {
		return yaxisOrder;
	}
	
	@Override
	public long getXStart() {
		return getFirst().getXStart();
	}
	
	@Override
	public long getXEnd() {
		return getFirst().getXEnd();
	}
	
	@Override
	public long getWidth() {
		return getXEnd() - getXStart();
	}
	
	@Override
	public int getPreviousNodesCount() {
		return getFirst().getPreviousNodesCount();
	}
	
	@Override
	public List<SingleNode> getIncoming() {
		return getFirst().getIncoming();
	}
	
	@Override
	public List<SingleNode> getOutgoing() {
		return getLast().getOutgoing();
	}
	
	@Override
	public long calculateStartX() {
		return getFirst().calculateStartX();
	}
	
	@Override
	public int calculatePreviousNodesCount() {
		return getFirst().calculatePreviousNodesCount();
	}
}
