package tudelft.ti2806.pl3.metafilter;

import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.data.Gender;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterController {

    private final AbstractGraphData graphData;
    private final ControllerContainer cc;

    /**
     * Construct the controller and instantiate the view.
     *
     * @param cc
     *         Reference to all controllers
     * @param graphData
     *         Contains the gene data
     */
    public MetaFilterController(ControllerContainer cc, GraphDataRepository graphData) {
        this.cc = cc;
        this.graphData = graphData;
    }


    /**
     * Opens the dialog which lets the user select a gene.<br>
     * After a gene is selected, it will do a call to the {@link GraphController} to navigate to the start of that
     * gene.
     */
    public void openDialog() {
        MetaFilterView metaFilterView = new MetaFilterView();

        JOptionPane
                .showMessageDialog(null, metaFilterView, "Select options to filter on:", JOptionPane.QUESTION_MESSAGE);

        List<String> genomeList = new ArrayList<>();
        int selectedAge = metaFilterView.getAge();
        Gender selectedGender = metaFilterView.getGender();
        String selectedLocation = metaFilterView.getStrainLocation();
        String selectedIsolationDate = metaFilterView.getIsolationDate();
        boolean selectedHivStatus = metaFilterView.getHivStatus();

        genomeList.addAll(graphData.getGenomes().stream().filter(genome -> genome.getAge() == selectedAge
                && genome.getGender().equals(selectedGender)
                && genome.getLocation().equals(selectedLocation)
                && genome.getIsolationDate().equals(selectedIsolationDate)
                && genome.getHivStatus() == selectedHivStatus).map(Genome::getIdentifier).collect(Collectors.toList()));

        if (genomeList.size() != 0) {
            cc.getGraphController().addFilter(GenomeFilter.NAME, new GenomeFilter(genomeList));
        }

//		if (previousSelected != findgenesView.getSelectedItem()) {
//			boolean tryAgain = false;
//			previousSelected = findgenesView.getSelectedItem();
//			try {
//				Gene selected = (Gene) findgenesView.getSelectedItem();
//				DataNode node = graphData.getGeneToStartNodeMap().get(selected);
//				if (node == null) {
//					JOptionPane.showMessageDialog(null,
//							"Couldn't find the selected gene. Try again");
//					tryAgain = true;
//				} else {
//					cc.getGraphController().centerOnNode(node);
//				}
//			} catch (ClassCastException e) {
//				JOptionPane.showMessageDialog(null, "Please select an existing gene. Try again");
//				tryAgain = true;
//			} catch (NodeNotFoundException e) {
//				JOptionPane.showMessageDialog(null, "Couldn't find the node on the graph. Try again");
//				tryAgain = true;
//			}
//			if (tryAgain) {
//				openDialog();
//			}
//		}
    }
}
