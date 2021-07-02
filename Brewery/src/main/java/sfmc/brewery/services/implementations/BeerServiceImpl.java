package sfmc.brewery.services.implementations;

import org.springframework.stereotype.Service;
import sfmc.brewery.services.interfaces.BeerService;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerStyle;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getById(UUID id) {
        return BeerDTO.builder()
                .id(id)
                .beerName("Galaxy")
                .beerType(BeerStyle.ALE)
                .price(new BigDecimal("1.45"))
                .build();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName(beerDTO.getBeerName())
                .beerType(beerDTO.getBeerType())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .build();
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beerDTO) {

    }

}
