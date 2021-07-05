package sfmc.brewery.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.repositories.BeerRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Component
public class DefaultBootstrap implements CommandLineRunner {
    private final BeerRepository beerRepository;

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    @Override
    public void run(String... args) {
        if (beerRepository.count() == 0)
            loadBeer();
    }

    private void loadBeer() {
        Beer b1 = Beer.builder()
                .upc(BEER_1_UPC)
                .beerName("Mango Bobs")
                .beerType("IPA")
                .price(new BigDecimal("12.95"))
                .minOnHand(12)
                .quantityToBrew(200)
                .build();

        Beer b2 = Beer.builder()
                .upc(BEER_2_UPC)
                .beerName("Galaxy cat")
                .beerType("PALE_ALE")
                .price(new BigDecimal("12.95"))
                .minOnHand(12)
                .quantityToBrew(200)
                .build();

        Beer b3 = Beer.builder()
                .upc(BEER_3_UPC)
                .beerName("Pinball Porter")
                .beerType("PORTER")
                .price(new BigDecimal("12.95"))
                .minOnHand(12)
                .quantityToBrew(200)
                .build();

        beerRepository.save(b1);
        beerRepository.save(b2);
        beerRepository.save(b3);

        log.trace("Loaded beers, record count {}", beerRepository.count());
    }
}
