package com.prakat.projectx.entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmploymentTypeTest {
    private EmploymentType employmentType;

    //This method will be executed before running each test case.
    @BeforeEach
    void setUp() {
        employmentType = new EmploymentType();
    }

    //this test case for employeeId.
    @Test
    void testEmployeeId() {
        Assertions.assertNull(employmentType.getId());
        Long expectedId = 1L;
        employmentType.setId(expectedId);
        Assertions.assertEquals(expectedId, employmentType.getId());
    }

    //This method is used to validate the employmentType.
    @Test
    void testEmploymentType() {
        Assertions.assertNull(employmentType.getEmploymentType());
        String employmentTypeName = "Part-time employment";
        employmentType.setEmploymentType(employmentTypeName);
        Assertions.assertEquals(employmentTypeName, employmentType.getEmploymentType());
    }

    //This method is used to validate the employmentAudit mapping.
    @Test
    void testAudit() {
        Assertions.assertNull(employmentType.getAudit());
        Audit expectedAudit = new Audit();
        employmentType.setAudit(expectedAudit);
        Assertions.assertEquals(expectedAudit, employmentType.getAudit());
    }

    @Test
    public void testStatusEnumValue() {
        Boolean expectedStatus = true;
        employmentType.setDeleted(expectedStatus);
        Boolean actualStatus = employmentType.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }


}