package isil.java_quiz_server.controller;

import isil.java_quiz_server.modal.User;
import isil.java_quiz_server.repository.UserRepository;
import isil.java_quiz_server.requests.LoginRequest;
import isil.java_quiz_server.response.LoginResponse;
import isil.java_quiz_server.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LoginService loginService;

    @Test
    public void testRegister() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("testuser")));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        LoginResponse response = new LoginResponse(user, true);

        when(loginService.authenticateUser(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated", is(true)));
    }

    @Test
    public void testLoginFailure() throws Exception {
        LoginResponse response = new LoginResponse(null, false);

        when(loginService.authenticateUser(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.authenticated", is(false)));
    }
}
