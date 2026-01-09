package az.edu.itbrains.karma_shop.repository;

import az.edu.itbrains.karma_shop.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
