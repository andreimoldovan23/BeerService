package sfmc.brewery.events;

import lombok.*;
import sfmc.brewery.web.model.BeerDTO;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {
    private BeerDTO beerDTO;
}
