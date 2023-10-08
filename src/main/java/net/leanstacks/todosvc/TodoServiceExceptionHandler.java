package net.leanstacks.todosvc;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class TodoServiceExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<?> handleNoSuchElement(RuntimeException ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleException(RuntimeException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

}
