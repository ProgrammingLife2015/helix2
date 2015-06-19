package tudelft.ti2806.pl3.findgenes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import tudelft.ti2806.pl3.ControllerContainer;
import tudelft.ti2806.pl3.data.graph.GraphDataRepository;

/**
 * Tests for FindgenesController
 * Created by Kasper on 17-6-2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class FindgenesControllerTest {

	@Mock
	ControllerContainer cc;
	@Mock
	GraphDataRepository graphData;
//	@Mock
//	List<Gene> geneList;
//	@Mock
//	Gene[] genesarray;


	public void setUp() {
		//when(findgenesView.getSelectedItem()).thenReturn(mock(Object.class));
//

	}

	@Test
	public void testConstructor() {
		FindGenesController findgenesController = new FindGenesController(cc, graphData);
	}
}
