package sfmc.brewery.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sfmc.brewery.domain.Beer;

import java.util.Optional;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByBeerName(String beerName, Pageable pageable);
    Page<Beer> findAllByBeerType(String beerType, Pageable pageable);
    Page<Beer> findAllByBeerNameAndBeerType(String beerName, String beerType, Pageable pageable);
    Optional<Beer> findByUpc(String upc);
}
