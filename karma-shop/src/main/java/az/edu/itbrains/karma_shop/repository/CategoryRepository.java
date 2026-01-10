package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByParentIsNull();
}
