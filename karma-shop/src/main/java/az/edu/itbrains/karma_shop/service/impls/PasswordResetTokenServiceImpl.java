package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.model.PasswordResetToken;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.PasswordResetTokenRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import az.edu.itbrains.karma_shop.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String createTokenForUser(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (userOptional.isEmpty()) return null; // Əgər istifadəçi tapılmırsa, null qaytarırıq

        User user = userOptional.get(); // Əgər varsa, user-i alırıq

        String token = UUID.randomUUID().toString();

        PasswordResetToken t = new PasswordResetToken();
        t.setToken(token);
        t.setUser(user);
        t.setUsed(false);
        t.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(t);

        return token;
    }


    @Override
    public boolean isTokenValid(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> !t.isUsed()) // Token artıq istifadə olunubmu yoxlanır
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now())) // Tokenin vaxtı keçibmi yoxlanır
                .isPresent();
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        var opt = tokenRepository.findByToken(token);
        if (opt.isEmpty()) return false;

        PasswordResetToken t = opt.get();
        if (t.isUsed() || t.getExpiresAt().isBefore(LocalDateTime.now())) return false;

        User user = t.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Yeni şifrəni kodlayırıq
        userRepository.save(user);

        t.setUsed(true); // Token artıq istifadə edilib
        tokenRepository.save(t);

        return true;
    }
}
