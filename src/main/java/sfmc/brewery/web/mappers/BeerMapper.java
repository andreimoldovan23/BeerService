package sfmc.brewery.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.web.model.BeerDTO;

@Mapper(uses = DateMapper.class, config = NonBuilderConfig.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    @Mapping(target = "quantityOnHand", ignore = true)
    BeerDTO entityToDTO(Beer beer);

    @Mapping(target = "quantityOnHand", ignore = true)
    BeerDTO entityToDTOWithInventory(Beer beer);

    @Mapping(target = "quantityToBrew", ignore = true)
    @Mapping(target = "minOnHand", ignore = true)
    Beer dtoToEntity(BeerDTO dto);
}
