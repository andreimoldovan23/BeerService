package sfmc.brewery.events;

import lombok.NoArgsConstructor;
import sfmc.brewery.web.model.BeerDTO;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }

}
