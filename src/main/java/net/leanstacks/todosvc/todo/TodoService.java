package net.leanstacks.todosvc.todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

  Optional<Todo> find(Long id);

  List<Todo> findAll();

  Todo create(String title);

  Optional<Todo> update(Todo todo);

}
