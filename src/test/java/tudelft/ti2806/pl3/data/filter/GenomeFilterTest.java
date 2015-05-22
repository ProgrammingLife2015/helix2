package tudelft.ti2806.pl3.data.filter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the  {@link GenomeFilter} class
 * Created by Boris Mattijssen on 18-05-15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenomeFilterTest {


	@Mock
			Genome genome1;
	@Mock
			Genome genome2;
	@Mock
			DataNode node1;
	@Mock
			DataNode node2;
	@Mock
			DataNode node3;
	@Mock
			DataNode node4;
	GenomeFilter genomeFilter;
	List<DataNode> nodeList;

	/**
	 * Setup the genomes, nodes and lists.
	 * We stub the genomes and nodes to only test the filtering behaviour.
	 */
	@Before
	public void before() {
		when(genome1.getIdentifier()).thenReturn("ID1");
		when(genome2.getIdentifier()).thenReturn("ID2");

		when(node1.getSource()).thenReturn(new Genome[]{genome1, genome2});
		when(node2.getSource()).thenReturn(new Genome[]{genome2});
		when(node3.getSource()).thenReturn(new Genome[]{genome1});
		when(node4.getSource()).thenReturn(new Genome[]{});

		nodeList = new ArrayList<>();
		nodeList.add(node1);
		nodeList.add(node2);
		nodeList.add(node3);
		nodeList.add(node4);

		List<String> genomes = new ArrayList<>();
		genomes.add(genome1.getIdentifier());
		genomeFilter = new GenomeFilter(genomes);
	}

	/**
	 * Test to check that all four nodes are there before filtering.
	 */
	@Test
	public void testBeforeFilter() {
		assertEquals(nodeList.size(), 4);
	}

	/**
	 * After filtering only the nodes containing genome1 should be there.
	 */
	@Test
	public void testGenomeFilter() {
		genomeFilter.filter(nodeList);
		assertEquals(nodeList.size(), 2);
	}

}
