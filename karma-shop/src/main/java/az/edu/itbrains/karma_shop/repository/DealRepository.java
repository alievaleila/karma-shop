package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal,Long> {
}
