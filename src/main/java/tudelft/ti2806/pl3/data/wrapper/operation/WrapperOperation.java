package tudelft.ti2806.pl3.data.wrapper.operation;

import tudelft.ti2806.pl3.data.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.data.wrapper.DataNodeWrapper;
import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.data.wrapper.WrapperPlaceholder;

/**
 * A {@link WrapperOperation} is used to when we want to handle different
 * implementations of {@link Wrapper} in a different way without identifying and
 * casting the objects.
 * 
 * <p>
 * Examples of usage:
 * <ul>
 * <li>Calculating which {@link Wrapper} should collapse.
 * <li>Calculating the order of the nodes within a {@link CombineWrapper}.
 * <li>Calculating the y position of a {@link Wrapper}.
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
	public void calculate(Wrapper node, Wrapper container) {
		node.calculate(this, container);
	}
	
	/**
	 * Executes the operation for {@link HorizontalWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(Wrapper)} for each
	 * {@link Wrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	@SuppressWarnings("unused")
	public void calculate(HorizontalWrapper wrapper, Wrapper container) {
		for (Wrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(Wrapper)} for each
	 * {@link Wrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	@SuppressWarnings("unused")
	public void calculate(VerticalWrapper wrapper, Wrapper container) {
		for (Wrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link SpaceWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(Wrapper)} for each
	 * {@link Wrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	@SuppressWarnings("unused")
	public void calculate(SpaceWrapper wrapper, Wrapper container) {
		for (Wrapper node : wrapper.getNodeList()) {
			calculate(node, wrapper);
		}
	}
	
	/**
	 * Executes the operation for {@link SingleWrapper}.
	 * 
	 * <p>
	 * The public method just calls the {@link #calculate(Wrapper)} for the
	 * {@link Wrapper} within this {@link SingleWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	@SuppressWarnings("unused")
	public void calculate(SingleWrapper wrapper, Wrapper container) {
		calculate(wrapper.getNode(), wrapper);
	}
	
	/**
	 * Executes the operation for {@link DataNodeWrapper}.
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
	@SuppressWarnings("unused")
	public void calculate(DataNodeWrapper wrapper, Wrapper container) {
		
	}
	
	/**
	 * Executes the operation for {@link FixWrapper}.
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
	@SuppressWarnings("unused")
	public void calculate(FixWrapper wrapper, Wrapper container) {
		
	}
	
	@SuppressWarnings("unused")
	public void calculate(WrapperClone wrapperClone, Wrapper nodeWrapper) {
	}
	
	@SuppressWarnings("unused")
	public void calculate(WrapperPlaceholder placeholderWrapper,
			Wrapper nodeWrapper) {
	}
}
