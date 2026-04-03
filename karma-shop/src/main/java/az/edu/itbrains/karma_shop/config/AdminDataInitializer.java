package az.edu.itbrains.karma_shop.config;
import az.edu.itbrains.karma_shop.model.Role;
import az.edu.itbrains.karma_shop.model.User;
import az.edu.itbrains.karma_shop.repository.RoleRepository;
import az.edu.itbrains.karma_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_USER");
                    return roleRepository.save(role);
                });
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
            System.out.println("Admin istifadÉ™Ã§isi yaradÄ±ldÄ±: admin@karmashop.az / admin123");
        } else {
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            existingAdmin.setRoles(roles);
            userRepository.save(existingAdmin);
            System.out.println("Admin istifadÉ™Ã§isinin rollarÄ± yenilÉ™ndi: " + existingAdmin.getRoles().size() + " rol");        }
    }
}