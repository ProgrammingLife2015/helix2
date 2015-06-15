package tudelft.ti2806.pl3.data.gene;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by tombrouws on 30/05/15.
 */
public class GeneTest {

    /**
     * Test if gene equals itself.
     */
    @Test
    public void equalsTestSelf() {
        Gene gene = new Gene("test", 1, 10);

        assertTrue(gene.equals(gene));
    }

    /**
     * Test if gene equals null.
     */
    @Test
    public void equalsTestNull() {
        Gene gene = new Gene("test", 1, 10);

        assertFalse(gene.equals(null));
    }

    /**
     * Test if gene equals a gene with different end
     */
    @Test
    public void equalsTestEnd() {
        Gene gene1 = new Gene("test", 1, 10);
        Gene gene2 = new Gene("test", 1, 11);

        assertFalse(gene1.equals(gene2));
    }

    /**
     * Test if gene equals a gene with different start
     */
    @Test
    public void equalsTestStart() {
        Gene gene1 = new Gene("test", 1, 10);
        Gene gene2 = new Gene("test", 2, 10);

        assertFalse(gene1.equals(gene2));
    }

    /**
     * Test if gene equals another gene with
     */
    @Test
    public void equalsTestTrue() {
        Gene gene1 = new Gene("test", 1, 10);
        Gene gene2 = new Gene("test", 1, 10);

        assertTrue(gene1.equals(gene2));
    }

    /**
     * Test if gene hashes to the same value as a copy of the gene
     */
    @Test
    public void hashCodeTestTrue() {
        Gene gene1 = new Gene("test", 1, 10);
        Gene gene2 = new Gene("test", 1, 10);

        assertTrue(gene1.hashCode() == gene2.hashCode());
    }

    /**
     * Test if gene hashes to the same value as a copy of the gene
     */
    @Test
    public void hashCodeTestFalse() {
        Gene gene1 = new Gene("test", 2, 10);
        Gene gene2 = new Gene("test", 1, 10);

        assertFalse(gene1.hashCode() == gene2.hashCode());
    }
}
