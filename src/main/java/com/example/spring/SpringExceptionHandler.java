package com.example.spring;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorMessage {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  private final ZonedDateTime date = ZonedDateTime.now();
  private String code;
  private String message;
  private String origin;
  private final Map<String, Object> data = new HashMap<>();
}

@RestControllerAdvice
public class SpringExceptionHandler extends ResponseEntityExceptionHandler {

  ////////////////////////////////////////////////////////////////////////////////////////////
  // SPRING
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorMessage> handleAccessDeniedException(Exception ex, WebRequest request) {
    ErrorMessage errorDetails = new ErrorMessage("SECURITY", ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}