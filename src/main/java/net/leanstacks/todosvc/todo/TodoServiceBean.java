package net.leanstacks.todosvc.todo;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceBean implements TodoService {

  private static final Logger logger = LoggerFactory.getLogger(TodoServiceBean.class);
  private final AtomicLong counter = new AtomicLong();

  @Override
  public Todo find(long id) {
    logger.info("> find");
    logger.debug("id: {}", id);

    final Todo todo = new Todo(counter.incrementAndGet(), "Do this not that.", false);

    logger.info("< find");
    return todo;
  }

  @Override
  public List<Todo> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Todo create(String title) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Todo update(Todo todo) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

}
