package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Graph;

import tudelft.ti2806.pl3.data.GraphParser;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Mathieu Post on 29-4-15.
 */
public class App {
	public static void main(String args[]) throws FileNotFoundException {
		Graph graph = GraphParser.parseGraph("hello", new File(
				"data/10_strains_graph/simple_graph.node.graph"), new File(
				"data/10_strains_graph/simple_graph.edge.graph"));
		graph.display();
	}
}
