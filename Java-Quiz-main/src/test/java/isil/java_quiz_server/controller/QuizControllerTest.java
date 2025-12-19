package isil.java_quiz_server.controller;

import isil.java_quiz_server.modal.Quiz;
import isil.java_quiz_server.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizRepository quizRepository;

    @Test
    public void testGetAllQuizzes() throws Exception {
        Quiz q1 = new Quiz();
        q1.setTitle("Java Basics");
        Quiz q2 = new Quiz();
        q2.setTitle("Spring Boot");

        when(quizRepository.findAll()).thenReturn(Arrays.asList(q1, q2));

        mockMvc.perform(get("/quizzes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Java Basics")));
    }

    @Test
    public void testCreateQuiz() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setTitle("New Quiz");

        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(post("/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Quiz\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Quiz")));
    }

    @Test
    public void testGetQuizById() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setTitle("Java Basics");
        quiz.setId(1L);

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        mockMvc.perform(get("/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Java Basics")));
    }

    @Test
    public void testGetQuizByIdNotFound() throws Exception {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/quizzes/1"))
                .andExpect(status().isNotFound());
    }
}
