package sfmc.brewery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import sfmc.brewery.events.BeerEvent;
import sfmc.brewery.events.NewInventoryEvent;
import sfmc.brewery.web.model.BeerDTO;

import java.util.HashMap;

@Configuration
public class JmsConfig {
    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory-request";

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);

        HashMap<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put(BeerEvent.class.getSimpleName(), BeerEvent.class);
        typeIdMappings.put(BeerDTO.class.getSimpleName(), BeerDTO.class);
        typeIdMappings.put(NewInventoryEvent.class.getSimpleName(), NewInventoryEvent.class);

        converter.setTypeIdMappings(typeIdMappings);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
