package tudelft.ti2806.pl3.metafilter;

import org.apache.commons.lang.math.IntRange;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.data.Gender;
import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.filter.GenomeFilter;
import tudelft.ti2806.pl3.data.graph.AbstractGraphData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.ui.util.DialogUtil;
import tudelft.ti2806.pl3.visualization.GraphController;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom Brouws on 16-06-15.
 */
public class MetaFilterController {

    private final AbstractGraphData graphData;
    private final ControllerContainer cc;
    private MetaFilterView metaFilterView;

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
     * Compute the gender from input.
     *
     * @param input
     *         the input
     * @return the gender
     */
    private Gender computeGender(String input) {
        if (input.equals("Female")) {
            return Gender.FEMALE;
        } else if (input.equals("Male")) {
            return Gender.MALE;
        } else {
            return null;
        }
    }

    /**
     * Compute the HIV status from input.
     *
     * @param input
     *         the input
     * @return the HIV status
     */
    private boolean computeHivStatus(String input) {
        if (input.equals("Positive")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compute the age range from input.
     *
     * @param input
     *         the input
     * @return the age range
     */
    private IntRange computeAge(String input) {
        String[] tokens = input.split("-");
        try {
            int rangeStart = Integer.parseInt(tokens[0]);
            int rangeEnd = Integer.parseInt(tokens[1]);
            return new IntRange(rangeStart, rangeEnd);
        } catch (ArrayIndexOutOfBoundsException e) {
            return new IntRange(0, Integer.parseInt(tokens[0]));
        } catch (NumberFormatException e) {
            return new IntRange(0, 0);
        }
    }

    /**
     * Opens the dialog which lets the user select a gene.<br>
     * After a gene is selected, it will do a call to the {@link GraphController} to navigate to the start of that
     * gene.
     */
    public void openDialog() {
        if (metaFilterView == null) {
            metaFilterView = new MetaFilterView();
        }

        JOptionPane
                .showMessageDialog(null, metaFilterView, "Select options to filter on:", JOptionPane.QUESTION_MESSAGE);

        List<Genome> firstGenomeList = new ArrayList<>();
        List<String> genomeList = new ArrayList<>();
        IntRange selectedAge = computeAge(metaFilterView.getAge());
        Gender selectedGender = computeGender(metaFilterView.getGender());
        String selectedLocation = metaFilterView.getStrainLocation();
        String selectedIsolationDate = metaFilterView.getIsolationDate();

        final boolean selectedHivStatus;
        final boolean checkHiv;
        if (metaFilterView.getHivStatus().equals("- None -")) {
            checkHiv = false;
            selectedHivStatus = false;
        } else {
            selectedHivStatus = computeHivStatus(metaFilterView.getHivStatus());
            checkHiv = true;
        }

        firstGenomeList.addAll(graphData.getGenomes().stream().filter(
                genome -> (selectedGender == null || genome.getGender().equals(selectedGender))
                        && (selectedLocation.equals("") || genome.getLocation().equals(selectedLocation))
                        && (selectedIsolationDate.equals("") || genome.getIsolationDate().equals(selectedIsolationDate))
                        && (!checkHiv || genome.getHivStatus() == selectedHivStatus)).collect(
                Collectors.toList()));

        if (selectedAge.getMaximumInteger() == 0) {
           genomeList = firstGenomeList.stream().map(Genome::getIdentifier).collect(Collectors.toList());
        } else if (selectedAge.getMinimumInteger() == 0) {
            genomeList.addAll(firstGenomeList.stream().filter(
                    genome -> (selectedAge.getMaximumInteger() == genome.getAge())).map(Genome::getIdentifier).collect(
                    Collectors.toList()));
        } else {
            genomeList.addAll(firstGenomeList.stream().filter(
                    genome -> (selectedAge.containsInteger(genome.getAge()))).map(Genome::getIdentifier).collect(
                    Collectors.toList()));
        }

        if (genomeList.size() > 1) {
            cc.getGraphController().addFilter(GenomeFilter.NAME, new GenomeFilter(genomeList));
        } else if (genomeList.size() == 1) {
            genomeList.add("TKK_REF");
            cc.getGraphController().addFilter(GenomeFilter.NAME, new GenomeFilter(genomeList));
        } else {
            if (DialogUtil.confirm("No genomes found",
                    "No genomes are found for the selected metadata, want to enter new data?")) {
                openDialog();
            }
        }
    }
}
