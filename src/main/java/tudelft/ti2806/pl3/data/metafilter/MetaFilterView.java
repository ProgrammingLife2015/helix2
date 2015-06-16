package tudelft.ti2806.pl3.data.metafilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import tudelft.ti2806.pl3.data.Gender;
import tudelft.ti2806.pl3.data.gene.Gene;

import javax.swing.*;

/**
 * The view for selecting a gene to navigate to.
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterView extends JPanel {

	public MetaFilterView(Gene[] genes) {
		JCheckBox hivStatus = new JCheckBox("HIV positive", true);
		JTextField age = new JTextField("age");
		JComboBox<Gender> gender = new JComboBox<>(Gender.values());
		AutoCompleteDecorator.decorate(gender);
	}

}
