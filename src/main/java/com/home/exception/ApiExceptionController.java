package com.home.exception;

import static java.util.stream.Collectors.joining;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> allExceptionHandler(Exception ex, WebRequest webRequest) {
    ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
        webRequest.getDescription(false));
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> usernameNotFoundExceptionHandler(Exception ex, WebRequest webRequest) {
    ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
        webRequest.getDescription(false));
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DuplicateMemberException.class)
  public ResponseEntity<?> duplicateMemberFoundExceptionHandler(Exception ex,
      WebRequest webRequest) {
    ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
        webRequest.getDescription(false));
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(IdPasswordNotMatchingException.class)
  public ResponseEntity<?> idPasswordNotMatchingException(Exception e, WebRequest w) {
    ExceptionResponse response =
        new ExceptionResponse(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    String errors =
        fieldErrors.stream().map(error -> "\"" + error.getField() +"\"" + ":" + "\"" + error.getDefaultMessage() + "\"")
            .collect(joining(","));
    ExceptionResponse response =
        new ExceptionResponse(LocalDateTime.now(), "validation error", "{" + errors + "}");
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
