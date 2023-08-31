package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProjectHoursTest {


    private ProjectHours projectHours;

    @BeforeEach
    void setup(){
       projectHours=new ProjectHours();
    }

    @Test
    void testId(){
        Assertions.assertNull(projectHours.getId());
        Long expectedId = 1L;
        projectHours.setId(expectedId);
        Assertions.assertEquals(expectedId, projectHours.getId());
    }


    @Test
    public void testApprovedHoursColumnMapping() {

        // Set the approved hours
        Integer approvedHours = 40;
        projectHours.setApprovedHours(approvedHours);
        Integer retrievedApprovedHours = projectHours.getApprovedHours();
        assertEquals(approvedHours, retrievedApprovedHours);
    }

    @Test
    void testAudit() {
        Assertions.assertNull(projectHours.getAudit());
        Audit expectedAudit = new Audit();
        projectHours.setAudit(expectedAudit);
        Assertions.assertEquals(expectedAudit, projectHours.getAudit());
    }



    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        projectHours.setDeleted(expectedStatus);
        Boolean actualStatus = projectHours.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }






}
