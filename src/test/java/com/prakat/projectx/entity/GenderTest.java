package com.prakat.projectx.entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class GenderTest {

    @Test
    public void testSetAndGetGenderId() {
        Gender gender = new Gender();
        Character expectedGenderId = 'M';

        gender.setGenderId(expectedGenderId);
        Character actualGenderId = gender.getGenderId();

        Assertions.assertEquals(expectedGenderId, actualGenderId);
    }

    @Test
    void testGender() {
        Gender gender = new Gender();
        gender.setGender("Male");

        Assertions.assertEquals("Male", gender.getGender());
    }





    @Test
    public void testDeleted() {
        Boolean expectedStatus = true;
        Gender gender = new Gender();
        gender.setDeleted(expectedStatus);
        Boolean actualStatus = gender.isDeleted();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }
}