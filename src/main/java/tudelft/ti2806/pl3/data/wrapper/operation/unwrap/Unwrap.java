package tudelft.ti2806.pl3.data.wrapper.operation.unwrap;

import tudelft.ti2806.pl3.data.graph.DataNode;
import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;
import tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;
import tudelft.ti2806.pl3.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * A {@link WrapperOperation} that unwraps the wrapped root node.
 *
 * <p>
 * The algorithm unwraps the wrapped root node.
 * </p>
 * <p>
 * For every node that needs to be unwrapped, a placeholder is created in the
 * new graph. Then that placeholder, together with a reference to the node in
 * the original graph is put on a stack. The algorithm continues until the stack
 * is empty. When a new item is popped from the stack, it will be unwrapped
 * using one of the calculate methods.
 * </p>
 * <p>
 * When a node should not be unwrapped, a new {@link WrapperClone} is created
 * and all {@link DataNode}s that the node from the original graph contains, are
 * listed in the new {@link WrapperClone}.
 * </p>
 * <p>
 * In the end a new graph is constructed containing only {@link WrapperClone}s
 * </p>
 * Created by Boris Mattijssen on 18-05-15.
 */
public abstract class Unwrap extends WrapperOperation {
	
	private Wrapper result;
	private List<WrapperClone> wrapperClones;
	Stack<Pair<WrapperPlaceholder, Wrapper>> stack;
	
	/**
	 * Construct the Unwrap operation.
	 *
	 * <p>
	 * A Stack is constructed and the given start node is inserted. Then it
	 * keeps looping until the stack is empty. Each item of the stack will be
	 * evaluated using one of the calculate methods.
	 * </p>
	 * 
	 * @param start
	 *            The big wrapped node containing the entire graph
	 */
	public void compute(Wrapper start) {
		wrapperClones = new ArrayList<>();
		stack = new Stack<>();
		result = createNewNode(start);
		while (!stack.empty()) {
			Pair<WrapperPlaceholder, Wrapper> pair = stack.pop();
			pair.getSecond().calculate(this, pair.getFirst());
		}
		Wrapper.computeLongestPaths(new ArrayList<Wrapper>(wrapperClones));
		for (Wrapper wrapper : wrapperClones) {
			wrapper.calculateX();
		}
	}
	
	public Wrapper getResult() {
		return result;
	}
	
	public List<WrapperClone> getWrapperClones() {
		return wrapperClones;
	}
	
	/**
	 * Unwrap a {@link HorizontalWrapper}.
	 *
	 * <p>
	 * It looks for the incoming {@link Wrapper}s of the placeholder and
	 * connects these to the first {@link Wrapper} in the
	 * {@link HorizontalWrapper}
	 * </p>
	 * <p>
	 * Then it connects all internal (newly created) {@link Wrapper}s.
	 * </p>
	 * <p>
	 * Then it looks for the outgoing {@link Wrapper}s of the placeholder and
	 * connects these to the last {@link Wrapper} in the
	 * {@link HorizontalWrapper}
	 * </p>
	 * 
	 * @param node
	 *            The reference to the {@link HorizontalWrapper} that contains
	 *            the nodes to be unwrapped
	 * @param placeholder
	 *            The placeholder for this {@link HorizontalWrapper} in the
	 *            newly constructed graph
	 */
	@Override
	public void calculate(HorizontalWrapper node, Wrapper placeholder) {
		List<Wrapper> nodeWrapperList = node.getNodeList();
		Wrapper start = createNewNode(nodeWrapperList.get(0));
		for (Wrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if (placeholder == result) {
			result = start;
		}
		Wrapper curr = null;
		Wrapper prev = start;
		for (int i = 1; i < nodeWrapperList.size(); i++) {
			curr = createNewNode(nodeWrapperList.get(i));
			prev.getOutgoing().add(curr);
			curr.getIncoming().add(prev);
			prev = curr;
		}
		for (Wrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}
	
	/**
	 * Unwrap a {@link VerticalWrapper}.
	 *
	 * <p>
	 * It looks for the incoming {@link Wrapper}s of the placeholder and
	 * connects these to all the {@link Wrapper}s in the {@link VerticalWrapper}
	 * </p>
	 * <p>
	 * Then it looks for the outgoing {@link Wrapper}s of the placeholder and
	 * connects these to all the {@link Wrapper}s in the {@link VerticalWrapper}
	 * </p>
	 * 
	 * @param node
	 *            The reference to the {@link VerticalWrapper} that contains the
	 *            nodes to be unwrapped
	 * @param placeholder
	 *            The placeholder for this {@link VerticalWrapper} in the newly
	 *            constructed graph
	 */
	@Override
	public void calculate(VerticalWrapper node, Wrapper placeholder) {
		List<Wrapper> nodeWrapperList = node.getNodeList();
		for (int i = 0; i < nodeWrapperList.size(); i++) {
			Wrapper curr = createNewNode(nodeWrapperList.get(i));
			for (Wrapper incoming : placeholder.getIncoming()) {
				incoming.getOutgoing().remove(placeholder);
				incoming.getOutgoing().add(curr);
				curr.getIncoming().add(incoming);
			}
			for (Wrapper outgoing : placeholder.getOutgoing()) {
				outgoing.getIncoming().remove(placeholder);
				outgoing.getIncoming().add(curr);
				curr.getOutgoing().add(outgoing);
			}
		}
	}
	
	/**
	 * Unwrap a {@link SpaceWrapper}.
	 *
	 * <p>
	 * It looks for the incoming {@link Wrapper}s of the placeholder and
	 * connects these to the first {@link Wrapper} in the {@link SpaceWrapper}
	 * </p>
	 * <p>
	 * Then it connects all internal (newly created) {@link Wrapper}s. It
	 * connects all newly created {@link Wrapper}s in the same way as they were
	 * connected in the reference {@link SpaceWrapper}.
	 * </p>
	 * <p>
	 * Then it looks for the outgoing {@link Wrapper}s of the placeholder and
	 * connects these to the last {@link Wrapper} in the {@link SpaceWrapper}
	 * </p>
	 * 
	 * @param node
	 *            The reference to the {@link SpaceWrapper} that contains the
	 *            nodes to be unwrapped
	 * @param placeholder
	 *            The placeholder for this {@link SpaceWrapper} in the newly
	 *            constructed graph
	 */
	@Override
	public void calculate(SpaceWrapper node, Wrapper placeholder) {
		Map<Wrapper, Wrapper> referencePlaceholderMapper = new HashMap<>();
		List<Wrapper> nodeWrapperList = node.getNodeList();
		Wrapper start = createNewNode(nodeWrapperList.get(0));
		referencePlaceholderMapper.put(nodeWrapperList.get(0), start);
		for (Wrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(start);
			start.getIncoming().add(incoming);
		}
		if (placeholder == result) {
			result = start;
		}
		Wrapper curr = null;
		for (int i = 1; i < nodeWrapperList.size(); i++) {
			curr = createNewNode(nodeWrapperList.get(i));
			referencePlaceholderMapper.put(nodeWrapperList.get(i), curr);
		}
		for (Map.Entry<Wrapper, Wrapper> referencePlaceholderMap : referencePlaceholderMapper
				.entrySet()) {
			for (Wrapper outgoing : referencePlaceholderMap.getKey()
					.getOutgoing()) {
				// only add if outgoing is in this SpaceWrapper
				if (referencePlaceholderMapper.get(outgoing) != null) {
					referencePlaceholderMap.getValue().getOutgoing()
							.add(referencePlaceholderMapper.get(outgoing));
				}
			}
			for (Wrapper incoming : referencePlaceholderMap.getKey()
					.getIncoming()) {
				// only add if incoming is in this SpaceWrapper
				if (referencePlaceholderMapper.get(incoming) != null) {
					referencePlaceholderMap.getValue().getIncoming()
							.add(referencePlaceholderMapper.get(incoming));
				}
			}
		}
		for (Wrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(curr);
			curr.getOutgoing().add(outgoing);
		}
	}
	
	/**
	 * Unwrap a {@link SingleWrapper}.
	 *
	 * <p>
	 * It creates a node from the node its containing.
	 * </p>
	 * <p>
	 * Then it connects all the placeholder incoming and outgoing nodes to the
	 * newly created node's incoming and outgoing nodes.
	 * </p>
	 *
	 * @param node
	 *            The reference to the {@link SingleWrapper} that contains the
	 *            node to be unwrapped
	 * @param placeholder
	 *            The placeholder for this {@link SingleWrapper} in the newly
	 *            constructed graph
	 */
	@Override
	public void calculate(SingleWrapper node, Wrapper placeholder) {
		Wrapper newNode = createNewNode(node.getNode());
		for (Wrapper incoming : placeholder.getIncoming()) {
			incoming.getOutgoing().remove(placeholder);
			incoming.getOutgoing().add(newNode);
			newNode.getIncoming().add(incoming);
		}
		if (placeholder == result) {
			result = newNode;
		}
		for (Wrapper outgoing : placeholder.getOutgoing()) {
			outgoing.getIncoming().remove(placeholder);
			outgoing.getIncoming().add(newNode);
			newNode.getOutgoing().add(outgoing);
		}
	}
	
	/**
	 * This method creates a new node that will be part of the newly created
	 * graph.
	 *
	 * <p>
	 * It checks to see if the node is a {@link CombineWrapper} and should be
	 * unwrapped. If so, a placeholder will be created and returned and the
	 * placeholder together with a reference to the {@link CombineWrapper} will
	 * be put on the stack. This placeholder is necessary to connect it to the
	 * correct nodes in the graph we're constructing. The reference is passed to
	 * unwrap its nodes in the next calculate.
	 * </p>
	 * <p>
	 * If the {@link Wrapper} shouldn't be unwrapped, a {@link WrapperClone} is
	 * returned. This node contains all the {@link DataNode}s that are wrapped
	 * in this node.
	 * </p>
	 * 
	 * @param node
	 *            The {@link Wrapper} from the original graph.
	 * @return The new node to be used in the new graph we're constructing. It's
	 *         either a {@link WrapperPlaceholder} or a {@link WrapperClone}.
	 */
	private Wrapper createNewNode(Wrapper node) {
		if (node instanceof CombineWrapper
				&& isConditionMet((CombineWrapper) node)
				|| node instanceof SingleWrapper) {
			WrapperPlaceholder placeholder = new WrapperPlaceholder();
			stack.add(new Pair<>(placeholder, node));
			return placeholder;
		} else {
			WrapperClone wrapperClone = new WrapperClone(node.getDataNodes(),
					node);
			wrapperClone.setY(node.getY());
			wrapperClones.add(wrapperClone);
			return wrapperClone;
		}
	}
	
	protected abstract boolean isConditionMet(CombineWrapper node);
}
