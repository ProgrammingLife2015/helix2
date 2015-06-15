package nl.tudelft.ti2806.pl3.data;

import static nl.tudelft.ti2806.pl3.data.BasePair.G;

import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2806.pl3.data.AminoAcid;
import nl.tudelft.ti2806.pl3.data.BasePair;

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
