package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest

class AuditTest {
@Mock
    //declaring Audit entity global
    private Audit audit;

    //This method will be executed before running each test case.
    @BeforeEach
    void setUp() {
        audit = new Audit();
    }

    //This method will verify the primary key for audit purposes.
    @Test
    public void testAuditId() {
        Assertions.assertNull(audit.getAuditId());
        audit.setAuditId(1L);
        Assertions.assertEquals(1L, audit.getAuditId());
    }

    //This method is used to validate the CreatedByEmpId
    @Test
    public void testCreatedByEmpId() {
        Assertions.assertNull(audit.getCreatedByEmpId());
        audit.setCreatedByEmpId(123L);
        Assertions.assertEquals(123L, audit.getCreatedByEmpId());
    }

    //This method is used to validate the CreatedOn
    @Test
    public void testCreatedOn() {
        Assertions.assertNull(audit.getCreatedOn());
        LocalDate createdOn = LocalDate.now();
        audit.setCreatedOn(createdOn);
        Assertions.assertEquals(createdOn, audit.getCreatedOn());
    }

    //This method is used to validate the UpdatedByEmpId
    @Test
    public void testUpdatedByEmpId() {
        Assertions.assertNull(audit.getUpdatedByEmpId());
        audit.setUpdatedByEmpId(456L);
        Assertions.assertEquals(456L, audit.getUpdatedByEmpId());
    }

    //This method is used to validate the UpdatedOn
    @Test
    public void testUpdatedOn() {
        LocalDateTime updatedOn = LocalDateTime.now();
        audit.setUpdatedOn(updatedOn);
        Assertions.assertEquals(updatedOn, audit.getUpdatedOn());
    }

    //This method is used to validate the ProcessName
    @Test
    public void testProcessName() {
        Assertions.assertNull(audit.getProcessName());
        audit.setProcessName("SomeProcess");
        Assertions.assertEquals("SomeProcess", audit.getProcessName());
    }

    //This method is used to validate the CreatedByDbUser
    @Test
    public void testCreatedByDbUser() {
        Assertions.assertNull(audit.getCreatedByDbUser());
        audit.setCreatedByDbUser(789L);
        Assertions.assertEquals(789L, audit.getCreatedByDbUser());
    }

    //This method is used to validate the UpdatedByDbUser.
    @Test
    public void testUpdatedByDbUser() {
        Assertions.assertNull(audit.getUpdatedByDbUser());
        audit.setUpdatedByDbUser(101L);
        Assertions.assertEquals(101L, audit.getUpdatedByDbUser());
    }
}
