package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.sidebar.SideBarViewInterface;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * View that shows the genome filter.
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterView extends JPanel implements SideBarViewInterface {

	private JList<String> list;

	public static final double GENOME_FILTER_FACTOR = 0.8;
	public static final double GENOME_FILTER_HEIGHT = 0.2;

	/**
	 * Construct the view for the genome filter.
	 * @param genomeFilterController
	 *          the view's controller
	 * @param genomes
	 *          the list of genomes to filter
	 */
	public GenomeFilterView(GenomeFilterController genomeFilterController,
	                        List<Genome> genomes) {
		createList(genomes);
		selectAllIndices(genomes);

		add(new JLabel("Genome filter:"));
		add(createScrollPane());
		add(createButton(genomeFilterController));
	}

	/**
	 * Create the scroll panel containing the @JList.
	 * @return
	 *          the scroll panel
	 */
	private JScrollPane createScrollPane() {
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(
				ScreenSize.getInstance().calculateWidth(
						SideBarView.SIDEBAR_FACTOR * GENOME_FILTER_FACTOR),
				ScreenSize.getInstance().calculateHeight(GENOME_FILTER_HEIGHT)));
		return listScroller;
	}

	/**
	 * Create the submit button.
	 * @param genomeFilterController
	 *          the controller which will be the action listener
	 * @return
	 *          the submit button
	 */
	private JButton createButton(GenomeFilterController genomeFilterController) {
		JButton button = new JButton("Update");
		button.addActionListener(genomeFilterController);
		return button;
	}

	/**
	 * Creates the JList for the view.
	 * @param genomes
	 *          the list of all genomes
	 */
	private void createList(List<Genome> genomes) {
		list = new JList<>(genomes
				.stream()
				.map(Genome::getIdentifier)
				.sorted()
				.toArray(String[]::new));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
	}

	/**
	 * Select all genomes on the JList.
	 * @param genomes
	 *          all genomes to select
	 */
	private void selectAllIndices(List<Genome> genomes) {
		int[] indices = new int[genomes.size()];
		for (int i = 0; i < genomes.size(); i++) {
			indices[i] = i;
		}
		list.setSelectedIndices(indices);
	}

	/**
	 * Get all selected items.
	 * @return
	 *          the selected items in the list
	 */
	public List<String> getSelected() {
		return list.getSelectedValuesList();
	}
}
