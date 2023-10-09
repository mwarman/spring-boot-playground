package net.leanstacks.todosvc.todo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Create Todo Request", description = "The request body payload for creating a new todo.")
public class CreateTodoDto implements Serializable {

  @NotBlank(message = "title is required")
  @Size(min = 0, max = 100)
  private String title;

  public CreateTodoDto(String title) {
    this.title = title;
  }

  @Schema(example = "Document the API")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}