package tudelft.ti2806.pl3.sidebar.genomeFilter;

import tudelft.ti2806.pl3.sidebar.SideBarViewInterface;

import java.awt.*;
import javax.swing.*;

/**
 * Created by Boris Mattijssen on 12-05-15.
 */
public class GenomeFilterView extends JPanel implements SideBarViewInterface {

	public GenomeFilterView() {
		String[] data = new String[2];
		data[0] = "Hoi";
		data[1] = "Doei";
		JList<String> list = new JList(data); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));

		add(listScroller);
	}
}
