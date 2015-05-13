package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.Controller;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterController implements Controller, ActionListener {

	private final GraphController graphController;
	GenomeFilterView view;

	public GenomeFilterController(GraphController graphController, GraphDataRepository gd) {
		this.graphController = graphController;
		view = new GenomeFilterView(this, gd.getGenomes());
	}

	@Override
	public Component getPanel() {
		return view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		graphController.addFilter("genome", new GenomeFilter(view.getSelected()));
	}
}
