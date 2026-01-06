package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
