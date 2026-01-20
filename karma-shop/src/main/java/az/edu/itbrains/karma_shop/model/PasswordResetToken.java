package az.edu.itbrains.karma_shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime expiresAt;
    private boolean used;

    @ManyToOne
    private User user;
}
