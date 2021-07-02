package sfmc.brewery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sfmc.brewery.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
