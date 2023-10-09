package net.leanstacks.todosvc;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    System.out.println("handleNoSuchElement");
    HttpStatus status = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    System.out.println("handleMethodArgumentNotValid");
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StringBuilder messageBuilder = new StringBuilder("");
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      messageBuilder.append(((FieldError) error).getField()).append(": ");
      messageBuilder.append(((FieldError) error).getDefaultMessage()).append(". ");
    });
    String message = messageBuilder.toString().trim();
    return new ResponseEntity<>(new ExceptionDetail(status, message), status);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleUnknown(RuntimeException ex) {
    System.out.println("handleUnknown");
    System.out.println(ex.getClass());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

}
