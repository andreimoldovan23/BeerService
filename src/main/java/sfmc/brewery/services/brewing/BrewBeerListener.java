package sfmc.brewery.services.brewing;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sfmc.brewery.config.JmsConfig;
import sfmc.brewery.events.BrewBeerEvent;
import sfmc.brewery.events.NewInventoryEvent;
import sfmc.brewery.web.model.BeerDTO;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDTO dto = brewBeerEvent.getBeerDTO();
        dto.setQuantityOnHand(dto.getQuantityOnHand() + dto.getQuantityToBrew());

        log.trace("Brewed {} beer for {}", dto.getQuantityToBrew(), dto.getId());
        log.trace("Having {} beer for {}", dto.getQuantityOnHand(), dto.getId());
        log.trace("Sending new inventory event for {}...", dto.getId());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE,
                new NewInventoryEvent(dto));
    }
}
