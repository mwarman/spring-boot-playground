package net.leanstacks.todosvc;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExceptionDetail {

  private HttpStatus httpStatus;

  private String message;

  public ExceptionDetail(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  @Schema(example = "400")
  public int getStatus() {
    return this.httpStatus.value();
  }

  @Schema(example = "Status value description")
  public String getStatusText() {
    return this.httpStatus.getReasonPhrase();
  }

  @Schema(example = "Problem-specific message")
  public String getMessage() {
    return this.message;
  }

}
