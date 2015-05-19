package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.PlaceholderWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.List;
import java.util.Stack;

/**
 * Created by Boris Mattijssen on 18-05-15.
 */
public class Unwrap extends WrapperOperation {

	private NodeWrapper result;
	Stack<Pair<PlaceholderWrapper, NodeWrapper>> stack;

	public Unwrap(HorizontalWrapper start) {
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

	public void calculate(NodeWrapper node, NodeWrapper container) {

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

	private NodeWrapper stackAdd(NodeWrapper node) {
		if(node instanceof CombineWrapper && ((CombineWrapper) node).isCollapsed()) {
			PlaceholderWrapper placeholder = new PlaceholderWrapper();
			stack.add(new Pair<>(placeholder, node));
			return placeholder;
		} else {
			return new DataNodeWrapper(node.getDataNodes());
		}
	}


}
