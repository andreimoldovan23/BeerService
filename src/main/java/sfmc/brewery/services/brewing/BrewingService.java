package sfmc.brewery.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sfmc.brewery.config.JmsConfig;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.events.BrewBeerEvent;
import sfmc.brewery.repositories.BeerRepository;
import sfmc.brewery.web.mappers.BeerMapper;
import sfmc.brewery.web.model.BeerDTO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Transactional
    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            BeerDTO beerDTO = beerMapper.entityToDTOWithInventory(beer);
            Integer inventory = beerDTO.getQuantityOnHand();

            log.trace("Min on hand for {}: {}", beer.getId(), beer.getMinOnHand());
            log.trace("Inventory for {}: {}", beer.getId(), inventory);

            if (beer.getMinOnHand() >= inventory) {
                log.trace("Sending brew beer event for {}...", beer.getId());
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerDTO));
            }
        });
    }
}
