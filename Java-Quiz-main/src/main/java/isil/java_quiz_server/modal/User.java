package isil.java_quiz_server.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username, password, email;
    private long phone;
    @com.fasterxml.jackson.annotation.JsonProperty("isTeacher")
    private boolean isTeacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("isTeacher")
    public boolean isTeacher() {
        return isTeacher;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("isTeacher")
    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
