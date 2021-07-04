package sfmc.brewery.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.services.inventory.interfaces.BeerInventoryService;
import sfmc.brewery.web.model.BeerDTO;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO entityToDTO(Beer beer) {
        return mapper.entityToDTO(beer);
    }

    @Override
    public BeerDTO entityToDTOWithInventory(Beer beer) {
        BeerDTO dto = mapper.entityToDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getInventoryOnHand(beer.getId()));
        return dto;
    }

    @Override
    public Beer dtoToEntity(BeerDTO beerDTO) {
        return mapper.dtoToEntity(beerDTO);
    }

}
