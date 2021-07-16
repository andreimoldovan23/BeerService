package sfmc.brewery.services.validation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import sfmc.brewery.config.JmsConfig;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.events.ValidateOrderEvent;
import sfmc.brewery.events.ValidationResponseEvent;
import sfmc.brewery.repositories.BeerRepository;
import sfmc.brewery.web.model.BeerOrderDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateOrderListener {
    private final JmsTemplate jmsTemplate;
    private final BeerRepository beerRepository;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderEvent event) {
        List<String> beerUpcs = beerRepository.findAll().stream()
                .map(Beer::getUpc)
                .collect(Collectors.toList());

        log.trace("Current beer upcs: {}", beerUpcs);

        BeerOrderDTO beerOrderDTO = event.getBeerOrderDTO();

        log.trace("Received order for validation: {}", beerOrderDTO);

        try {
            beerOrderDTO.getBeerOrderLines().forEach(line -> {
                if (!beerUpcs.contains(line.getUpc())) {
                    jmsTemplate.convertAndSend(new ValidationResponseEvent(beerOrderDTO.getId(), false));
                    log.trace("Upc {} does not exist", line.getUpc());
                    throw new RuntimeException("Invalid order");
                }
            });

            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    new ValidationResponseEvent(beerOrderDTO.getId(), true));
        } catch (RuntimeException re) {
            log.trace("Invalid order");
        }
    }
}
