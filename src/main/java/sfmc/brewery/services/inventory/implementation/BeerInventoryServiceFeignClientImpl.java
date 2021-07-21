package sfmc.brewery.services.inventory.implementation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sfmc.brewery.services.inventory.interfaces.BeerInventoryService;
import sfmc.brewery.services.inventory.interfaces.BeerInventoryServiceFeignClient;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@Profile("localdiscovery")
@Slf4j
@RequiredArgsConstructor
@Service
public class BeerInventoryServiceFeignClientImpl implements BeerInventoryService {
    private final BeerInventoryServiceFeignClient feignClient;

    @Override
    public Integer getInventoryOnHand(UUID id) {
        log.trace("Getting inventory on hand for beer {}", id);

        ResponseEntity<List<BeerInventoryDTO>> entity = feignClient.getInventoryOnHand(id);

        Integer onHand = Objects.requireNonNull(entity.getBody()).stream()
                .mapToInt(BeerInventoryDTO::getQuantityOnHand)
                .sum();

        log.trace("Quantity on hand for beer {} is {}", id, onHand);
        return onHand;
    }
}
