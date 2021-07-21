package sfmc.brewery.services.inventory.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@FeignClient(name = "inventory-failover-service")
public interface InventoryFailoverFeignClient {
    @GetMapping("/api/v1/inventory-failover")
    ResponseEntity<List<BeerInventoryDTO>> getInventoryOnHand();
}
