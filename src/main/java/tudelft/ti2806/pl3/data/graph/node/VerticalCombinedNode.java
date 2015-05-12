package tudelft.ti2806.pl3.data.graph.node;

import tudelft.ti2806.pl3.data.Genome;

import java.util.ArrayList;
import java.util.List;

public class VerticalCombinedNode implements Node {
	List<Node> nodeList;
	
	public VerticalCombinedNode(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	
	private Node getFirst() {
		return nodeList.get(0);
	}
	
	@Override
	public int getId() {
		return getFirst().getId();
	}
	
	@Override
	public Genome[] getSource() {
		List<Genome> genomeList = new ArrayList<Genome>();
		for (Node node : nodeList) {
			for (Genome genome : node.getSource()) {
				genomeList.add(genome);
			}
		}
		return genomeList.toArray(new Genome[genomeList.size()]);
	}
	
	@Override
	public int getRefStartPoint() {
		int min = Integer.MAX_VALUE;
		for (Node node : nodeList) {
			min = Math.min(min, node.getRefStartPoint());
		}
		return min;
	}
	
	@Override
	public int getRefEndPoint() {
		int max = Integer.MIN_VALUE;
		for (Node node : nodeList) {
			max = Math.max(max, node.getRefEndPoint());
		}
		return max;
	}
	
	@Override
	public long getWidth() {
		long max = Integer.MIN_VALUE;
		for (Node node : nodeList) {
			max = Math.max(max, node.getWidth());
		}
		return max;
	}
	
}
