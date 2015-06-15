package nl.tudelft.ti2806.pl3.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import nl.tudelft.ti2806.pl3.data.Genome;

import org.junit.Before;
import org.junit.Test;

/**
 * Genome data class tests.<br>
 * Created by Kasper on 21-5-2015.
 */
public class GenomeTest {

    private Genome genome1;
    private Genome genome2;

    @Before
    public void before() {
        genome1 = new Genome("genome1");
        genome2 = new Genome("genome2");
    }

    @Test
    public void testEquals() {
        assertTrue(genome1.equals(genome1));
        assertFalse(genome1.equals(null));
        assertFalse(genome1.equals(new Object()));
        assertFalse(new Genome(null).equals(genome1));
        assertFalse(genome1.equals(genome2));

        assertTrue(genome1.equals(new Genome("genome1")));
    }

    @Test
    public void testHashCode() {
        assertNotEquals(genome1.hashCode(), genome2.hashCode());
        assertEquals(genome1.hashCode(), genome1.hashCode());
    }

    @Test
    public void testGetIdentifier() {
        assertEquals(genome1.getIdentifier(), "genome1");
        assertNotEquals(genome1.getIdentifier(), genome2.getIdentifier());
    }

}
