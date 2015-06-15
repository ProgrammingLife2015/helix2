package tudelft.ti2806.pl3.data.wrapper.operation.interest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tudelft.ti2806.pl3.data.gene.GeneData;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;
import tudelft.ti2806.pl3.data.wrapper.WrappedGraphData;
import tudelft.ti2806.pl3.data.wrapper.Wrapper;
import tudelft.ti2806.pl3.data.wrapper.util.WrapUtil;
import tudelft.ti2806.pl3.testutil.UtilTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ComputeInterestTest {
    private static final float[] expectedResults = new float[]{0f, 1f, 2f, 2f, 2f};

    @Test
    public void test() throws IOException {
        File nodesFile = new File("data/testdata/interest/compute.node.graph");
        File edgesFile = new File("data/testdata/interest/compute.edge.graph");
        GeneData geneData = GeneData.parseGenes("data/testdata/TestGeneAnnotationsFile");

        GraphDataRepository gdr = new GraphDataRepository();
        gdr.parseGraph(nodesFile, edgesFile, geneData);
        WrappedGraphData wgd = new WrappedGraphData(gdr);
        Wrapper wrapper = WrapUtil.collapseGraph(wgd).getPositionedNodes().get(0);
        ComputeInterest.compute(wrapper);
        List<Wrapper> list = wgd.getPositionedNodes().stream()
                .sorted((e1, e2) -> Float.compare(e1.getInterest(), e2.getInterest()))
                .collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedResults[i], list.get(i).getInterest(), 0f);
        }
    }

    @Test
    public void utilClassTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        new UtilTest<>(ComputeInterest.class).testConstructorIsPrivate();
    }

}
