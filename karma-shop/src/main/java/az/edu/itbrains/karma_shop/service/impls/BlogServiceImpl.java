package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.blog.BlogDto;
import az.edu.itbrains.karma_shop.repository.BlogRepository;
import az.edu.itbrains.karma_shop.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<BlogDto> getAllBlogs() {
        List<BlogDto>blogDtoList=blogRepository.findAll()
                .stream()
                .map(b->modelMapper.map(b,BlogDto.class)).collect(Collectors.toList());
        return blogDtoList;
    }
}
