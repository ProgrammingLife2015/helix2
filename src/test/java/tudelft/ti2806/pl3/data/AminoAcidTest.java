package tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertTrue;
import static tudelft.ti2806.pl3.data.BasePair.G;

import org.junit.Test;

import java.util.Arrays;

public class AminoAcidTest {

    @Test
    public void getAcidsTest() {
        assertTrue(Arrays.equals(AminoAcid.getAcids(BasePair
                .toEnumString("GGG")), new AminoAcid[]{AminoAcid.get(BasePair
                .getCodon(new BasePair[]{G, G, G}))}));
        assertTrue(Arrays.equals(
                AminoAcid.getAcids(BasePair.toEnumString("NGG")),
                new AminoAcid[]{null}));
    }
}
