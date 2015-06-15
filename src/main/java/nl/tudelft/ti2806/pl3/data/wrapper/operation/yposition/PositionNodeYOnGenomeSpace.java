package nl.tudelft.ti2806.pl3.data.wrapper.operation.yposition;

import nl.tudelft.ti2806.pl3.data.Genome;
import nl.tudelft.ti2806.pl3.data.wrapper.HorizontalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.SingleWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.SpaceWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.VerticalWrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.Wrapper;
import nl.tudelft.ti2806.pl3.data.wrapper.operation.WrapperOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Computes the position of the nodes on the y axis, based on their space in
 * their wrapped node. The space of a node is equal to the number of different
 * genome it contains.
 *
 * @author Sam Smulders
 */
public class PositionNodeYOnGenomeSpace extends WrapperOperation {
    @Override
    public void calculate(SpaceWrapper wrapper, Wrapper container) {
        List<Wrapper> nodeList = wrapper.getNodeList();
        nodeList.get(0).setY(wrapper.getY());
        for (int i = 0; i < nodeList.size() - 1; i++) {
            Wrapper from = nodeList.get(i);
            List<Wrapper> sortedOutgoing = new ArrayList<>(from.getOutgoing());

            Collections.sort(sortedOutgoing);
            Set<Genome> set = new HashSet<>();
            Map<Wrapper, Integer> magicMap = new HashMap<>();
            for (Wrapper to : sortedOutgoing) {
                Set<Genome> toGenome = new HashSet<>(to.getGenome());
                toGenome.retainAll(from.getGenome());
                int size = -set.size();
                set.addAll(toGenome);
                magicMap.put(to, size + set.size());
            }
            float share = -from.getGenome().size() / 2f + from.getY();
            for (Wrapper to : from.getOutgoing()) {
                float size = magicMap.get(to);
                to.setY(to.getY() + (share + size / 2)
                        * (size / to.getGenome().size()));
                share += size;
            }
        }
        nodeList.get(nodeList.size() - 1).setY(nodeList.get(0).getY());
        super.calculate(wrapper, container);
    }

    @Override
    public void calculate(VerticalWrapper wrapper, Wrapper container) {
        int wrapperGenomeSize = wrapper.getGenome().size();
        float space = wrapper.getY() - wrapperGenomeSize / 2f;
        for (Wrapper node : wrapper.getNodeList()) {
            int nodeSize = node.getGenome().size();
            node.setY(space + nodeSize / 2f);
            space += nodeSize;
        }
        super.calculate(wrapper, container);
    }

    @Override
    public void calculate(HorizontalWrapper wrapper, Wrapper container) {
        for (Wrapper node : wrapper.getNodeList()) {
            node.setY(wrapper.getY());
        }
        super.calculate(wrapper, container);
    }

    @Override
    public void calculate(SingleWrapper wrapper, Wrapper container) {
        wrapper.getNode().setY(wrapper.getY());
        super.calculate(wrapper, container);
    }
}
