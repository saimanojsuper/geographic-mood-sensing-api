package com.moodsensor.stem.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
      MissingServletRequestParameterException.class})
  public ResponseEntity<Object> handleRequestParamValueValidationException(Exception ex) {
    log.error("Invalid request parameter value", ex);
    Map<String,Object> body = new HashMap<>();
    body.put("message", "Invalid request parameter value");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(body);
  }

  @ExceptionHandler({InvalidUserException.class})
  public ResponseEntity<Object> handleInvalidUserException(Exception ex) {
    log.error("Exception from service layer: " + ex.getMessage(), ex);
    Map<String,Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleUnknownException(Exception ex) {
    log.error("Unknown exception from underlying layers: ", ex);
    Map<String,Object> body = new HashMap<>();
    body.put("message", "Something went wrong, please try again after sometime");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(body);
  }

}

