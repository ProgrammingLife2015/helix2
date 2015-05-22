package tudelft.ti2806.pl3.data.graph;

import org.junit.Assert;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.exeption.DuplicateGenomeNameException;
import tudelft.ti2806.pl3.util.DeadEdgeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ParsedGraphDataTester {
//	@Test
	public void testGraphData() throws FileNotFoundException {
		File nodesFile = new File(
				"data/38_strains_graph/simple_graph.node.graph");
		File edgesFile = new File(
				"data/38_strains_graph/simple_graph.edge.graph");
		GraphDataRepository gdr = GraphDataRepository.parseGraph(nodesFile,
				edgesFile);
		test(gdr);
	}
	
	public static void test(final AbstractGraphData origin) {
		origin.getGenomes()
				.stream()
				.map(Genome::getIdentifier)
				.forEach(
						string -> {
						List<DataNode> nodeList = origin.getNodeListClone();
						List<String> list = new ArrayList<>();
						list.add(string);
						new GenomeFilter(list).filter(nodeList);
						List<Edge> edgeList = origin.getEdgeListClone();
						DeadEdgeUtil.removeAllDeadEdges(edgeList, nodeList);
						GraphData gd = new GraphData(origin, nodeList,
								edgeList, origin.getGenomes());
						WrappedGraphData wgd = new WrappedGraphData(gd);
						Wrapper node = getFirstNode(wgd);
						Set<Wrapper> set = new HashSet<>(wgd
								.getPositionedNodes());
						set.remove(node);
						while (true) {
							if (node.getOutgoing().size() == 1) {
								node = node.getOutgoing().get(0);
							} else if (node.getOutgoing().size() > 1) {
								int min = Integer.MAX_VALUE;
								Wrapper newNode = null;
								for (Wrapper out : node.getOutgoing()) {
									if (out.getPreviousNodesCount() < min) {
										min = out.getPreviousNodesCount();
										newNode = out;
									}
								}
								node = newNode;
							} else {
								break;
							}
							set.remove(node);
						}
						if (set.size() != 0) {
							try {
								throw new DuplicateGenomeNameException(
										"Genome is not fully connected.",
										"Parse test. Found " + set.size()
												+ " remaining nodes.");
							} catch (Exception e) {
								e.printStackTrace();
							}
							Assert.fail();
						}
					});
	}
	
	private static Wrapper getFirstNode(WrappedGraphData wgd) {
		for (Wrapper node : wgd.getPositionedNodes()) {
			if (node.getPreviousNodesCount() == 0) {
				return node;
			}
		}
		return null;
	}
}
