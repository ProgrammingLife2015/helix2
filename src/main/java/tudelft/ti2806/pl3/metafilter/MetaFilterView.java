package tudelft.ti2806.pl3.metafilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;

/**
 * The view for filtering on metadata
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterView extends JPanel {

	private final JComboBox<String> hivStatus;
	private final JTextField age;
	private final JComboBox<String> gender;

	public String getHivStatus() {
		return (String) hivStatus.getSelectedItem();
	}

	public String getAge() {
		return age.getText();
	}

	public String getGender() {
		return (String) gender.getSelectedItem();
	}

	public String getStrainLocation() {
		return strainLocation.getText();
	}

	public String getIsolationDate() {
		return isolationDate.getText();
	}

	private final JTextField strainLocation;
	private final JTextField isolationDate;

	public MetaFilterView() {
		super();

		GridLayout gridLayout = new GridLayout(5, 2);
		this.setLayout(gridLayout);

		JLabel hivLabel = new JLabel("HIV status:");
		hivStatus = new JComboBox<>(new DefaultComboBoxModel(new String[] {"- None -", "Positive", "Negative"}));

		JLabel ageLabel = new JLabel("Age:");
		age = new JTextField();

		JLabel genderLabel = new JLabel("Gender:");
		gender = new JComboBox<>(new DefaultComboBoxModel(new String[] {"- None -", "Male", "Female"}));

		JLabel locationLabel = new JLabel("Location:");
		strainLocation = new JTextField();

		JLabel isolationDateLabel = new JLabel("Isolation date:");
		isolationDate = new JTextField();

		this.add(hivLabel);
		this.add(hivStatus);
		this.add(ageLabel);
		this.add(age);
		this.add(genderLabel);
		this.add(gender);
		this.add(locationLabel);
		this.add(strainLocation);
		this.add(isolationDateLabel);
		this.add(isolationDate);
		AutoCompleteDecorator.decorate(gender);
	}

}
