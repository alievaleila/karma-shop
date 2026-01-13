package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findByNameIgnoreCase(String name);
}
