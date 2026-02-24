package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.RegisterDto;
import az.edu.itbrains.karma_shop.model.Role;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.RoleRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import az.edu.itbrains.karma_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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

        // ROLE_USER rolu ver
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return true;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
