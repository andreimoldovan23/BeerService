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
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class BeerController {

    private final BeerService beerService;

    @Value("${default.page.size}") private Integer DEFAULT_PAGE_SIZE;
    @Value("${default.page.number}") private Integer DEFAULT_PAGE_NUMBER;

    @GetMapping("/beer")
    public BeerPagedList getBeerList(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "beerName", required = false) String beerName,
                                     @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                     @RequestParam(value = "showInventory", required = false) Boolean showInventory) {
        pageSize = (pageSize == null || pageSize < 0) ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = (pageNumber == null || pageNumber < 0) ? DEFAULT_PAGE_NUMBER : pageNumber;
        showInventory = showInventory != null && showInventory;
        log.trace("Getting beer list w/ pageSize {}, pageNumber {}, showInventory {}", pageSize, pageNumber, showInventory);
        return beerService.getBeerList(beerName, beerStyle, showInventory, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/beer/{beerId}")
    public BeerDTO getBeerById(@PathVariable UUID beerId,
                               @RequestParam(value = "showInventory", required = false) Boolean showInventory) {
        log.trace("Getting beer with uuid - {}", beerId);
        showInventory = showInventory != null && showInventory;
        return beerService.getById(beerId, showInventory);
    }

    @GetMapping("/beerUpc/{upc}")
    public BeerDTO getBeerByUPC(@PathVariable String upc,
                                @RequestParam(value = "showInventory", required = false) Boolean showInventory) {
        log.trace("Getting beer by upc - {}", upc);
        showInventory = showInventory != null && showInventory;
        return beerService.getByUpc(upc, showInventory);
    }

    @PostMapping("/beer")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO saveBeer(@Validated @RequestBody BeerDTO beerDTO) {
        log.trace("Saving beer - {}", beerDTO);
        return beerService.saveBeer(beerDTO);
    }

    @PutMapping("/beer/{beerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BeerDTO updateBeer(@PathVariable UUID beerId, @Validated @RequestBody BeerDTO beerDTO) {
        log.trace("Updating beer with uuid - {}, new value - {}", beerId, beerDTO);
        return beerService.updateBeer(beerId, beerDTO);
    }

}
