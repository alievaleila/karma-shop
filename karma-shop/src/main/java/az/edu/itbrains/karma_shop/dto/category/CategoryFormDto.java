package az.edu.itbrains.karma_shop.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFormDto {
    private Long id;
    private String name;
    private Long parentId;
}
