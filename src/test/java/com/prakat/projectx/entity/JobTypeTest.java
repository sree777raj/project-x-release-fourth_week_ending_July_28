package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * This class used for JobTypeTest validation
 */

@SpringBootTest
class JobTypeTest {
    private JobType jobType;

    //This method will be executed before running each test case.
    @BeforeEach
    void setUp() {
        jobType = new JobType();
    }

    //This method is used to validate the JobId.
    @Test
    void testJobId() {
        Assertions.assertNull(jobType.getJobId());
        Long jobId = 1L;
        jobType.setJobId(jobId);
        Assertions.assertEquals(jobId, jobType.getJobId());
    }

    //This method is used to validate the JobType.
    @Test
    void testJobType() {
        Assertions.assertNull(jobType.getJobType());
        String jobTypeName = "Part-Time";
        jobType.setJobType(jobTypeName);
        Assertions.assertEquals(jobTypeName, jobType.getJobType());
    }

    //This method is used to validate the Audit Mapping one to one.
    @Test
    void testAudit() {
        Assertions.assertNull(jobType.getAudit());
        Audit expectedAudit = new Audit();
        jobType.setAudit(expectedAudit);
        Assertions.assertEquals(expectedAudit, jobType.getAudit());
    }



    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        jobType.setDeleted(expectedStatus);
        Boolean actualStatus = jobType.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }


}