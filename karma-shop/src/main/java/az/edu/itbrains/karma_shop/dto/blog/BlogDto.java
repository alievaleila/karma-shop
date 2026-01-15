package az.edu.itbrains.karma_shop.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private String authorName;
    private LocalDateTime publishDate;
    private Long views;
    private Long comments;
    private List<String> categories;
    private String imageUrl;
}
