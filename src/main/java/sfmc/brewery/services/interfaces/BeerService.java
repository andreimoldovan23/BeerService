package sfmc.brewery.services.interfaces;

import org.springframework.data.domain.PageRequest;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerPagedList;
import sfmc.brewery.web.model.BeerStyle;

import java.util.UUID;

public interface BeerService {
    BeerDTO getById(UUID id);
    BeerDTO saveBeer(BeerDTO beerDTO);
    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);
    BeerPagedList getBeerList(String beerName, BeerStyle beerStyle, PageRequest pageRequest);
}
