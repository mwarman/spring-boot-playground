package net.leanstacks.todosvc.todo;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("ci")
public class TodoControllerTests {

  @Autowired
  private JacksonTester<Todo> json;

  @Autowired
  private TodoService todoService;

  @Test
  void testGetTodos(@Autowired MockMvc mvc) throws Exception {
    String url = "/api/todos";
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)).andReturn();

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    Assertions.assertThat(result.getResponse().getContentAsString().length()).isGreaterThan(0);
  }

  @Test
  void testGetTodo(@Autowired MockMvc mvc) throws Exception {
    String url = "/api/todos/{id}";
    Long id = Long.valueOf(1);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url, id).accept(MediaType.APPLICATION_JSON))
        .andReturn();

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    Todo responseTodo = this.json.parseObject(result.getResponse().getContentAsString());
    Assertions.assertThat(responseTodo.getId()).isEqualTo(id);
  }

  @Test
  void testCreateTodo(@Autowired MockMvc mvc) throws Exception {
    String url = "/api/todos";
    Todo requestTodo = new Todo();
    requestTodo.setTitle("Run tests");
    String requestBody = this.json.write(requestTodo).getJson();

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    Assertions.assertThat(result.getResponse().getHeader(HttpHeaders.CONTENT_TYPE))
        .isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    Todo responseTodo = this.json.parseObject(result.getResponse().getContentAsString());
    Assertions.assertThat(responseTodo.getId()).isNotNull();
    Assertions.assertThat(responseTodo.getTitle()).isEqualTo(requestTodo.getTitle());
    Assertions.assertThat(responseTodo.getIsComplete()).isFalse();
  }

  @Test
  void testUpdateTodo(@Autowired MockMvc mvc) throws Exception {
    String url = "/api/todos/{id}";
    Long id = Long.valueOf(1);
    String title = "Update test";
    Todo requestTodo = todoService.find(id).get();
    requestTodo.setTitle(title);
    requestTodo.setIsComplete(true);
    String requestBody = this.json.write(requestTodo).getJson();

    MvcResult result = mvc.perform(MockMvcRequestBuilders.put(url, id).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).content(requestBody)).andReturn();

    Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    Assertions.assertThat(result.getResponse().getHeader(HttpHeaders.CONTENT_TYPE))
        .isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    Todo responseTodo = this.json.parseObject(result.getResponse().getContentAsString());
    Assertions.assertThat(responseTodo.getId()).isEqualTo(id);
    Assertions.assertThat(responseTodo.getTitle()).isEqualTo(title);
    Assertions.assertThat(responseTodo.getIsComplete()).isTrue();
  }

  @Test
  void testDeleteTodo(@Autowired MockMvc mvc) throws Exception {
    String url = "/api/todos/{id}";
    Long id = Long.valueOf(1);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(url, id)).andReturn();

    Assertions.assertThat(result.getResponse().getStatus())
        .isEqualTo(HttpStatus.NO_CONTENT.value());
    Assertions.assertThat(result.getResponse().getContentAsString().length()).isEqualTo(0);

    Optional<Todo> deletedTodo = todoService.find(id);
    Assertions.assertThat(deletedTodo.isPresent()).isFalse();
  }

}
