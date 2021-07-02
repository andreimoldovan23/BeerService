package sfmc.brewery.web.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerStyle;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String beerString1, beerString2, invalidBeerString;

    @BeforeAll
    static void setUpClass() throws JsonProcessingException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Galaxy")
                .beerType(BeerStyle.ALE)
                .price(new BigDecimal("1.45")).build();

        beerString1 = objectMapper.writeValueAsString(beerDTO);

        beerDTO.setUpc(337001001001L);
        beerString2 = objectMapper.writeValueAsString(beerDTO);

        beerDTO.setId(UUID.randomUUID());
        invalidBeerString = objectMapper.writeValueAsString(beerDTO);
    }

    @Test
    void getBeerByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(beerString1))
                    .andExpect(status().isOk());
    }

    @Test
    void saveBeerTest() throws Exception {
        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString2))
                    .andExpect(content().json(beerString2))
                    .andExpect(status().isCreated());
    }

    @Test
    void updateBeerTest() throws Exception {
        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString2))
                    .andExpect(status().isAccepted());
    }

    @Test
    void validationTest() throws Exception {
        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBeerString))
                    .andExpect(status().isBadRequest());
    }
}