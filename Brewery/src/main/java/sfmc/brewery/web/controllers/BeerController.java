package sfmc.brewery.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sfmc.brewery.services.interfaces.BeerService;
import sfmc.brewery.web.model.BeerDTO;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
@Slf4j
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public BeerDTO getBeerById(@PathVariable UUID beerId) {
        log.trace("Getting beer with uuid - {}", beerId);
        return beerService.getById(beerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO saveBeer(@Validated @RequestBody BeerDTO beerDTO) {
        log.trace("Saving beer - {}", beerDTO);
        return beerService.saveBeer(beerDTO);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBeer(@PathVariable UUID beerId, @Validated @RequestBody BeerDTO beerDTO) {
        log.trace("Updating beer with uuid - {}, new value - {}", beerId, beerDTO);
        beerService.updateBeer(beerId, beerDTO);
    }

}
