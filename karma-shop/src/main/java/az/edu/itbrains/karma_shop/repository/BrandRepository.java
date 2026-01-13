package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByNameIgnoreCase(String name);
}
