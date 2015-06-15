package tudelft.ti2806.pl3.findgenes;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import tudelft.ti2806.pl3.data.gene.Gene;

import javax.swing.JComboBox;

/**
 * The view for selecting a gene to navigate to.
 * Created by Boris Mattijssen on 30-05-15.
 */
public class FindgenesView extends JComboBox<Gene> {

    /**
     * Construct the {@link JComboBox} and make it autocompletable.
     *
     * @param genes
     *         The genes to select
     */
    public FindgenesView(Gene[] genes) {
        super(genes);
        setEditable(true);
        AutoCompleteDecorator.decorate(this);
    }

}
