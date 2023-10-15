package net.leanstacks.todosvc.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<?> handleNoSuchElement(RuntimeException ex) {
    logger.info("> handleNoSuchElement");
    HttpStatus status = HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    logger.info("> handleMethodArgumentNotValid");
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
    logger.info("> handleUnknown");
    logger.info("- {}", ex.getClass());
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<>(new ExceptionDetail(status, ex.getMessage()), status);
  }

}