package net.leanstacks.todosvc.todo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceBean implements TodoService {

  private static final Logger logger = LoggerFactory.getLogger(TodoServiceBean.class);
  private final TodoRepository todoRepository;

  @Autowired
  public TodoServiceBean(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public Optional<Todo> find(Long id) {
    logger.info("> find");
    logger.debug("id: {}", id);

    final Optional<Todo> todo = todoRepository.findById(id);

    logger.info("< find");
    return todo;
  }

  @Override
  public List<Todo> findAll() {
    logger.info("> findAll");

    final List<Todo> todos = todoRepository.findAll();

    logger.info("< findAll");
    return todos;
  }

  @Override
  public Todo create(String title) {
    logger.info("> create");

    final Todo savedTodo = todoRepository.save(new Todo(title, false));

    logger.info("< create");
    return savedTodo;
  }

  @Override
  public Optional<Todo> update(Todo todo) {
    logger.info("> update");
    Todo updatedTodo = null;

    Optional<Todo> savedTodo = this.find(todo.getId());
    if (savedTodo.isPresent()) {
      logger.debug("Todo found. updating.");
      Todo todoToUpdate = savedTodo.get();
      todoToUpdate.setTitle(todo.getTitle());
      todoToUpdate.setIsComplete(todo.getIsComplete());
      updatedTodo = todoRepository.save(todoToUpdate);
    }

    logger.info("< update");
    return Optional.ofNullable(updatedTodo);
  }

  @Override
  public void delete(Long id) {
    logger.info("> delete");

    todoRepository.deleteById(id);

    logger.info("< delete");
  }

}
