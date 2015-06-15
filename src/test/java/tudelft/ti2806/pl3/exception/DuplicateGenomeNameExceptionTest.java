package tudelft.ti2806.pl3.exception;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test for DuplicateGenomeNameException
 * Created by Kasper on 10-6-2015.
 */

public class DuplicateGenomeNameExceptionTest {


    @Test
    public void testDescriptionAndReason() {
        String description = "testdescription";
        String reason = "testreason";
        String message = description + "\n" + reason;
        try {
            throw new DuplicateGenomeNameException(description, reason);
        } catch (DuplicateGenomeNameException e) {
            assertEquals(message, e.getMessage());
        }
    }
}
