package tudelft.ti2806.pl3.visualization.wrapper.operation.yposition;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MagicYaxis extends WrapperOperation {
	// TODO we are editing the y of the outgoing of the last node!
	@Override
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		List<NodeWrapper> nodeList = wrapper.getNodeList();
		nodeList.get(0).setY(wrapper.getY());
		for (int i = 0; i < nodeList.size(); i++) {
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
				int size = magicMap.get(to);
				to.setY(to.getY() + (share + size / 2f) * (size / to.getGenome().size()));
				share += size;
			}
		}
		for (int i = 0; i < nodeList.size(); i++) {
			NodeWrapper node = nodeList.get(i);
			node.setY(node.getY() / wrapper.getGenome().size());
		}
		super.calculate(wrapper, container);
	}
}
