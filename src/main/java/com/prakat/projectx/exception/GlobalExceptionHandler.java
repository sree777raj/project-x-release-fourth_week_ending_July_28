package com.prakat.projectx.exception;

import com.prakat.projectx.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ControllerAdvice
public class GlobalExceptionHandler {
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);



    /**
     *
     * @param customException
     * @param request
     * @return error code and customException object
     */

    @ExceptionHandler(value = CustomException.class)
    ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException customException,WebRequest request) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(customException.getErrorCode(),
                customException.getErrorMsg(),
                request.getDescription(false),
                customException.getDebugMessage());
        LOGGER.error("Log from the Exception handler: ",customException);
        LOGGER.info(request);
        return ResponseEntity.status(errorResponseDTO.getErrorCode())
                .body(errorResponseDTO);
    }

    /**
     *Exception to be thrown when validation on an argument annotated with @Valid
     * @param ex
     * @param request
     * @return error code and Validation object
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        LOGGER.error("Log from the Exception handler: ",ex);
        LOGGER.info(request);
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<Object>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception thrown when an action would violate a constraint on repository structure
     * @param ex
     * @return error code and Validation object
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            String errorMessage = error.getMessage();
            errors.add(errorMessage);
        });
        LOGGER.error("Log from the Exception handler: ",ex);
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
                );
        LOGGER.error(errors);
        return new ResponseEntity<Object>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     *an exception that is thrown when there is an error caused in binding
      when an application tries to bind a socket to a local address and port
     * @param ex
     * @return error code and Validation object
     */

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex,WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        LOGGER.error("Log from Exception Handling",ex);
        LOGGER.error(errors);
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<Object>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * an exception that is thrown when the URI template does not match
      the path variable name declared on the method parameter
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return Custom exception response object,headers,Http status
     */

    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {

        String error = ex.getParameter() + " parameter is missing";
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false),
                Optional.of("Path variable is incorrect!!"));
        LOGGER.error("Log from the Exception handler: ",ex);
        LOGGER.info(request);
        return new ResponseEntity<Object>(errorResponseDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponseDTO exceptionResponse = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage(),
                request.getDescription(false),
                Optional.of("Internal Server Error"));
        LOGGER.error("Log from the Exception handler: ",ex);
        LOGGER.info(request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * when the DispatcherServlet can't find a handler for a request it sends a 404 response
     * @param ex
     * @param request
     * @return Http status and exception response object
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        ErrorResponseDTO exceptionResponse = new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));
        LOGGER.error("Logging from global exception:",ex);
        LOGGER.info(request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}







