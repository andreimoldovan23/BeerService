package sfmc.brewery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import sfmc.brewery.events.BeerEvent;
import sfmc.brewery.events.NewInventoryEvent;
import sfmc.brewery.events.ValidateOrderEvent;
import sfmc.brewery.events.ValidationResponseEvent;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerOrderDTO;
import sfmc.brewery.web.model.BeerOrderLineDTO;

import java.util.HashMap;

@Configuration
public class JmsConfig {
    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory-request";
    public static final String VALIDATE_ORDER_QUEUE = "validate-order-request";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE = "validate-order-response";

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);

        HashMap<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put(BeerEvent.class.getSimpleName(), BeerEvent.class);
        typeIdMappings.put(BeerDTO.class.getSimpleName(), BeerDTO.class);
        typeIdMappings.put(NewInventoryEvent.class.getSimpleName(), NewInventoryEvent.class);
        typeIdMappings.put(BeerOrderDTO.class.getSimpleName(), BeerOrderDTO.class);
        typeIdMappings.put(BeerOrderLineDTO.class.getSimpleName(), BeerOrderLineDTO.class);
        typeIdMappings.put(ValidateOrderEvent.class.getSimpleName(), ValidateOrderEvent.class);
        typeIdMappings.put(ValidationResponseEvent.class.getSimpleName(), ValidationResponseEvent.class);

        converter.setTypeIdMappings(typeIdMappings);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
