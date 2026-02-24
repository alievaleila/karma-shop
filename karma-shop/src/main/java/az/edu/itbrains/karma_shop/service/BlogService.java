package az.edu.itbrains.karma_shop.service;

import az.edu.itbrains.karma_shop.dto.blog.BlogDto;

import java.util.List;

public interface BlogService {

    List<BlogDto> getAllBlogs();

    BlogDto getById(Long id);
}
