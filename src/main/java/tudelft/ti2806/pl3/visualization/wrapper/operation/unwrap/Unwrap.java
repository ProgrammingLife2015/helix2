package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.*;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.*;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class Unwrap extends WrapperOperation {

	private NodeWrapper result;
	private List<DataNodeWrapper> dataNodeWrappers;
	Stack<Pair<PlaceholderWrapper, NodeWrapper>> stack;

	public Unwrap(HorizontalWrapper start) {
		dataNodeWrappers = new ArrayList<>();
		stack = new Stack<>();
		result = stackAdd(start);
		while(!stack.empty()) {
			Pair<PlaceholderWrapper, NodeWrapper> pair = stack.pop();
			pair.getSecond().calculate(this, pair.getFirst());
		}
	}

	public NodeWrapper getResult() {
		return result;
	}

	public List<DataNodeWrapper> getDataNodeWrappers() {
		return dataNodeWrappers;
	}

	public void calculate(HorizontalWrapper node, NodeWrapper placeholder) {
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		NodeWrapper start = stackAdd(nodeWrapperList.get(0));
		for(NodeWrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if(placeholder == result) {
			result = start;
		}
		NodeWrapper curr = null;
		NodeWrapper prev = start;
		for(int i = 1; i < nodeWrapperList.size(); i++) {
			curr = stackAdd(nodeWrapperList.get(i));
			prev.getOutgoing().add(curr);
			curr.getIncoming().add(prev);
			prev = curr;
		}
		for(NodeWrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}

	public void calculate(VerticalWrapper node, NodeWrapper placeholder) {
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		for(int i = 0; i < nodeWrapperList.size(); i++) {
			NodeWrapper curr = stackAdd(nodeWrapperList.get(i));
			for(NodeWrapper incoming : placeholder.getIncoming()) {
				incoming.getOutgoing().remove(placeholder);
				incoming.getOutgoing().add(curr);
				curr.getIncoming().add(incoming);
			}
			for(NodeWrapper outgoing : placeholder.getOutgoing()) {
				outgoing.getIncoming().remove(placeholder);
				outgoing.getIncoming().add(curr);
				curr.getOutgoing().add(outgoing);
			}
		}
	}

	public void calculate(SpaceWrapper node, NodeWrapper placeholder) {
		Map<NodeWrapper, NodeWrapper> referencePlaceholderMapper = new HashMap<>();
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		NodeWrapper start = stackAdd(nodeWrapperList.get(0));
		referencePlaceholderMapper.put(nodeWrapperList.get(0), start);
		for(NodeWrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if(placeholder == result) {
			result = start;
		}
		NodeWrapper curr = null;
		for(int i = 1; i < nodeWrapperList.size(); i++) {
			curr = stackAdd(nodeWrapperList.get(i));
			referencePlaceholderMapper.put(nodeWrapperList.get(i), curr);
		}
		for(Map.Entry<NodeWrapper, NodeWrapper> referencePlaceholderMap :
				    referencePlaceholderMapper.entrySet()) {
			for(NodeWrapper outgoing : referencePlaceholderMap.getKey().getOutgoing()) {
				referencePlaceholderMap.getValue().getOutgoing().add(referencePlaceholderMapper.get(outgoing));
			}
		}
		for(NodeWrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}

	private NodeWrapper stackAdd(NodeWrapper node) {
		if(node instanceof CombineWrapper && ((CombineWrapper) node).isCollapsed()) {
			PlaceholderWrapper placeholder = new PlaceholderWrapper();
			stack.add(new Pair<>(placeholder, node));
			return placeholder;
		} else {
			DataNodeWrapper dataNodeWrapper = new DataNodeWrapper(node.getDataNodes());
			dataNodeWrapper.setY(node.getY());
			dataNodeWrappers.add(dataNodeWrapper);
			return dataNodeWrapper;
		}
	}


}
