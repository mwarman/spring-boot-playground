package net.leanstacks.todosvc.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class TodoControllerDocTests {

  ParameterDescriptor idParameter = RequestDocumentation.parameterWithName("id").description("A todo identifier");

  FieldDescriptor id = PayloadDocumentation.fieldWithPath("id").description("The todo identifier");
  FieldDescriptor title = PayloadDocumentation.fieldWithPath("title").description("The todo title");
  FieldDescriptor body = PayloadDocumentation
      .fieldWithPath("isComplete").description("Indicates if the todo has been completed");

  FieldDescriptor[] todo = new FieldDescriptor[] { id, title, body };

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TodoService todoService;

  @Test
  void listTodos() throws Exception {
    // mock the service interaction
    List<Todo> responseTodos = new ArrayList<Todo>();
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(Long.valueOf(1));
    responseTodos.add(responseTodo);
    BDDMockito.given(this.todoService.findAll()).willReturn(responseTodos);

    // invoke the controller
    this.mvc.perform(RestDocumentationRequestBuilders.get("/api/todos").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("list-todos", PayloadDocumentation
            .responseFields(PayloadDocumentation.fieldWithPath("[]").description("An array of todos"))
            .andWithPrefix("[].", todo)));
  }

  @Test
  void getTodo() throws Exception {
    // mock the service interaction
    Long todoId = Long.valueOf(1);
    Todo responseTodo = new Todo("Document an API", false);
    responseTodo.setId(todoId);
    BDDMockito.given(this.todoService.find(todoId)).willReturn(Optional.of(responseTodo));

    // invoke the controller
    this.mvc.perform(RestDocumentationRequestBuilders.get("/api/todos/{id}", todoId).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcRestDocumentation.document("get-todo", RequestDocumentation.pathParameters(idParameter),
            PayloadDocumentation
                .responseFields(todo)));
  }
}
