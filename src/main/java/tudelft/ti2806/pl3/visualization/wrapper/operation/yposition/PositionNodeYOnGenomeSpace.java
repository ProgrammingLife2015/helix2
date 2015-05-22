package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Computes the position of the nodes on the y axis, based on their space in
 * their wrapped node. The space of a node is equal to the number of different
 * genome it contains.
 * 
 * @author Sam Smulders
 *
 */
public class PositionNodeYOnGenomeSpace extends WrapperOperation {
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		List<NodeWrapper> nodeList = wrapper.getNodeList();
		nodeList.get(0).setY(wrapper.getY());
		for (int i = 0; i < nodeList.size() - 1; i++) {
			NodeWrapper from = nodeList.get(i);
			List<NodeWrapper> sortedOutgoing = new ArrayList<>(
					from.getOutgoing());
			
			Collections.sort(sortedOutgoing);
			Set<Genome> set = new HashSet<>();
			Map<NodeWrapper, Integer> magicMap = new HashMap<>();
			for (NodeWrapper to : sortedOutgoing) {
				Set<Genome> toGenome = new HashSet<>(to.getGenome());
				toGenome.retainAll(from.getGenome());
				int size = -set.size();
				set.addAll(toGenome);
				magicMap.put(to, size + set.size());
			}
			float share = -from.getGenome().size() / 2f + from.getY();
			for (NodeWrapper to : from.getOutgoing()) {
				float size = magicMap.get(to);
				to.setY(to.getY() + (share + size / 2)
						* (size / to.getGenome().size()));
				share += size;
			}
		}
		for (int i = 0; i < nodeList.size() - 1; i++) {
			NodeWrapper node = nodeList.get(i);
			node.setY(node.getY() / wrapper.getGenome().size());
		}
		nodeList.get(nodeList.size() - 1).setY(nodeList.get(0).getY());
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		int wrapperGenomeSize = wrapper.getGenome().size();
		float space = wrapper.getY() - wrapperGenomeSize / 2f;
		for (NodeWrapper node : wrapper.getNodeList()) {
			int nodeSize = node.getGenome().size();
			node.setY(space + nodeSize / 2f);
			space += nodeSize;
		}
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			node.setY(wrapper.getY());
		}
		super.calculate(wrapper, container);
	}
	
	@Override
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		wrapper.getNode().setY(wrapper.getY());
		super.calculate(wrapper, container);
	}
}
