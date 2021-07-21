package sfmc.brewery.services.inventory.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sfmc.brewery.services.inventory.implementation.BeerInventoryServiceRestTemplateImpl;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@FeignClient(name = "beer-inventory-service")
public interface BeerInventoryServiceFeignClient {
    @GetMapping(BeerInventoryServiceRestTemplateImpl.inventoryUrl)
    ResponseEntity<List<BeerInventoryDTO>> getInventoryOnHand(@PathVariable UUID beerId);
}
