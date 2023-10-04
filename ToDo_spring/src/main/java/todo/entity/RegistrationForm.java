package todo.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import todo.entity.User;

@Data
public class RegistrationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Size(min=3, message="Username must be at least 3 characters long")
    private String username;
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;
    @Email(message="Wrong e-mail format")
    private String email;
    @Size(min=6, message="Password must be at least 6 characters long")
    private String password;
    @Size(min=6, message="Password must be at least 6 characters long")
    private String password2;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, name, email, passwordEncoder.encode(password));
    }

}
