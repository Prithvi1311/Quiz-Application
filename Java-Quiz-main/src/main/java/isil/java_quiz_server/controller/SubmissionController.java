package isil.java_quiz_server.controller;

import isil.java_quiz_server.modal.Submission;
import isil.java_quiz_server.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        submission.setSubmissionTime(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @GetMapping("/user/{username}")
    public List<Submission> getUserSubmissions(@PathVariable String username) {
        return submissionRepository.findByUsername(username);
    }
}
