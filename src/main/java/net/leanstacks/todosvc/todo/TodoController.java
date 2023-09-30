package net.leanstacks.todosvc.todo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public Todo getTodo(@PathVariable("id") long id) {
    logger.info("> getTodo");
    logger.debug("id: {}", id);

    final Todo todo = todoService.find(id);

    logger.info("< getTodo");
    return todo;
  }

  @PostMapping
  public Todo createTodo(@RequestBody Todo todo) {
    logger.info("> createTodo");

    final Todo createdTodo = todoService.create(todo.title());

    logger.info("< createTodo");
    return createdTodo;
  }

  @PutMapping("/{id}")
  public Todo updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
    logger.info("> updateTodo");

    final Todo updatedTodo = todoService.update(todo);

    logger.info("< updateTodo");
    return updatedTodo;
  }

}
