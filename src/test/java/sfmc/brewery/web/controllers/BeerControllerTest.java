package sfmc.brewery.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerStyle;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String beerString, invalidBeerString;

    @BeforeAll
    static void setUpClass() throws JsonProcessingException {
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
        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                .andDo(document("api/v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer")
                        ),
                        responseFields(
                            fieldWithPath("id").description("Beer id - is an uuid").type(UUID.class),
                            fieldWithPath("version").description("Version number").type(Integer.class),
                            fieldWithPath("createdDate").description("Date of creation").type(OffsetDateTime.class),
                            fieldWithPath("lastModifiedDate").description("Date of modification").type(OffsetDateTime.class),
                            fieldWithPath("beerName").description("Name of beer").type(String.class),
                            fieldWithPath("beerType").description("Type of beer").type(BeerStyle.class),
                            fieldWithPath("upc").description("Universal product code of beer").type(Long.class),
                            fieldWithPath("price").description("Price").type(BigDecimal.class),
                            fieldWithPath("quantityOnHand").description("Quantity available").type(Integer.class)
                        )
                ));
    }

    @Test
    void saveBeerTest() throws Exception {
        ConstrainedFields constrainedFields = new ConstrainedFields(BeerDTO.class);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString))
                    .andExpect(status().isCreated())
                .andDo(document("api/v1/beer-save",
                        requestFields(
                            constrainedFields.withPath("id").ignored(),
                            constrainedFields.withPath("version").ignored(),
                            constrainedFields.withPath("createdDate").ignored(),
                            constrainedFields.withPath("lastModifiedDate").ignored(),
                            constrainedFields.withPath("beerName").description("Name of beer"),
                            constrainedFields.withPath("beerType").description("Type of beer"),
                            constrainedFields.withPath("upc").description("Universal product code of beer"),
                            constrainedFields.withPath("price").description("Price"),
                            constrainedFields.withPath("quantityOnHand").ignored()
                        ),
                        responseFields(
                                fieldWithPath("id").description("Beer id - is an uuid").type(UUID.class),
                                fieldWithPath("version").description("Version number").type(Integer.class),
                                fieldWithPath("createdDate").description("Date of creation").type(OffsetDateTime.class),
                                fieldWithPath("lastModifiedDate").description("Date of modification").type(OffsetDateTime.class),
                                fieldWithPath("beerName").description("Name of beer").type(String.class),
                                fieldWithPath("beerType").description("Type of beer").type(BeerStyle.class),
                                fieldWithPath("upc").description("Universal product code of beer").type(Long.class),
                                fieldWithPath("price").description("Price").type(BigDecimal.class),
                                fieldWithPath("quantityOnHand").description("Quantity available").type(Integer.class)
                        )
                ));
    }

    @Test
    void updateBeerTest() throws Exception {
        ConstrainedFields constrainedFields = new ConstrainedFields(BeerDTO.class);

        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerString))
                    .andExpect(status().isAccepted())
                .andDo(document("api/v1/beer-update",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer")
                        ),
                        requestFields(
                                constrainedFields.withPath("id").ignored(),
                                constrainedFields.withPath("version").ignored(),
                                constrainedFields.withPath("createdDate").ignored(),
                                constrainedFields.withPath("lastModifiedDate").ignored(),
                                constrainedFields.withPath("beerName").description("Name of beer"),
                                constrainedFields.withPath("beerType").description("Type of beer"),
                                constrainedFields.withPath("upc").description("Universal product code of beer"),
                                constrainedFields.withPath("price").description("Price"),
                                constrainedFields.withPath("quantityOnHand").ignored()
                        )
                ));
    }

    @Test
    void validationTest() throws Exception {
        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBeerString))
                    .andExpect(status().isBadRequest());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(
                    StringUtils.collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}