package ti2806.data;

import static org.junit.Assert.assertTrue;
import static ti2806.data.Gene.G;

import org.junit.Test;

import java.util.Arrays;

public class AminoAcidTest {
	
	@Test
	public void getAcidsTest() {
		assertTrue(Arrays.equals(AminoAcid.getAcids(Gene.getGeneString("GGG")),
				new AminoAcid[] { AminoAcid.get(Gene.getCodon(G, G, G)) }));
	}
}
