package sfmc.brewery.services.inventory.interfaces;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getInventoryOnHand(UUID id);
}
