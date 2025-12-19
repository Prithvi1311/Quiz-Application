package isil.java_quiz_server.repository;

import isil.java_quiz_server.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUsername(String username);
}
