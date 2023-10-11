package net.leanstacks.todosvc.todo;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TodoController.class)
@AutoConfigureRestDocs
@AutoConfigureJsonTesters
public class TodoControllerDocTests {

  ParameterDescriptor idParameter = RequestDocumentation.parameterWithName("id").description("A todo identifier");

  FieldDescriptor id = PayloadDocumentation.fieldWithPath("id").description("The todo identifier");
  FieldDescriptor title = PayloadDocumentation.fieldWithPath("title").description("The todo title");
  FieldDescriptor body = PayloadDocumentation.fieldWithPath("isComplete")
      .description("Indicates if the todo has been completed");

  FieldDescriptor[] todo = new FieldDescriptor[] { id, title, body };

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Todo> json;

  @Autowired
  private JacksonTester<CreateTodoDto> createTodoDtoJson;

  @MockBean
  private TodoService todoService;

  @Test
  void listTodos() throws Exception {
    // set up the test
    List<Todo> responseTodos = new ArrayList<Todo>();
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(Long.valueOf(1));
    responseTodos.add(responseTodo);
    BDDMockito.given(this.todoService.findAll()).willReturn(responseTodos);

    // invoke the controller
    this.mvc
        .perform(
            RestDocumentationRequestBuilders.get("/api/todos").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("list-todos",
            PayloadDocumentation
                .responseFields(
                    PayloadDocumentation.fieldWithPath("[]").description("An array of todos"))
                .andWithPrefix("[].", todo)));
  }

  @Test
  void getTodo() throws Exception {
    // set up the test
    Long todoId = Long.valueOf(1);
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(todoId);
    BDDMockito.given(this.todoService.find(todoId)).willReturn(Optional.of(responseTodo));

    // invoke the controller
    this.mvc
        .perform(RestDocumentationRequestBuilders
            .get("/api/todos/{id}", todoId).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("get-todo",
            RequestDocumentation.pathParameters(idParameter),
            PayloadDocumentation.responseFields(todo)));
  }

  @Test
  void createTodo() throws Exception {
    // set up the test
    CreateTodoDto requestTodo = new CreateTodoDto("Document an API");
    String requestBody = this.createTodoDtoJson.write(requestTodo).getJson();

    Long responseId = Long.valueOf(1);
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(responseId);

    // BDDMockito.given(this.todoService.create(requestTodo)).willReturn(responseTodo);
    BDDMockito.given(this.todoService.create(any(CreateTodoDto.class)))
        .willReturn(responseTodo);

    // invoke the controller
    this.mvc
        .perform(RestDocumentationRequestBuilders.post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("create-todo",
            PayloadDocumentation.requestFields(title), PayloadDocumentation.responseFields(todo)));
  }

  @Test
  void updateTodo() throws Exception {
    // set up the test
    Long requestId = Long.valueOf(1);
    Todo requestTodo = new Todo("Document an API", true);
    requestTodo.setId(requestId);
    String requestBody = this.json.write(requestTodo).getJson();

    Long responseId = Long.valueOf(1);
    Todo responseTodo = new Todo("Document an API", true);
    responseTodo.setId(responseId);

    BDDMockito.given(this.todoService.update(any(Todo.class)))
        .willReturn(Optional.of(responseTodo));

    // invoke the controller
    this.mvc
        .perform(RestDocumentationRequestBuilders
            .put("/api/todos/{id}", requestId).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("update-todo",
            RequestDocumentation.pathParameters(idParameter),
            PayloadDocumentation.requestFields(todo), PayloadDocumentation.responseFields(todo)));
  }

  @Test
  void deleteTodo() throws Exception {
    // set up the test
    Long requestId = Long.valueOf(1);

    BDDMockito.doNothing().when(this.todoService).delete(requestId);

    // invoke the controller
    this.mvc.perform(RestDocumentationRequestBuilders.delete("/api/todos/{id}", requestId))
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
        .andDo(MockMvcRestDocumentation.document("delete-todo",
            RequestDocumentation.pathParameters(idParameter)));
  }
}
