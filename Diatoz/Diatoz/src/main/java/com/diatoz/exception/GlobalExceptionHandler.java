package com.diatoz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for MethodArgumentNotValidException.
     * Handles validation errors and returns an error response with details.
     *
     * @param methodArgumentNotValidException the exception thrown for method argument validation errors
     * @return ResponseEntity containing an ErrorDetails object with validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorDetails errorDetail = new ErrorDetails("Validation error",
                methodArgumentNotValidException
                        .getBindingResult()
                        .getFieldError()
                        .getDefaultMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for generic Exception.
     * Handles any unhandled exceptions and returns an error response with details.
     *
     * @param e           the exception that occurred
     * @param webRequest  the web request associated with the exception
     * @return ResponseEntity containing an ErrorDetails object with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> anyExceptionHandlerMethod(Exception e, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for UserLocationException.
     * Handles custom UserLocationException and returns an error response with details.
     *
     * @param e           the UserLocationException that occurred
     * @param webRequest  the web request associated with the exception
     * @return ResponseEntity containing an ErrorDetails object with error details
     */
    @ExceptionHandler(DetailsNotFoundException.class)
    public ResponseEntity<ErrorDetails> userLocationExceptionHandler(DetailsNotFoundException e, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for RuntimeException.
     * Handles generic runtime exceptions and returns an error response with details.
     *
     * @param e           the runtime exception that occurred
     * @param webRequest  the web request associated with the exception
     * @return ResponseEntity containing an ErrorDetails object with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> anyExceptionHandlerMethod(RuntimeException e, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for ProjectException.
     * Handles custom ProjectException and returns an error response with details.
     *
     * @param e           the ProjectException that occurred
     * @param webRequest  the web request associated with the exception
     * @return ResponseEntity containing an ErrorDetails object with error details
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(UserNotFoundException e, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for IllegalArgumentException.
     * Handles illegal argument exceptions and returns an error response with details.
     *
     * @param e           the illegal argument exception that occurred
     * @param webRequest  the web request associated with the exception
     * @return ResponseEntity containing an ErrorDetails object with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(IllegalArgumentException e, WebRequest webRequest) {
        ErrorDetails errorDetail = new ErrorDetails(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


}
