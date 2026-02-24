package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findTop8ByOrderByIdDesc();

    List<Product> findTop6ByCategoryIdOrderByIdDesc(Long categoryId);

    @Query(value = """
            SELECT p.*
            FROM products p
            WHERE (:categoryId IS NULL OR p.category_id = :categoryId)
              AND (:brandId IS NULL OR p.brand_id = :brandId)
              AND (:colorId IS NULL OR p.color_id = :colorId)
              AND (:minPrice IS NULL OR p.price >= :minPrice)
              AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            ORDER BY p.id DESC
            LIMIT 6
            """, nativeQuery = true)
    List<Product> filterTop6(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("colorId") Long colorId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
