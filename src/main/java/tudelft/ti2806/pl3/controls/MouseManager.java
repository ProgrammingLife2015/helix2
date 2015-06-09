package tudelft.ti2806.pl3.controls;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import tudelft.ti2806.pl3.data.wrapper.WrapperClone;
import tudelft.ti2806.pl3.detailedView.DetailView;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Mathieu Post on 8-6-15.
 */
public class MouseManager extends DefaultMouseManager {
	WrapperClone node = null;
	DetailView detailView = new DetailView();

	@Override public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		int x = e.getX();
		int y = e.getY();
		ArrayList<GraphicElement> graphicElements = view.allNodesOrSpritesIn(x - 5, y - 5, x + 5, y + 5);
		for (GraphicElement graphicElement : graphicElements) {
			System.out.println(graphicElement);
		}
	}

	@Override public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		int x = e.getX();
		int y = e.getY();
		ArrayList<GraphicElement> graphicElements = view.allNodesOrSpritesIn(x - 5, y - 5, x + 5, y + 5);
		if (graphicElements.size() == 0) {
			node = null;
		} else {
			WrapperClone wrapper = graphicElements.get(0).getAttribute("node", WrapperClone.class);
			view.add(detailView);
			if (node != wrapper) {
				node = wrapper;
				System.out.println(node.getGenome());
			}
		}
	}
}
