package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.RegisterDto;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import az.edu.itbrains.karma_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(RegisterDto registerDto) {
        User findUser = userRepository.findByUsername(registerDto.getUsername());
        if (findUser != null) {
            return false;
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());

        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
}
