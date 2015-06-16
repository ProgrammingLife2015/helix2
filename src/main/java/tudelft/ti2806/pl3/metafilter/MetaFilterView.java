package tudelft.ti2806.pl3.metafilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import tudelft.ti2806.pl3.data.Gender;

import javax.swing.*;

/**
 * The view for selecting a gene to navigate to.
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterView extends JPanel {

	public MetaFilterView() {
		JCheckBox hivStatus = new JCheckBox("HIV positive", true);
		JTextField age = new JTextField("age");
		JComboBox<Gender> gender = new JComboBox<>(Gender.values());

		this.add(hivStatus);
		this.add(age);
		this.add(gender);
		AutoCompleteDecorator.decorate(gender);
	}

}
