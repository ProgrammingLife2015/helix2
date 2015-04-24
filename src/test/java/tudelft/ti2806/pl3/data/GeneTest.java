package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertTrue;
import static tudelft.ti2806.pl3.data.Gene.A;
import static tudelft.ti2806.pl3.data.Gene.C;
import static tudelft.ti2806.pl3.data.Gene.G;
import static tudelft.ti2806.pl3.data.Gene.N;
import static tudelft.ti2806.pl3.data.Gene.U;

import org.junit.Test;

import java.util.Arrays;

public class GeneTest {
	
	@Test
	public void geneStringTest() {
		assertTrue(Arrays.equals(Gene.getGeneString("UCAG"),
				new Gene[] {U, C, A, G }));
	}
	
	@Test
	public void getCodonTest() {
		assertTrue(Gene.getCodon(U, U, U) == 0);
		assertTrue(Gene.getCodon(U, U, G) == 3);
		assertTrue(Gene.getCodon(U, G, G) == 15);
		assertTrue(Gene.getCodon(G, G, G) == 63);
		assertTrue(Gene.getCodon(N, G, G) == -1);
		
		assertTrue(Gene.getCodon(Gene.getGeneString("UUA")) == 2);
		assertTrue(Gene.getCodon(Gene.getGeneString("GGC")) == 61);
	}
}
