package tudelft.ti2806.pl3.data.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tudelft.ti2806.pl3.data.Genome;
import tudelft.ti2806.pl3.data.graph.DataNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Test the {@link GenomeFilter} class Created by Boris Mattijssen on 18-05-15.
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
	 * Setup the genomes, nodes and lists. We stub the genomes and nodes to only
	 * test the filtering behaviour.
	 */
	@Before
	public void before() {
		when(genome1.getIdentifier()).thenReturn("ID1");
		when(genome2.getIdentifier()).thenReturn("ID2");
		
		Set<Genome> set1 = new HashSet<>();
		Set<Genome> set2 = new HashSet<>();
		Set<Genome> set3 = new HashSet<>();
		Set<Genome> set4 = new HashSet<>();
		set1.add(genome1);
		set1.add(genome2);
		set2.add(genome2);
		set3.add(genome1);
		DataNode node1 = new DataNode(1, set1, 0, 0, new byte[] {});
		DataNode node2 = new DataNode(2, set2, 0, 0, new byte[] {});
		DataNode node3 = new DataNode(3, set3, 0, 0, new byte[] {});
		DataNode node4 = new DataNode(4, set4, 0, 0, new byte[] {});
		
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
		for (DataNode node : nodeList) {
			assertTrue(node.getCurrentGenomeList().size() > 0);
			for (Genome genome : node.getCurrentGenomeList()) {
				assertFalse(genome.getIdentifier().equals(
						genome2.getIdentifier()));
			}
		}
	}
	
}
