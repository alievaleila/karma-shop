package az.edu.itbrains.karma_shop.service.impls;

import az.edu.itbrains.karma_shop.dto.deal.DealDto;
import az.edu.itbrains.karma_shop.repository.DealRepository;
import az.edu.itbrains.karma_shop.service.DealService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DealDto> getAllDeals() {
        return dealRepository.findAll().stream().map(d->modelMapper.map(d, DealDto.class)).toList();
    }
}
