package net.leanstacks.todosvc.todo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.leanstacks.todosvc.exception.ExceptionDetail;

@SecurityRequirement(name = "basicAuth")
@Tag(name = "Todos", description = "The API endpoints for Todos.")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @Operation(summary = "List all todos.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)) }),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
  })
  @GetMapping
  public List<Todo> getTodos() {
    logger.info("> getTodos");

    final List<Todo> todos = todoService.findAll();

    logger.info("< getTodos");
    return todos;
  }

  @Operation(summary = "Get a todo by its identifier.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid identifier", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class)))
  })
  @GetMapping("/{id}")
  public Todo getTodo(@Parameter(description = "The todo identifier") @PathVariable("id") Long id) {
    logger.info("> getTodo");
    logger.debug("id: {}", id);

    final Optional<Todo> todo = todoService.find(id);

    logger.info("< getTodo");
    return todo.get();
  }

  @Operation(summary = "Create a todo.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Success", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid request content", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Todo createTodo(@Valid @RequestBody CreateTodoDto todoDto) {
    logger.info("> createTodo");

    final Todo createdTodo = todoService.create(todoDto);

    logger.info("< createTodo");
    return createdTodo;
  }

  @Operation(summary = "Update a todo.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid identifier or body content", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
      @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class)))
  })
  @PutMapping("/{id}")
  public Todo updateTodo(@Parameter(description = "The todo identifier") @PathVariable("id") Long id,
      @RequestBody Todo todo) {
    logger.info("> updateTodo");

    final Optional<Todo> updatedTodo = todoService.update(todo);

    logger.info("< updateTodo");
    return updatedTodo.get();
  }

  @Operation(summary = "Delete a todo.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Success", content = @Content()),
      @ApiResponse(responseCode = "400", description = "Invalid identifier", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetail.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTodo(@Parameter(description = "The todo identifier") @PathVariable("id") Long id) {
    logger.info("> deleteTodo");
    logger.debug("id: {}", id);

    todoService.delete(id);

    logger.info("< deleteTodo");
  }

}
