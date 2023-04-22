package cities.controllers;

import cities.models.City;
import cities.services.CityReader;
import cities.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {

        List<City> list = cityService.getAllCities(page, size);
        logger.info("Sent http request GET - getCities");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<City> getCity(@PathVariable @NonNull String cityName) {
        Optional<City> optionalCity = cityService.getCityByName(cityName);
        logger.info("Sent http request GET - getCity");
        return optionalCity.map(city -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(city))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build());
    }

    @PutMapping("/{cityName}/edit")
    public ResponseEntity<City> updateCity(@PathVariable @NonNull String cityName, @RequestBody City updateCity) {
        City cityToUpdate = cityService.updateCityNameAndImage(cityName, updateCity);
        logger.info("Sent http request PUT - updateCity");
        return new ResponseEntity<>(cityToUpdate, HttpStatus.OK);
    }
}
