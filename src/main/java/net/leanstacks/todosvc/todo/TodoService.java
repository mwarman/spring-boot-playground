package net.leanstacks.todosvc.todo;

import java.util.List;

public interface TodoService {

  Todo find(long id);

  List<Todo> findAll();

  Todo create(String title);

  Todo update(Todo todo);

}
