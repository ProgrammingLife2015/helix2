package tudelft.ti2806.pl3.data.meta;

import org.junit.Test;
import tudelft.ti2806.pl3.data.Gender;
import tudelft.ti2806.pl3.data.Genome;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tombrouws on 16/06/15.
 */
public class MetaParserTest {

    @Test
    public void parserTest() throws FileNotFoundException {
        Map<String, Genome> genomeMap = new HashMap<>();
        Genome gen1 = new Genome("BTB04_097");
        Genome gen2 = new Genome("TKK_04_0048");
        File testFile = new File("data/testdata/metadata");

        genomeMap.put("BTB04_097", gen1);
        genomeMap.put("TKK_04_0048", gen2);

        MetaParser.parseMeta(testFile, genomeMap);

        assertFalse(gen1.getHivStatus());
        assertTrue(gen2.getHivStatus());
        assertEquals(gen1.getAge(), 34);
        assertEquals(gen2.getAge(), 34);
        assertEquals(gen1.getGender(), Gender.FEMALE);
        assertEquals(gen2.getGender(), Gender.MALE);
        assertEquals(gen1.getLocation(), "Europe");
        assertEquals(gen2.getLocation(), "South-Africa");
        assertEquals(gen1.getIsolationDate(), "11/2013");
        assertEquals(gen2.getIsolationDate(), "5/2007");
    }
}

//##ID	HIV	Age	Gender	Location	IsolationDate
//        BTB04_097	Negative	34	Female	Europe	11/2013
//        TKK_04_0048	Positive	34	Male	South-Africa	5/2007
