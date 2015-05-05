package tudelft.ti2806.pl3.data.graph.positioning;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tudelft.ti2806.pl3.data.graph.Edge;
import tudelft.ti2806.pl3.data.graph.GraphData;
import tudelft.ti2806.pl3.data.graph.Node;
import tudelft.ti2806.pl3.data.graph.SingleNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetTest {
	private File nodesFile = new File("data/7TestNodes.node.graph");
	private File edgesFile = new File("data/7TestNodes.edge.graph");
	
	@Test
	public void calculateIntersectsTest() throws FileNotFoundException {
		// Load data
		GraphData gd = GraphData.parseGraph(nodesFile, edgesFile);
		List<Edge> testEdges = new ArrayList<Edge>();
		Edge edge34 = gd.getEdge(3, 4);
		testEdges.add(edge34);
		Edge edge35 = gd.getEdge(3, 5);
		testEdges.add(edge35);
		Edge edge36 = gd.getEdge(3, 6);
		testEdges.add(edge36);
		Edge edge67 = gd.getEdge(6, 7);
		testEdges.add(edge67);
		
		// Create target list
		List<Target> targets = new ArrayList<Target>();
		for (Node node : gd.getNodes()) {
			SingleNode snode = (SingleNode) node;
			snode.calculateStartX();
			snode.calculatePreviousNodesCount();
			targets.add(new NodeTarget(snode));
		}
		Map<Edge, EdgeTarget> testTargets = new HashMap<Edge, EdgeTarget>();
		for (Edge edge : gd.getEdges()) {
			if (testEdges.contains(edge)) {
				EdgeTarget testTarget = new EdgeTarget(edge);
				testTargets.put(edge, testTarget);
				targets.add(testTarget);
			} else {
				targets.add(new EdgeTarget(edge));
			}
		}
		
		// Select candidates
		for (EdgeTarget target : testTargets.values()) {
			target.selectCandidates(targets);
		}
		assertTrue(testTargets.get(edge34).intersectCandidates.size() == 4);
		assertTrue(testTargets.get(edge35).intersectCandidates.size() == 4);
		assertTrue(testTargets.get(edge36).intersectCandidates.size() == 4);
		assertTrue(testTargets.get(edge67).intersectCandidates.size() == 2);
		int[] order = new int[] { -1, 1, 2, 3, 4, 5, 6, 7 };
		assertTrue(testTargets.get(edge34).calculateIntersects(order) == 2);
		assertTrue(testTargets.get(edge35).calculateIntersects(order) == 1);
		assertTrue(testTargets.get(edge36).calculateIntersects(order) == 0);
		assertTrue(testTargets.get(edge67).calculateIntersects(order) == 0);
	}
}
