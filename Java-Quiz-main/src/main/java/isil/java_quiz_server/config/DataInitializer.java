package isil.java_quiz_server.config;

import isil.java_quiz_server.modal.Question;
import isil.java_quiz_server.modal.Quiz;
import isil.java_quiz_server.repository.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final QuizRepository quizRepository;

    public DataInitializer(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedQuiz("India General Knowledge", createIndiaQuestions());
        seedQuiz("Java Basics", createJavaQuestions());
    }

    private void seedQuiz(String title, List<Question> questions) {
        Quiz quiz = quizRepository.findAll().stream()
                .filter(q -> title.equals(q.getTitle()))
                .findFirst()
                .orElse(null);

        if (quiz == null) {
            quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setUsername("admin");
            quiz.setQuestions(questions);
            quizRepository.save(quiz);
            System.out.println("Seeded " + title + " Quiz.");
        } else if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            quiz.setQuestions(questions);
            quizRepository.save(quiz);
            System.out.println("Updated " + title + " Quiz with questions.");
        }
    }

    private List<Question> createIndiaQuestions() {
        Question q1 = new Question();
        q1.setText("What is the capital of India?");
        q1.setOptions(Arrays.asList("Mumbai", "New Delhi", "Kolkata", "Chennai"));
        q1.setCorrectOption("New Delhi");

        Question q2 = new Question();
        q2.setText("Which river is considered the holiest in India?");
        q2.setOptions(Arrays.asList("Yamuna", "Ganga", "Brahmaputra", "Godavari"));
        q2.setCorrectOption("Ganga");

        Question q3 = new Question();
        q3.setText("Who is known as the 'Father of the Nation' in India?");
        q3.setOptions(Arrays.asList("Jawaharlal Nehru", "Subhas Chandra Bose", "Mahatma Gandhi",
                "Sardar Vallabhbhai Patel"));
        q3.setCorrectOption("Mahatma Gandhi");

        Question q4 = new Question();
        q4.setText("Which is the national animal of India?");
        q4.setOptions(Arrays.asList("Lion", "Elephant", "Tiger", "Peacock"));
        q4.setCorrectOption("Tiger");

        Question q5 = new Question();
        q5.setText("What is the currency of India?");
        q5.setOptions(Arrays.asList("Dollar", "Rupee", "Euro", "Yen"));
        q5.setCorrectOption("Rupee");

        return Arrays.asList(q1, q2, q3, q4, q5);
    }

    private List<Question> createJavaQuestions() {
        Question q1 = new Question();
        q1.setText("What is the correct syntax for the main method in Java?");
        q1.setOptions(Arrays.asList(
                "public void main(String[] args)",
                "public static void main(String[] args)",
                "private static void main(String[] args)",
                "static void main(String args)"));
        q1.setCorrectOption("public static void main(String[] args)");

        Question q2 = new Question();
        q2.setText("What is the size of an int variable in Java?");
        q2.setOptions(Arrays.asList("8 bit", "16 bit", "32 bit", "64 bit"));
        q2.setCorrectOption("32 bit");

        Question q3 = new Question();
        q3.setText("What is the default value of a boolean variable in Java?");
        q3.setOptions(Arrays.asList("true", "false", "0", "null"));
        q3.setCorrectOption("false");

        Question q4 = new Question();
        q4.setText("Which method is used to find the length of a string in Java?");
        q4.setOptions(Arrays.asList("length()", "size()", "getLength()", "count()"));
        q4.setCorrectOption("length()");

        Question q5 = new Question();
        q5.setText("Which of these is NOT a Java keyword?");
        q5.setOptions(Arrays.asList("static", "void", "Boolean", "try"));
        q5.setCorrectOption("Boolean"); // 'Boolean' is a wrapper class, 'boolean' is the keyword

        return Arrays.asList(q1, q2, q3, q4, q5);
    }
}
