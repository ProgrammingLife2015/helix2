package nl.tudelft.ti2806.pl3.data.wrapper;

import nl.tudelft.ti2806.pl3.data.Genome;
import nl.tudelft.ti2806.pl3.data.graph.DataNode;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.Set;

public class FixWrapper extends Wrapper {
    public static final int ID = -1;
    public static final String ID_STRING = "[FIX]";
    private Set<Genome> genome;

    @Override
    public long getBasePairCount() {
        return 0;
    }

    @Override
    public String getIdString() {
        return ID_STRING;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Set<Genome> getGenome() {
        return genome;
    }

    @Override
    public void calculate(WrapperOperation operation, Wrapper container) {
        operation.calculate(this, container);
    }

    @Override
    public void collectDataNodes(Set<DataNode> set) {

    }

    public void setGenome(Set<Genome> genome) {
        this.genome = genome;
    }

    @Override
    public void calculateX() {
        this.x = this.getPreviousNodesCount();
    }

    @Override
    public int getWidth() {
        return 0;
    }
}
