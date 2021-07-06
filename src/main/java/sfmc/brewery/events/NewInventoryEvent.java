package sfmc.brewery.events;

import lombok.NoArgsConstructor;
import sfmc.brewery.web.model.BeerDTO;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }

}
