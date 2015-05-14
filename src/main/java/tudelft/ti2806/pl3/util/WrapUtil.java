package tudelft.ti2806.pl3.util;

import tudelft.ti2806.pl3.visualization.position.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.NodePositionWrapper;
import tudelft.ti2806.pl3.visualization.position.wrapper.SingleWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WrapUtil {
	static List<NodePositionWrapper> wrapNodes(
			List<NodePositionWrapper> nonCombinedNodes,
			List<CombineWrapper> combinedNodes) {
		Map<NodePositionWrapper, NodePositionWrapper> map
				= new HashMap<NodePositionWrapper, NodePositionWrapper>();
		List<NodePositionWrapper> newLayer = new ArrayList<NodePositionWrapper>();
		for (NodePositionWrapper node : nonCombinedNodes) {
			SingleWrapper newWrapper = new SingleWrapper(node);
			newLayer.add(newWrapper);
			map.put(node, newWrapper);
		}
		for (CombineWrapper verNode : combinedNodes) {
			newLayer.add(verNode);
			for (NodePositionWrapper node : verNode.getNodeList()) {
				map.put(node, verNode);
			}
		}
		for (NodePositionWrapper node : nonCombinedNodes) {
			NodePositionWrapper newWrapper = map.get(node);
			for (NodePositionWrapper in : node.getIncoming()) {
				if (!newWrapper.getIncoming().contains(map.get(in))) {
					newWrapper.getIncoming().add(map.get(in));
				}
			}
			for (NodePositionWrapper out : node.getOutgoing()) {
				if (!newWrapper.getOutgoing().contains(map.get(out))) {
					newWrapper.getOutgoing().add(map.get(out));
				}
			}
		}
		for (CombineWrapper verNode : combinedNodes) {
			for (NodePositionWrapper in : verNode.getFirst().getIncoming()) {
				if (!verNode.getIncoming().contains(map.get(in))) {
					verNode.getIncoming().add(map.get(in));
				}
			}
			for (NodePositionWrapper out : verNode.getFirst().getOutgoing()) {
				if (!verNode.getOutgoing().contains(map.get(out))) {
					verNode.getOutgoing().add(map.get(out));
				}
			}
		}
		return newLayer;
	}
	
}
