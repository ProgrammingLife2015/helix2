package tudelft.ti2806.pl3.visualization.wrapper.operation;

import tudelft.ti2806.pl3.visualization.wrapper.*;

/**
 * A {@link WrapperOperation} is used to when we want to handle different
 * implementations of {@link NodeWrapper} in a different way without identifying
 * and casting the objects.
 * 
 * <p>
 * Examples of usage:
 * <ul>
 * <li>Calculating which {@link NodeWrapper} should collapse.
 * <li>Calculating the order of the nodes within a {@link CombineWrapper}.
 * <li>Calculating the y position of a {@link NodeWrapper}.
 * </ul>
 * 
 * @author Sam Smulders
 *
 */
public abstract class WrapperOperation {
	
	/**
	 * This method makes the node call back to prevent identifying the class
	 * type and to prevent casting.<br>
	 * This method should most likely be left untouched.
	 * 
	 * @param node
	 *            the node which is called to make a call back
	 * @param container
	 *            the wrapper containing this node
	 */
	public void calculate(NodeWrapper node, NodeWrapper container) {
		node.calculate(this, container);
	}
	
	/**
	 * Executes the operation for {@link HorizontalWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	public void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	public void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link SpaceWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for each
	 * {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	public void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link SingleWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(NodeWrapper)} for the
	 * {@link NodeWrapper} within this {@link SingleWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	public void calculate(SingleWrapper wrapper, NodeWrapper container) {
		calculate(wrapper.getNode(), wrapper);
	}
	
	/**
	 * Executes the operation for {@link NodePosition}.
	 * 
	 * <p>
	 * The public method is empty.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	public void calculate(NodePosition wrapper, NodeWrapper container) {
		
	}

	public void calculate(DataNodeWrapper dataNodeWrapper, NodeWrapper nodeWrapper){}
	public void calculate(PlaceholderWrapper placeholderWrapper, NodeWrapper nodeWrapper){}
}
