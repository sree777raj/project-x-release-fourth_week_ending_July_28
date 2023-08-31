package com.prakat.projectx.exception;

import com.prakat.projectx.dto.ErrorResponseDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
class ExceptionResponseTest {

    @Test
    void test_get_ExceptionResponseTest(){
        String expectedErrorMsg = "Error message";
        int expectedErrorCode = 500;
        String expectedWebRequest = "Web Error";
        Optional<String> debug=Optional.of("Debug message");
       // String expectedtimestamp = "timestamp Error";

        ErrorResponseDTO response = new ErrorResponseDTO(expectedErrorCode,expectedErrorMsg,expectedWebRequest,debug);
        Assert.assertEquals(expectedErrorMsg,response.getErrorMessage());
        Assert.assertEquals(expectedErrorCode,response.getErrorCode());
        Assert.assertEquals(expectedWebRequest,response.getWebRequest());
       // Assert.assertEquals(expectedtimestamp,response.getTimeStamp());

    }

    @Test
    void test_set_ExceptionResponseTest(){
        String expectedErrorMsg = "Error message";
        int expectedErrorCode = 500;
        String expectedWebRequeste = "Web Error";
        String expectedtimestamp = "timestamp Error";
        Optional<String> debug=Optional.of("Debug message");

        ErrorResponseDTO response = new ErrorResponseDTO(expectedErrorCode,expectedErrorMsg,expectedWebRequeste,debug);

        response.setErrorMessage(expectedErrorMsg);
        response.setErrorCode(expectedErrorCode);
        response.setWebRequest(expectedWebRequeste);
        //response.setTimeStamp(expectedtimestamp);


        Assert.assertEquals(expectedErrorMsg,response.getErrorMessage());
        Assert.assertEquals(expectedErrorCode,response.getErrorCode());
        Assert.assertEquals(expectedWebRequeste,response.getWebRequest());
       // Assert.assertEquals(expectedtimestamp,response.getTimeStamp());
    }

}