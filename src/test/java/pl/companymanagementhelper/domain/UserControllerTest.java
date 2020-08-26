package pl.companymanagementhelper.domain;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.companymanagementhelper.config.UserRole;
import pl.companymanagementhelper.domain.address.Address;
import pl.companymanagementhelper.domain.user.User;
import pl.companymanagementhelper.domain.user.UserController;
import pl.companymanagementhelper.domain.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserRepository userRepository;

  private List<User> userList;

  @BeforeEach
  void setUp() {
    this.userList = new ArrayList<>();
    this.userList.add(new User(1L, "Marian", "Kowalski", LocalDate.parse("1980-05-05"), BigDecimal.valueOf(3500.0), UserRole.WORKER, 1.0, new Address(123L, "Monte Cassino", "1", "01-121", "Warszawa")));
    this.userList.add(new User(2L, "Jan", "Mazur", LocalDate.parse("1995-11-25"), BigDecimal.valueOf(4000.0), UserRole.WORKER, 1.0, new Address(1L, "Kazimierzowska", "12", "03-542", "Siedlce")));
    this.userList.add(new User(3L, "Aleksandra", "Sadowska", LocalDate.parse("1999-08-14"), BigDecimal.valueOf(5000.0), UserRole.WORKER, 0.75, new Address(44L, "Warszawska", "16", "21-754", "Lublin")));

  }

  @Test
  void shouldFetchAllUsers() throws Exception {
    given(userRepository.findAll()).willReturn(userList);

    this.mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(userList.size())));
  }

  @Test
  void shouldFetchOneUserById() throws Exception {
    given(userRepository.findById(this.userList.get(0).getId())).willReturn(Optional.of(this.userList.get(0)));

    this.mockMvc.perform(get("/api/users/{id}", this.userList.get(0).getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(this.userList.get(0).getFirstName())))
        .andExpect(jsonPath("$.surname", is(this.userList.get(0).getSurname())))
        .andExpect(jsonPath("$.dateOfBirth", is(this.userList.get(0).getDateOfBirth().toString())));
  }

  @Test
  void shouldCreateNewUser() throws Exception {
    given(userRepository.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

    this.mockMvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.userList.get(0))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", is(this.userList.get(0).getFirstName())))
        .andExpect(jsonPath("$.surname", is(this.userList.get(0).getSurname())))
        .andExpect(jsonPath("$.dateOfBirth", is(this.userList.get(0).getDateOfBirth().toString())));
  }

  @Test
  void shouldUpdateUser() throws Exception {
    given(userRepository.save(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

    this.mockMvc.perform(put("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.userList.get(0))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is(this.userList.get(0).getFirstName())))
        .andExpect(jsonPath("$.surname", is(this.userList.get(0).getSurname())))
        .andExpect(jsonPath("$.dateOfBirth", is(this.userList.get(0).getDateOfBirth().toString())));
  }

  @Test
  void shouldDeleteUser() throws Exception {
    given(userRepository.findById(this.userList.get(0).getId())).willReturn(Optional.of(this.userList.get(0)));
    doNothing().when(userRepository).deleteById(this.userList.get(0).getId());

    this.mockMvc.perform(delete("/api/users/{id}", this.userList.get(0).getId()))
        .andExpect(status().isNoContent());
  }
}
