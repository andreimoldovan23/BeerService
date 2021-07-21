package sfmc.brewery.services.inventory.implementation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sfmc.brewery.services.inventory.interfaces.BeerInventoryService;
import sfmc.brewery.services.inventory.model.BeerInventoryDTO;

@Profile("!localdiscovery")
@Service
@Slf4j
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    @Value("${inventory.host}") private String inventoryHost;
    public static final String inventoryUrl = "/api/v1/beer/{beerId}/inventory";

    private final RestTemplate restTemplate;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    @Override
    public Integer getInventoryOnHand(UUID id) {
        log.trace("Getting inventory on hand for beer {}", id);

        ResponseEntity<List<BeerInventoryDTO>> entity = restTemplate.exchange(
                inventoryHost + inventoryUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}, id
        );

        Integer onHand = Objects.requireNonNull(entity.getBody()).stream()
            .mapToInt(BeerInventoryDTO::getQuantityOnHand)
            .sum();

        log.trace("Quantity on hand for beer {} is {}", id, onHand);
        return onHand;
    }

}
