package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.contact.ContactDto;
import az.edu.itbrains.karma_shop.model.Contact;
import az.edu.itbrains.karma_shop.repository.ContactRepository;
import az.edu.itbrains.karma_shop.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public void addContact(ContactDto contactDto) {
        Contact contact=new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setSubject(contactDto.getSubject());
        contact.setMessage(contactDto.getMessage());

        contactRepository.save(contact);
    }
}
