package az.edu.itbrains.karma_shop.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private Long id;
    private String name;
    private String email;
    private String subject;
    private String message;
}
