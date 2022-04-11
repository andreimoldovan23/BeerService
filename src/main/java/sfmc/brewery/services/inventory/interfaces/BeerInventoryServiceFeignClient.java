package sfmc.brewery.services.inventory.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sfmc.brewery.services.inventory.implementation.InventoryServiceFailoverFeignClient;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@FeignClient(name = "beer-inventory-service", fallback = InventoryServiceFailoverFeignClient.class)
public interface BeerInventoryServiceFeignClient {
    @GetMapping("/api/v1/beer/{beerId}/inventory")
    ResponseEntity<List<BeerInventoryDTO>> getInventoryOnHand(@PathVariable UUID beerId);
}
