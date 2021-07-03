package sfmc.brewery.services.interfaces;

import sfmc.brewery.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {
    BeerDTO getById(UUID id);
    BeerDTO saveBeer(BeerDTO beerDTO);
    void updateBeer(UUID id, BeerDTO beerDTO);
}