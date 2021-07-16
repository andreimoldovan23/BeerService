package sfmc.brewery.events;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sfmc.brewery.web.model.BeerOrderDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderEvent implements Serializable {
    private BeerOrderDTO beerOrderDTO;
}
