package com.prakat.projectx.exception;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Optional;

public class CustomExceptionTest {

    private CustomException customException;

    @Test
    public void test_get_CustomException() {
        String expectedErrorMsg = "Error message";
        int expectedErrorCode = 500;
        Optional<String> expectedDebugMessage = Optional.of("Debug message");

        CustomException exception = new CustomException(expectedErrorMsg, expectedErrorCode, expectedDebugMessage);

        Assert.assertEquals(expectedErrorMsg, exception.getErrorMsg());
        Assert.assertEquals(expectedErrorCode, exception.getErrorCode());
        Assert.assertEquals(expectedDebugMessage, exception.getDebugMessage());
    }

    @Test
    public void test_set_CustomException() {
        String expectedErrorMsg = "Error message";
        int expectedErrorCode = 500;
        Optional<String> expectedDebugMessage = Optional.of("Debug message");

        CustomException customException = new CustomException(expectedErrorMsg, expectedErrorCode, expectedDebugMessage);

        customException.setErrorMsg(expectedErrorMsg);
        customException.setErrorCode(expectedErrorCode);
        customException.setDebugMessage(expectedDebugMessage);

        Assert.assertEquals(expectedErrorMsg, customException.getErrorMsg());
        Assert.assertEquals(expectedErrorCode, customException.getErrorCode());
        Assert.assertEquals(expectedDebugMessage, customException.getDebugMessage());
    }



    @Test
    public void testCustomException() {
        String expectedErrorMsg = "Error message";
        int expectedErrorCode = 500;
        Optional<String> expectedDebugMessage = Optional.of("Debug message");

        CustomException exceptionMock = Mockito.mock(CustomException.class);

        // Set up the expected behavior of the mocked exception
        Mockito.when(exceptionMock.getErrorMsg()).thenReturn(expectedErrorMsg);
        Mockito.when(exceptionMock.getErrorCode()).thenReturn(expectedErrorCode);
        Mockito.when(exceptionMock.getDebugMessage()).thenReturn(expectedDebugMessage);

        Assert.assertEquals(expectedErrorMsg, exceptionMock.getErrorMsg());
        Assert.assertEquals(expectedErrorCode, exceptionMock.getErrorCode());
        Assert.assertEquals(expectedDebugMessage, exceptionMock.getDebugMessage());
    }
}
