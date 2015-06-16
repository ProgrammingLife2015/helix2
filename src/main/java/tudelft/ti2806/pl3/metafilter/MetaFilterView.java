package tudelft.ti2806.pl3.metafilter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import tudelft.ti2806.pl3.data.Gender;

import javax.swing.*;
import java.awt.*;

/**
 * The view for filtering on metadata
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterView extends JPanel {

	private final JCheckBox hivStatus;
	private final JTextField age;
	private final JComboBox<Gender> gender;

	public boolean getHivStatus() {
		return hivStatus.isEnabled();
	}

	public int getAge() { // TODO: check input
		return Integer.parseInt(age.getText());
	}

	public Gender getGender() {
		if(gender.getSelectedItem().equals(Gender.FEMALE)) {
			return Gender.FEMALE;
		} else {
			return Gender.MALE;
		}
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

		hivStatus = new JCheckBox("HIV positive", true);

		JLabel ageLabel = new JLabel("Age:");
		age = new JTextField();

		JLabel genderLabel = new JLabel("Gender:");
		gender = new JComboBox<>(Gender.values());

		JLabel locationLabel = new JLabel("Location:");
		strainLocation = new JTextField();

		JLabel isolationDateLabel = new JLabel("Isolation date:");
		isolationDate = new JTextField();

		JLabel spacingLabel = new JLabel();

		this.add(hivStatus);
		this.add(spacingLabel);
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
