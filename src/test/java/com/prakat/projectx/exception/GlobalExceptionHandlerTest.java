package com.prakat.projectx.exception;

import com.prakat.projectx.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.lang.reflect.Method;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public final class GlobalExceptionHandlerTest {

    @Mock
    private WebRequest webRequestMock;
    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;
    @Mock
    private CustomException customException;

    @BeforeEach
    void setup() {
        when(customException.getErrorMsg()).thenReturn("Custom error message");
        when(customException.getErrorCode()).thenReturn(500);
        when(customException.getDebugMessage()).thenReturn(Optional.of("Custom debug message"));
    }



    @Test
    public void handleCustomException_ReturnsExpectedResponse() {

        CustomException customException = new CustomException("Error message", HttpStatus.INTERNAL_SERVER_ERROR.value(), Optional.of("Debug message"));
        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ResponseEntity<ErrorResponseDTO> responseEntity = exceptionHandler.handleCustomException(customException, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error message", responseEntity.getBody().getErrorMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getErrorCode());
    }


    @Test
    public void handleMethodArgumentNotValidException_ReturnsExpectedResponse() throws NoSuchMethodException {
        // Arrange
        List<ObjectError> errors = new ArrayList<>();
        errors.add(new FieldError("objectName", "fieldName", "Error message 1"));
        errors.add(new ObjectError("objectName", "Error message 2"));

        BindingResult bindingResult = mock(BindingResult.class);
        bindingResult.getAllErrors().addAll(errors);

        Method method = MyController.class.getMethod("myMethod", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ErrorResponseDTO errorResponse = (ErrorResponseDTO) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getErrorCode());
       // assertEquals("Validation failed for " + request, errorResponse.getErrorMsg());
        assertEquals(400, errorResponse.getErrorCode());
    }

    // Sample controller or service class
    static class MyController {
        public String myMethod(String parameter) {
            // Method implementation
            if (parameter == null || parameter.isEmpty()) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
            return "Success";
        }
    }


    @Test
    public void testHandleConstraintViolationException() {
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        WebRequest request = mock(WebRequest.class);

        when(exception.getConstraintViolations()).thenReturn(new HashSet<>());

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolationException(exception,request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void testHandleBindException() {
        BindingResult bindingResult = new BeanPropertyBindingResult(null, "target");
        BindException exception = new BindException(bindingResult);
        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        ResponseEntity<Object> response = exceptionHandler.handleBindException(exception,request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void testHandleMissingPathVariable() {
        MissingPathVariableException exception = mock(MissingPathVariableException.class);
        HttpHeaders headers = mock(HttpHeaders.class);
        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = exceptionHandler.handleMissingPathVariable(exception, headers, HttpStatus.BAD_REQUEST, request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    public void testHandleAllExceptions() {
        Exception exception = new Exception();
        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(exception, request);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testHandleNoHandlerFoundException() {
        NoHandlerFoundException exception = mock(NoHandlerFoundException.class);
        WebRequest request = mock(WebRequest.class);

        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = exceptionHandler.handleNoHandlerFoundException(exception, request);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}