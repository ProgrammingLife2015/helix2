package tudelft.ti2806.pl3.visualization;

import org.graphstream.graph.Node;

import tudelft.ti2806.pl3.data.GraphParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mathieu Post on 29-4-15.
 */
public class App {
	public static void main(String args[]) throws FileNotFoundException {
		for (Node node : graph) {
			node.setAttribute("y", 0);
			node.setAttribute("x", 5);
			// if (Math.random() > 0.1)
			node.addAttribute("ui.hide");
		}
		graph.display();
		
	}
}
