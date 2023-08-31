package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
    }

    //This method is used to validate the id
    @Test
    void testId() {
        Assertions.assertNull(project.getId());
        Long id = 1L;
        project.setId(id);
        Assertions.assertEquals(id, project.getId());
    }

    //This method is used to validate the projectName
    @Test
    void testProjectName() {
        Assertions.assertNull(project.getProjectName());
        String projectName = "Project-x";
        project.setProjectName(projectName);
        Assertions.assertEquals(projectName, project.getProjectName());
    }

    //This method is used to validate the projectValue
    @Test
    void testProjectValue() {
        Assertions.assertNull(project.getProjectValue());
        String projectValue = "100000";
        project.setProjectValue(projectValue);
        Assertions.assertEquals(projectValue, project.getProjectValue());
    }

    //This method is used to validate the approved budget
    @Test
    void testApprovedBudget() {
        String approvedBudget = "100000";
        project.setApprovedBudget(approvedBudget);
        Assertions.assertEquals(approvedBudget, project.getApprovedBudget());
    }

    //This method is used to validate the startDate
    @Test
    void testStartDate() {
        Date startDate = new Date();
        project.setStartDate(startDate);
        Assertions.assertEquals(startDate, project.getStartDate());
    }

    //This method is used to validate the endDate
    @Test
    void testEndDate() {
        Date endDate = new Date();
        project.setEndDate(endDate);
        Assertions.assertEquals(endDate, project.getEndDate());
    }

    //This method is used to validate the projectType
    @Test
    void testProjectType() {
        String projectType = "LongTerm";
        project.setProjectType(projectType);
        Assertions.assertEquals(projectType, project.getProjectType());
    }

    //This method is used to validate the projectHours
    @Test
    void testProjectHours() {
        List<ProjectHours> projectHours = new ArrayList<>();
        project.setProjectHours(projectHours);
        Assertions.assertEquals(projectHours, project.getProjectHours());
    }

    //This method is used to validate the max hours
    @Test
    void testMaxNumberHour() {
        Integer hours = 8;
        project.setMaxNumberHours(hours);
        Assertions.assertEquals(hours, project.getMaxNumberHours());

    }

    //This method is used to validate the testAudit
    @Test
    void testAudit() {
        assertNull(project.getAudit());
        Audit expectedAudit = new Audit();
        project.setAudit(expectedAudit);
        assertEquals(expectedAudit, project.getAudit());
    }



    @Test
    public void testStatusEnumValue() {
        Boolean expectedStatus = true;
        project.setDeleted(expectedStatus);
        Boolean actualStatus = project.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }

}
