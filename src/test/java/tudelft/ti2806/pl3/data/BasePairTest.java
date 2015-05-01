package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertTrue;
import static tudelft.ti2806.pl3.data.BasePair.A;
import static tudelft.ti2806.pl3.data.BasePair.C;
import static tudelft.ti2806.pl3.data.BasePair.G;
import static tudelft.ti2806.pl3.data.BasePair.N;
import static tudelft.ti2806.pl3.data.BasePair.U;

import org.junit.Test;

import java.util.Arrays;

public class BasePairTest {
	
	@Test
	public void geneStringTest() {
		assertTrue(Arrays.equals(BasePair.toEnumString("UCAG"), new BasePair[] {
				U, C, A, G }));
	}
	
	@Test
	public void getCodonTest() {
		assertTrue(BasePair.getCodon(new BasePair[] { U, U, U }) == 0);
		assertTrue(BasePair.getCodon(new BasePair[] { U, U, G }) == 3);
		assertTrue(BasePair.getCodon(new BasePair[] { U, G, G }) == 15);
		assertTrue(BasePair.getCodon(new BasePair[] { G, G, G }) == 63);
		assertTrue(BasePair.getCodon(new BasePair[] { N, G, G }) == -1);
		
		assertTrue(BasePair.getCodon(BasePair.toEnumString("UUA")) == 2);
		assertTrue(BasePair.getCodon(BasePair.toEnumString("GGC")) == 61);
	}
}
