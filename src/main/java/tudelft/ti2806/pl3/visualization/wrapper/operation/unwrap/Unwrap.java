package tudelft.ti2806.pl3.visualization.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.data.graph.node.DataNode;
import tudelft.ti2806.pl3.util.Pair;
import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.PlaceholderWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A {@link WrapperOperation} that unwraps the wrapped root node.
 *
 * <p>The algorithm unwraps the wrapped root node.</p>
 * <p>For every node that needs to be unwrapped, a placeholder is created in the
 * new graph. Then that placeholder, together with a reference to the node in the original
 * graph is put on a stack. The algorithm continues until the stack is empty. When a new
 * item is popped from the stack, it will be unwrapped using one of the calculate methods.</p>
 * <p>When a node should not be unwrapped, a new {@link DataNodeWrapper} is created and
 * all {@link DataNode}s that the node from the original graph contains, are listed in the
 * new {@link DataNodeWrapper}.</p>
 * <p>In the end a new graph is constructed containing only {@link DataNodeWrapper}s</p>
 * Created by Boris Mattijssen on 18-05-15.
 */
public class Unwrap extends WrapperOperation {

	private NodeWrapper result;
	private List<DataNodeWrapper> dataNodeWrappers;
	Stack<Pair<PlaceholderWrapper, NodeWrapper>> stack;

	/**
	 * Construct the Unwrap operation.
	 *
	 * <p>A Stack is constructed and the given start node is inserted.
	 * Then it keeps looping until the stack is empty. Each item of the stack will
	 * be evaluated using one of the calculate methods.</p>
	 * @param start
	 *          The big wrapped node containing the entire graph
	 */
	public Unwrap(NodeWrapper start) {
		dataNodeWrappers = new ArrayList<>();
		stack = new Stack<>();
		result = createNewNode(start);
		while (!stack.empty()) {
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

	/**
	 * Unwrap a {@link HorizontalWrapper}.
	 *
	 * <p>It looks for the incoming {@link NodeWrapper}s of the placeholder
	 * and connects these to the first {@link NodeWrapper} in the {@link HorizontalWrapper}</p>
	 * <p>Then it connects all internal (newly created) {@link NodeWrapper}s.</p>
	 * <p>Then it looks for the outgoing {@link NodeWrapper}s of the placeholder
	 * and connects these to the last {@link NodeWrapper} in the {@link HorizontalWrapper}</p>
	 * @param node
	 *          The reference to the {@link HorizontalWrapper} that contains the nodes to be unwrapped
	 * @param placeholder
	 *          The placeholder for this {@link HorizontalWrapper} in the newly constructed graph
	 */
	public void calculate(HorizontalWrapper node, NodeWrapper placeholder) {
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		NodeWrapper start = createNewNode(nodeWrapperList.get(0));
		for (NodeWrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if (placeholder == result) {
			result = start;
		}
		NodeWrapper curr = null;
		NodeWrapper prev = start;
		for (int i = 1; i < nodeWrapperList.size(); i++) {
			curr = createNewNode(nodeWrapperList.get(i));
			prev.getOutgoing().add(curr);
			curr.getIncoming().add(prev);
			prev = curr;
		}
		for (NodeWrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}

	/**
	 * Unwrap a {@link VerticalWrapper}.
	 *
	 * <p>It looks for the incoming {@link NodeWrapper}s of the placeholder
	 * and connects these to all the {@link NodeWrapper}s in the {@link VerticalWrapper}</p>
	 * <p>Then it looks for the outgoing {@link NodeWrapper}s of the placeholder
	 * and connects these to all the {@link NodeWrapper}s in the {@link VerticalWrapper}</p>
	 * @param node
	 *          The reference to the {@link VerticalWrapper} that contains the nodes to be unwrapped
	 * @param placeholder
	 *          The placeholder for this {@link VerticalWrapper} in the newly constructed graph
	 */
	public void calculate(VerticalWrapper node, NodeWrapper placeholder) {
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		for (int i = 0; i < nodeWrapperList.size(); i++) {
			NodeWrapper curr = createNewNode(nodeWrapperList.get(i));
			for (NodeWrapper incoming : placeholder.getIncoming()) {
				incoming.getOutgoing().remove(placeholder);
				incoming.getOutgoing().add(curr);
				curr.getIncoming().add(incoming);
			}
			for (NodeWrapper outgoing : placeholder.getOutgoing()) {
				outgoing.getIncoming().remove(placeholder);
				outgoing.getIncoming().add(curr);
				curr.getOutgoing().add(outgoing);
			}
		}
	}

	/**
	 * Unwrap a {@link SpaceWrapper}.
	 *
	 * <p>It looks for the incoming {@link NodeWrapper}s of the placeholder
	 * and connects these to the first {@link NodeWrapper} in the {@link SpaceWrapper}</p>
	 * <p>Then it connects all internal (newly created) {@link NodeWrapper}s.
	 * It connects all newly created {@link NodeWrapper}s in the same way as they were
	 * connected in the reference {@link SpaceWrapper}.</p>
	 * <p>Then it looks for the outgoing {@link NodeWrapper}s of the placeholder
	 * and connects these to the last {@link NodeWrapper} in the {@link SpaceWrapper}</p>
	 * @param node
	 *          The reference to the {@link SpaceWrapper} that contains the nodes to be unwrapped
	 * @param placeholder
	 *          The placeholder for this {@link SpaceWrapper} in the newly constructed graph
	 */
	public void calculate(SpaceWrapper node, NodeWrapper placeholder) {
		Map<NodeWrapper, NodeWrapper> referencePlaceholderMapper = new HashMap<>();
		List<NodeWrapper> nodeWrapperList = node.getNodeList();
		NodeWrapper start = createNewNode(nodeWrapperList.get(0));
		referencePlaceholderMapper.put(nodeWrapperList.get(0), start);
		for (NodeWrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if (placeholder == result) {
			result = start;
		}
		NodeWrapper curr = null;
		for (int i = 1; i < nodeWrapperList.size(); i++) {
			curr = createNewNode(nodeWrapperList.get(i));
			referencePlaceholderMapper.put(nodeWrapperList.get(i), curr);
		}
		for (Map.Entry<NodeWrapper, NodeWrapper> referencePlaceholderMap :
				    referencePlaceholderMapper.entrySet()) {
			for (NodeWrapper outgoing : referencePlaceholderMap.getKey().getOutgoing()) {
				referencePlaceholderMap.getValue().getOutgoing().add(
						referencePlaceholderMapper.get(outgoing));
			}
			for (NodeWrapper incoming : referencePlaceholderMap.getKey().getIncoming()) {
				referencePlaceholderMap.getValue().getIncoming().add(
						referencePlaceholderMapper.get(incoming));
			}
		}
		for (NodeWrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}

	/**
	 * This method creates a new node that will be part of the newly created graph.
	 *
	 * <p>It checks to see if the node is a {@link CombineWrapper} and should be unwrapped.
	 * If so, a placeholder will be created and returned and the placeholder together with
	 * a reference to the {@link CombineWrapper} will be put on the stack. This placeholder
	 * is necessary to connect it to the correct nodes in the graph we're constructing. The
	 * reference is passed to unwrap its nodes in the next calculate.</p>
	 * <p>If the {@link NodeWrapper} shouldn't be unwrapped, a DataNodeWrapper is returned.
	 * This node contains all the {@link DataNode}s that are wrapped in this node.</p>
	 * @param node
	 *          The {@link NodeWrapper} from the original graph.
	 * @return
	 *          The new node to be used in the new graph we're constructing. It's either a
	 *          {@link PlaceholderWrapper} or a {@link DataNodeWrapper}.
	 */
	private NodeWrapper createNewNode(NodeWrapper node) {
		if (node instanceof CombineWrapper && ((CombineWrapper) node).isCollapsed()) {
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
