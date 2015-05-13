package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.ScreenSize;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.sidebar.SideBarView;
import tudelft.ti2806.pl3.sidebar.SideBarViewInterface;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterView extends JPanel implements SideBarViewInterface {

	private JList list;

	public GenomeFilterView(GenomeFilterController genomeFilterController, List<Genome> genomes) {
		list = new JList(genomes
				.stream()
				.map(i -> i.getIdentifier())
				.sorted()
				.toArray());
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		int[] indices = new int[genomes.size()];
		for(int i = 0; i<genomes.size(); i++) {
			indices[i] = i;
		}
		list.setSelectedIndices(indices);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(
				ScreenSize.getInstance().calculateWidth(SideBarView.SIDEBAR_FACTOR*0.8),
				ScreenSize.getInstance().calculateHeight(0.2)));

		JButton button = new JButton("Update");
		button.addActionListener(genomeFilterController);

		add(new JLabel("Genome filter:"));
		add(listScroller);
		add(button);
	}

	public List getSelected() {
		return list.getSelectedValuesList();
	}
}
