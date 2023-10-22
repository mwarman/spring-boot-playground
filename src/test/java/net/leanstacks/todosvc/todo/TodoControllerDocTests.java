package net.leanstacks.todosvc.todo;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import net.leanstacks.todosvc.security.SecurityProperties;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@AutoConfigureJsonTesters
@ActiveProfiles("ci")
public class TodoControllerDocTests {

  HeaderDescriptor authorizationHeader = HeaderDocumentation.headerWithName("Authorization")
      .description("Contains the credentials to authenticate a user-agent with a server.");
  HeaderDescriptor acceptJson = HeaderDocumentation.headerWithName("Accept")
      .description("Informs the server about the types of data that can be sent back.");
  HeaderDescriptor contentTypeJson = HeaderDocumentation.headerWithName("Content-Type")
      .description("Indicates the media type of the resource.");

  ParameterDescriptor idParameter = RequestDocumentation.parameterWithName("id").description("A todo identifier");

  FieldDescriptor id = PayloadDocumentation.fieldWithPath("id").description("The todo identifier");
  FieldDescriptor title = PayloadDocumentation.fieldWithPath("title").description("The todo title");
  FieldDescriptor body = PayloadDocumentation.fieldWithPath("isComplete")
      .description("Indicates if the todo has been completed");

  FieldDescriptor[] todo = new FieldDescriptor[] { id, title, body };

  private static final String AUTH_USER = "user";
  private String authHeaderValue;

  @MockBean
  private TodoService todoService;

  @Autowired
  private SecurityProperties securityProperties;

  @Autowired
  private JacksonTester<Todo> json;

  @Autowired
  private JacksonTester<CreateTodoDto> createTodoDtoJson;

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  public void beforeEach() {
    String authValue = AUTH_USER + ":" + securityProperties.getUserPassword();
    String authValueEncoded = Base64.getEncoder().encodeToString(authValue.getBytes());
    this.authHeaderValue = "Basic " + authValueEncoded;
  }

  @Test
  @WithMockUser
  void docListTodos() throws Exception {
    // set up the test
    List<Todo> responseTodos = new ArrayList<Todo>();
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(Long.valueOf(1));
    responseTodos.add(responseTodo);
    BDDMockito.given(this.todoService.findAll()).willReturn(responseTodos);

    // invoke the controller
    this.mvc
        .perform(
            RestDocumentationRequestBuilders.get("/api/todos")
                .header("Authorization", authHeaderValue)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("list-todos",
            HeaderDocumentation.requestHeaders(authorizationHeader, acceptJson),
            PayloadDocumentation
                .responseFields(
                    PayloadDocumentation.fieldWithPath("[]").description("An array of todos"))
                .andWithPrefix("[].", todo)));
  }

  @Test
  @WithMockUser
  void docGetTodo() throws Exception {
    // set up the test
    Long todoId = Long.valueOf(1);
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(todoId);
    BDDMockito.given(this.todoService.find(todoId)).willReturn(Optional.of(responseTodo));

    // invoke the controller
    this.mvc
        .perform(RestDocumentationRequestBuilders
            .get("/api/todos/{id}", todoId)
            .header("Authorization", authHeaderValue)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("get-todo",
            HeaderDocumentation.requestHeaders(authorizationHeader, acceptJson),
            RequestDocumentation.pathParameters(idParameter),
            PayloadDocumentation.responseFields(todo)));
  }

  @Test
  @WithMockUser
  void docCreateTodo() throws Exception {
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
        .perform(
            RestDocumentationRequestBuilders.post("/api/todos")
                .header("Authorization", authHeaderValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("create-todo",
            HeaderDocumentation.requestHeaders(authorizationHeader, acceptJson, contentTypeJson),
            PayloadDocumentation.requestFields(title), PayloadDocumentation.responseFields(todo)));
  }

  @Test
  @WithMockUser
  void docUpdateTodo() throws Exception {
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
            .put("/api/todos/{id}", requestId)
            .header("Authorization", authHeaderValue)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("update-todo",
            HeaderDocumentation.requestHeaders(authorizationHeader, acceptJson, contentTypeJson),
            RequestDocumentation.pathParameters(idParameter),
            PayloadDocumentation.requestFields(todo), PayloadDocumentation.responseFields(todo)));
  }

  @Test
  @WithMockUser
  void docDeleteTodo() throws Exception {
    // set up the test
    Long requestId = Long.valueOf(1);

    BDDMockito.doNothing().when(this.todoService).delete(requestId);

    // invoke the controller
    this.mvc
        .perform(RestDocumentationRequestBuilders.delete("/api/todos/{id}", requestId)
            .header("Authorization", authHeaderValue))
        .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()))
        .andDo(MockMvcRestDocumentation.document("delete-todo",
            HeaderDocumentation.requestHeaders(authorizationHeader),
            RequestDocumentation.pathParameters(idParameter)));
  }
}
