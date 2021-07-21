package sfmc.brewery.services.inventory.implementation;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sfmc.brewery.services.inventory.interfaces.BeerInventoryServiceFeignClient;
import sfmc.brewery.services.inventory.interfaces.InventoryFailoverFeignClient;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@RequiredArgsConstructor
@Slf4j
@Component
public class InventoryServiceFailoverFeignClient implements BeerInventoryServiceFeignClient {
    private final InventoryFailoverFeignClient failoverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDTO>> getInventoryOnHand(UUID beerId) {
        return failoverFeignClient.getInventoryOnHand();
    }
}
