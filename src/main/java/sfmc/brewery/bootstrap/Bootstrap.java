package sfmc.brewery.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sfmc.brewery.domain.Beer;
import sfmc.brewery.repositories.BeerRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0)
            loadBeer();
    }

    private void loadBeer() {
        Beer b1 = Beer.builder()
                .beerName("Galaxy cat")
                .beerType("ALE")
                .minOnHand(100)
                .quantityToBrew(100)
                .price(new BigDecimal("12.45"))
                .upc(337010000001L)
                .build();

        Beer b2 = Beer.builder()
                .beerName("Fuller")
                .beerType("PORTER")
                .minOnHand(34)
                .quantityToBrew(123)
                .price(new BigDecimal("14.45"))
                .upc(338010000001L)
                .build();

        beerRepository.save(b1);
        beerRepository.save(b2);
    }
}
