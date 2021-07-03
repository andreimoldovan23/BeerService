package sfmc.brewery.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.repositories.BeerRepository;
import sfmc.brewery.web.mappers.BeerMapper;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerStyle;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    private String beerString, invalidBeerString;

    void setUpClass() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Galaxy")
                .beerType(BeerStyle.ALE)
                .upc(337001001001L)
                .price(new BigDecimal("1.45")).build();
        beerString = objectMapper.writeValueAsString(beerDTO);

        beerDTO.setId(UUID.randomUUID());
        invalidBeerString = objectMapper.writeValueAsString(beerDTO);
    }

    @Test
    void getBeerByIdTest() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        UUID id = beer.getId();
        String result = objectMapper.writeValueAsString(beerMapper.entityToDTO(beer));

        mockMvc.perform(get("/api/v1/beer/{beerId}", id)
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(result))
                    .andExpect(status().isOk());
    }

    @Test
    void saveBeerTest() throws Exception {
        setUpClass();

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString))
                    .andExpect(status().isCreated());
    }

    @Test
    void updateBeerTest() throws Exception {
        setUpClass();
        UUID id = beerRepository.findAll().get(0).getId();

        mockMvc.perform(put("/api/v1/beer/{beerId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString))
                    .andExpect(status().isAccepted());
    }

    @Test
    void validationTest() throws Exception {
        setUpClass();

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBeerString))
                    .andExpect(status().isBadRequest());
    }
}