package com.prakat.projectx.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JUnit test cases for the Band class.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BandTest {
    @Mock
    private Band band;


    /**
     * Set up Band object before each test.
     */
    @BeforeEach
    public void setup() {
        band = new Band();
    }

    /**
     * Test for the Band ID property.
     */
    @Test
    public void testBandId() {
        Long bandId = 1L;
        band.setBandId(bandId);
        Assertions.assertEquals(bandId, band.getBandId(), "Band ID should be equal");

        // Test for null value
        band.setBandId(null);
        Assertions.assertNull(band.getBandId(), "Band ID should be null");
    }

    /**
     * Test for the Band Name property.
     */
    @Test
    public void testBandName() {
        // Positive test case
        String bandName = "L1";
        band.setBandName(bandName);
        Assertions.assertEquals(bandName, band.getBandName(), "Band name should be equal");

        // Negative test case - Testing null value
        band.setBandName(null);
        Assertions.assertNull(band.getBandName(), "Band name should be null");

    }

@Test
public void testDeleted() {
    Boolean expectedStatus = true;
    band.setDeleted(expectedStatus);
    Boolean actualStatus = band.isDeleted();
    Assertions.assertEquals(expectedStatus, actualStatus);
}

    /**
     * Test for the Audit property.
     */
    @Test
    public void testAudit() {
        Audit audit = new Audit();
        band.setAudit(audit);
        Assertions.assertEquals(audit, band.getAudit(), "Audit should be equal");

        // Test for null value
        band.setAudit(null);
        Assertions.assertNull(band.getAudit(), "Audit should be null");
    }

}
