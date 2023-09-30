package net.leanstacks.todosvc.todo;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/{id}")
  public Todo getTodo(@PathVariable long id) {
    logger.info("> getTodo");
    logger.debug("id: {}", id);

    final Todo todo = new Todo(counter.incrementAndGet(), "Do this not that.", false);

    logger.info("< getTodo");
    return todo;
  }

}
