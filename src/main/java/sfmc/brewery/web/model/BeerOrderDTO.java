package sfmc.brewery.web.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderDTO {
    private UUID id;
    private List<BeerOrderLineDTO> beerOrderLines;
}
