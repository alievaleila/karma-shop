package az.edu.itbrains.karma_shop.config;

import az.edu.itbrains.karma_shop.model.Role;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.RoleRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // ROLE_ADMIN yaratmaq (əgər yoxdursa)
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        // ROLE_USER yaratmaq (əgər yoxdursa)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });

        // Admin istifadəçisi yaratmaq (əgər yoxdursa)
        User existingAdmin = userRepository.findByUsername("admin@karmashop.az");
        if (existingAdmin == null) {
            User admin = new User();
            admin.setUsername("admin@karmashop.az");
            admin.setPassword(passwordEncoder.encode("admin123"));
            
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            admin.setRoles(roles);
            
            userRepository.save(admin);
            System.out.println("Admin istifadəçisi yaradıldı: admin@karmashop.az / admin123");
        }
    }
}
