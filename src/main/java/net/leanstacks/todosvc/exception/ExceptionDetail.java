package net.leanstacks.todosvc.exception;

import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotBlank;

public class ExceptionDetail {

  private HttpStatus httpStatus;

  private String message;

  public ExceptionDetail(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @NotBlank
  public int getStatus() {
    return this.httpStatus.value();
  }

  public String getStatusText() {
    return this.httpStatus.getReasonPhrase();
  }

  public String getMessage() {
    return this.message;
  }

}