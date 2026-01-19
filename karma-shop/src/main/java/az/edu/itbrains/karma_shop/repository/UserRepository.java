package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
