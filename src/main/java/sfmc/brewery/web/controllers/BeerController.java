package sfmc.brewery.web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sfmc.brewery.services.interfaces.BeerService;
import sfmc.brewery.web.model.BeerDTO;
import sfmc.brewery.web.model.BeerPagedList;
import sfmc.brewery.web.model.BeerStyle;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
@Slf4j
public class BeerController {

    private final BeerService beerService;

    @Value("${default.page.size}") private Integer DEFAULT_PAGE_SIZE;
    @Value("${default.page.number}") private Integer DEFAULT_PAGE_NUMBER;

    @GetMapping
    public BeerPagedList getBeerList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "beerName", required = false) String beerName,
                                     @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle) {
        pageSize = (pageSize == null || pageSize < 0) ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = (pageNumber == null || pageNumber < 0) ? DEFAULT_PAGE_NUMBER : pageNumber;
        return beerService.getBeerList(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
    }

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
    public BeerDTO updateBeer(@PathVariable UUID beerId, @Validated @RequestBody BeerDTO beerDTO) {
        log.trace("Updating beer with uuid - {}, new value - {}", beerId, beerDTO);
        return beerService.updateBeer(beerId, beerDTO);
    }

}
