package sfmc.brewery.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.repositories.BeerRepository;
import sfmc.brewery.services.interfaces.BeerService;
import sfmc.brewery.web.mappers.BeerMapper;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerPagedList;
import sfmc.brewery.web.model.BeerStyle;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Transactional
    @Override
    public BeerDTO getById(UUID id) {
        log.trace("Getting beer w/ id {}", id);
        return beerMapper.entityToDTO(beerRepository.findById(id).orElseThrow(() -> {
            log.trace("No beer w/ id {}", id);
            return new RuntimeException("No beer found");
        }));
    }

    @Transactional
    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        log.trace("Saving beer {}", beerDTO);
        return beerMapper.entityToDTO(beerRepository.save(beerMapper.dtoToEntity(beerDTO)));
    }

    @Transactional
    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        log.trace("Updating beer w/ id {}, value {}", id, beerDTO);
        Beer beer = beerRepository.findById(id).orElseThrow(() -> {
            log.trace("No beer w/ id {}", id);
            return new RuntimeException("No beer found");
        });

        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerType(beerDTO.getBeerType().toString());
        beer.setUpc(beerDTO.getUpc());
        beer.setPrice(beerDTO.getPrice());

        return beerMapper.entityToDTO(beerRepository.save(beer));
    }

    @Transactional
    @Override
    public BeerPagedList getBeerList(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        log.trace("Getting beers w/ name {}, style {}, pageNumber {}, pageSize {}",
                beerName, beerStyle, pageRequest.getPageNumber(), pageRequest.getPageSize());

        Page<Beer> beerPage;
        if (isOk(beerName) && isOk(beerStyle))
            beerPage = beerRepository.findAllByBeerNameAndBeerType(beerName, beerStyle.toString(), pageRequest);

        else if (isOk(beerName))
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);

        else if (isOk(beerStyle))
            beerPage = beerRepository.findAllByBeerType(beerStyle.toString(), pageRequest);

        else
            beerPage = beerRepository.findAll(pageRequest);

        return new BeerPagedList(beerPage.getContent().stream()
                .map(beerMapper::entityToDTO)
                .collect(Collectors.toList()), PageRequest.of(
                        beerPage.getPageable().getPageNumber(),
                        beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements()
        );
    }

    private boolean isOk(String s) {
        return s != null && !s.isEmpty();
    }

    private boolean isOk(BeerStyle beerStyle) {
        return beerStyle != null && !beerStyle.toString().isEmpty();
    }

}
