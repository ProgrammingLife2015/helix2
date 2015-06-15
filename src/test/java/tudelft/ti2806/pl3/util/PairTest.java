package tudelft.ti2806.pl3.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for Pair class.<br>
 * Created by Kasper on 21-5-2015.
 */

public class PairTest {
    private Integer two;
    private Integer three;
    private Pair<Integer, Integer> pair;

    /**
     * Runs before each test.
     */
    @Before
    public void before() {
        two = new Integer(2);
        three = new Integer(3);
        pair = new Pair<>(two, three);
    }


    @Test
    public void testGetter() {
        pair = new Pair<>(two, three);
        assertEquals(pair.getFirst(), two);
        assertNotEquals(pair.getFirst(), three);
        assertEquals(pair.getSecond(), three);
        assertNotEquals(pair.getSecond(), two);
    }

    @Test
    public void testToString() {
        pair = new Pair<>(two, three);
        String name = "Pair [first=" + two.toString() + ", second=" + three.toString() + "]";
        assertEquals(pair.toString(), name);
        assertNotEquals(pair.toString(), "Pair not matching");
    }

    @Test
    public void testEquals() {
        assertTrue(pair.equals(pair));
        assertFalse(pair.equals(null));
        assertFalse(pair.equals(new Integer(100)));

        Pair<Integer, Integer> equal = new Pair<>(two, three);
        Pair<Integer, Integer> equal2 = new Pair<>(two, two);
        Pair<Integer, Integer> diffelement = new Pair<>(three, two);
        Pair<Integer, Integer> firstnull = new Pair<>(null, two);
        Pair<Integer, Integer> secondnull = new Pair<>(two, null);

        assertTrue(pair.equals(equal));
        assertFalse(pair.equals(diffelement));
        assertFalse(firstnull.equals(diffelement));
        assertFalse(secondnull.equals(equal));
        assertFalse(equal.equals(equal2));


    }

    @Test
    public void testHashCode() {
        pair = new Pair<>(two, three);
        assertEquals(pair.hashCode(), 1026);
        assertNotEquals(pair.hashCode(), 2020);
    }
}
