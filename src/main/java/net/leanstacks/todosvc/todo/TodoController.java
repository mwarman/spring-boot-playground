package net.leanstacks.todosvc.todo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
  private final TodoService todoService;

  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @GetMapping
  public List<Todo> getTodos() {
    logger.info("> getTodos");

    final List<Todo> todos = todoService.findAll();

    logger.info("< getTodos");
    return todos;
  }

  @GetMapping("/{id}")
  public Todo getTodo(@PathVariable("id") Long id) {
    logger.info("> getTodo");
    logger.debug("id: {}", id);

    final Optional<Todo> todo = todoService.find(id);

    if (!todo.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    logger.info("< getTodo");
    return todo.get();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Todo createTodo(@RequestBody Todo todo) {
    logger.info("> createTodo");

    final Todo createdTodo = todoService.create(todo.getTitle());

    logger.info("< createTodo");
    return createdTodo;
  }

  @PutMapping("/{id}")
  public Todo updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
    logger.info("> updateTodo");

    final Optional<Todo> updatedTodo = todoService.update(todo);
    if (!updatedTodo.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    logger.info("< updateTodo");
    return updatedTodo.get();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTodo(@PathVariable("id") Long id) {
    logger.info("> deleteTodo");
    logger.debug("id: {}", id);

    todoService.delete(id);

    logger.info("< deleteTodo");
  }

}
