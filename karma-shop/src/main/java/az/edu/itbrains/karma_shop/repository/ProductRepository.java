package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findTop6ByCategoryIdOrderByIdDesc(Long categoryId);
}
