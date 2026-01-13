package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.color.ColorDto;
import az.edu.itbrains.karma_shop.repository.ColorRepository;
import az.edu.itbrains.karma_shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ColorDto> getAll() {
        return colorRepository.findAll().stream().map(c -> modelMapper.map(c, ColorDto.class)).toList();
    }
}
