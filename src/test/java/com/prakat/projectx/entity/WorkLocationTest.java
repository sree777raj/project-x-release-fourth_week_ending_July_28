package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class WorkLocationTest {
    private WorkLocation workLocation;

    @Mock
    private Audit mockAudit;

    @BeforeEach
    public void setup() {
        workLocation = new WorkLocation();
    }

    @Test
    public void testGetSetLocationId() {
        Long locationId = 1L;
        workLocation.setLocationId(locationId);
        assertEquals(locationId, workLocation.getLocationId());
    }

    @Test
    public void testGetSetLocationName() {
        String locationName = "Office";
        workLocation.setLocationName(locationName);
        assertEquals(locationName, workLocation.getLocationName());
    }

    @Test
    public void testGetSetAudit() {
        workLocation.setAudit(mockAudit);
        assertEquals(mockAudit, workLocation.getAudit());
    }

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(workLocation);
    }

    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        workLocation.setDeleted(expectedStatus);
        Boolean actualStatus = workLocation.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }
}
