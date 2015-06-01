package tudelft.ti2806.pl3.findgenes;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import tudelft.ti2806.pl3.data.gene.Gene;

import javax.swing.JComboBox;

/**
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindgenesView extends JComboBox<Gene> {

	public FindgenesView(Gene[] genes) {
		super(genes);
		setEditable(true);
		AutoCompleteDecorator.decorate(this);
	}

}
