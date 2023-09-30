package net.leanstacks.todo.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.leanstacks.todo.model.Todo;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/{id}")
  public Todo getTodo(@PathVariable long id) {
    return new Todo(counter.incrementAndGet(), "Do this not that.", false);
  }

}
