package tudelft.ti2806.pl3.data.wrapper.util;

import tudelft.ti2806.pl3.data.wrapper.FixWrapper;
import tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;

import java.util.List;

/**
 * An utility class to collapse graphs into smaller graphs.
 * 
 * @author Sam Smulders
 */
public final class WrapUtil {
	private WrapUtil() {
	}
	
	/**
	 * Collapses a graph until it converges to a single {@link Wrapper}.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found.
	 */
	public static WrappedGraphData collapseGraph(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = HorizontalWrapUtil.collapseGraph(original, false);
		if (graph == null) {
			graph = lastGraph;
		}
		graph = collapseGraphSpacial(graph);
		if (graph.getPositionedNodes().size() > 1) {
			return applyFixNode(graph);
		}
		return graph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link SpaceWrapper}s, {@link HorizontalWrapper}s or {@link VerticalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using vertical,
	 *         horizontal and spatial options.
	 */
	static WrappedGraphData collapseGraphSpacial(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = collapseGraphVertical(graph);
			graph = SpaceWrapUtil.collapseGraph(lastGraph);
		}
		return lastGraph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link HorizontalWrapper}s or {@link VerticalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using vertical and
	 *         horizontal options.
	 */
	private static WrappedGraphData collapseGraphVertical(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = collapseGraphHorizontal(graph);
			graph = VerticalWrapUtil.collapseGraph(lastGraph);
		}
		return lastGraph;
	}
	
	/**
	 * Collapses a graph until it converges to a set of {@link Wrapper}s which can't be wrapped with
	 * {@link HorizontalWrapper}s.
	 * 
	 * @param original
	 *            the original graph to collapse, which will be left unchanged
	 * @return A {@link WrappedGraphData} instance with the most collapsed graph found, with only using horizontal
	 *         options.
	 */
	static WrappedGraphData collapseGraphHorizontal(WrappedGraphData original) {
		WrappedGraphData lastGraph = original;
		WrappedGraphData graph = original;
		while (graph != null) {
			lastGraph = graph;
			graph = HorizontalWrapUtil.collapseGraph(lastGraph, true);
		}
		return lastGraph;
	}
	
	/**
	 * Adds a node to the end and start of the graph, connecting to all genome endings on the graph, to make a last
	 * {@link SpaceWrapper} by the {@link SpaceWrapUtil} possible. And by that fixing the graph, making it possible 
	 * to wrap the graph to one single node.
	 * 
	 * @param graph
	 *            the graph to fix
	 * @return a fixed graph
	 */
	public static WrappedGraphData applyFixNode(WrappedGraphData graph) {
		List<Wrapper> nodes = graph.getPositionedNodes();

		FixWrapper startFix = new FixWrapper(-1);
		FixWrapper endFix = new FixWrapper(-2);
		
		FixWrapUtil.addFixNodesToGraph(nodes, startFix, endFix);
		
		WrappedGraphData wrappedGraph = collapseGraphSpacial(new WrappedGraphData(nodes, graph.getGenomeSize()));
		
		startFix.setX(-1);
		endFix.setX(wrappedGraph.getPositionedNodes().get(0).getWidth() + 1);
		
		return wrappedGraph;
	}
}
