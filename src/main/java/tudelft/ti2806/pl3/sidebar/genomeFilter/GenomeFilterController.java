package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterController implements Controller {

	JPanel view;

	public GenomeFilterController() {
		view = new GenomeFilterView();
	}

	@Override
	public Component getPanel() {
		return view;
	}
}
