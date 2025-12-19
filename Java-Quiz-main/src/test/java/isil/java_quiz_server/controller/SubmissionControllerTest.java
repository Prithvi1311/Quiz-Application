package isil.java_quiz_server.controller;

import isil.java_quiz_server.modal.Submission;
import isil.java_quiz_server.repository.SubmissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(SubmissionController.class)
public class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubmissionRepository submissionRepository;

    @Test
    public void testCreateSubmission() throws Exception {
        Submission submission = new Submission();
        submission.setQuizTitle("Java Basics");
        submission.setUsername("user1");
        submission.setScore(80);

        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);

        mockMvc.perform(post("/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"quizTitle\":\"Java Basics\",\"username\":\"user1\",\"score\":80}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quizTitle", is("Java Basics")))
                .andExpect(jsonPath("$.score", is(80)));
    }

    @Test
    public void testGetAllSubmissions() throws Exception {
        Submission s1 = new Submission();
        s1.setUsername("user1");
        Submission s2 = new Submission();
        s2.setUsername("user2");

        List<Submission> submissions = Arrays.asList(s1, s2);
        when(submissionRepository.findAll()).thenReturn(submissions);

        mockMvc.perform(get("/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("user1")));
    }
}
