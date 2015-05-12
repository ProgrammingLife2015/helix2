import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Drawing {
	public static void main(String[] s) {
		Graph graph = new SingleGraph("");
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CD", "C", "D");
		graph.addEdge("BE", "B", "E");
		graph.addEdge("EC", "E", "C");
		for (Node node : graph) {
			node.addAttribute("ui.label", "["+node.getId());
		}
		graph.display();
	}
}
