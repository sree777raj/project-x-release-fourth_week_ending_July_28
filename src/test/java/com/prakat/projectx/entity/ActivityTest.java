package com.prakat.projectx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityTest {
    private Activity activity;

    //This method will be executed before running each test case.
    @BeforeEach
    void setUp() {
        activity = new Activity();
    }

    //this test case for activity id
    @Test
    void testActivityId() {
        Assertions.assertNull(activity.getActivityId());
        Long expectedId = 1L;
        activity.setActivityId(expectedId);
        Assertions.assertEquals(expectedId, activity.getActivityId());
    }

    //This method is used to validate the ActivityType.
    @Test
    void testActivityType() {
        Assertions.assertNull(activity.getActivityType());
        String expectedType = "SomeActivity";
        activity.setActivityType(expectedType);
        Assertions.assertEquals(expectedType, activity.getActivityType());
    }

    //This method is used to validate the testAudit
    @Test
    void testAudit() {
        Assertions.assertNull(activity.getAudit());
        Audit expectedAudit = new Audit();
        activity.setAudit(expectedAudit);
        Assertions.assertEquals(expectedAudit, activity.getAudit());
    }
    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        activity.setDeleted(expectedStatus);
        Boolean actualStatus = activity.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }
}
