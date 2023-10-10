package net.leanstacks.todosvc.todo;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Todo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "title is required")
  @Size(min = 1, max = 100, message = "title must contain 1 to 100 characters")
  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private boolean isComplete;

  protected Todo() {
  }

  public Todo(String title, boolean isComplete) {
    this.title = title;
    this.isComplete = isComplete;
  }

  @Schema(example = "5")
  public Long getId() {
    return this.id;
  }

  @Schema(example = "Document the API")
  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Schema(example = "true")
  public boolean getIsComplete() {
    return this.isComplete;
  }

  public void setIsComplete(boolean isComplete) {
    this.isComplete = isComplete;
  }
}
