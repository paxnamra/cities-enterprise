package cities.controllers;

import cities.models.City;
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

/**
 * CityController class is responsible for handling HTTP requests related to city data management.
 * It provides endpoints for retrieving a list of cities, getting a city by name, and updating city data.
 */
@RestController
@RequestMapping("/api/cities")
public class CityController {
    private static final Logger LOG = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    /**
     * Handles GET requests for retrieving a list of cities with optional pagination parameters.
     *
     * @param page Integer representing the page number (default: 0).
     * @param size Integer representing the page size (default: 10).
     * @return ResponseEntity containing a list of City objects and the HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<City>> getCities(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {

        List<City> list = cityService.getAllCities(page, size);
        LOG.info("Sent http request GET - getCities");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Handles GET requests for retrieving a city by name.
     *
     * @param cityName String representing the name of the city.
     * @return ResponseEntity containing the City object if found and the HTTP status, or a NOT_FOUND status.
     */
    @GetMapping("/{cityName}")
    public ResponseEntity<City> getCity(@PathVariable @NonNull String cityName) {
        Optional<City> optionalCity = cityService.getCityByName(cityName);
        LOG.info("Sent http request GET - getCity");
        return optionalCity.map(city -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(city))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build());
    }

    /**
     * Handles PUT requests for updating the name and image of a city.
     *
     * @param cityName   String representing the name of the city to update.
     * @param updateCity City object containing the new data for the city.
     * @return ResponseEntity containing the updated City object and the HTTP status.
     */
    @PutMapping("/{cityName}/edit")
    public ResponseEntity<City> updateCity(@PathVariable @NonNull String cityName, @RequestBody City updateCity) {
        City cityToUpdate = cityService.updateCityNameAndImage(cityName, updateCity);
        LOG.info("Sent http request PUT - updateCity");
        return new ResponseEntity<>(cityToUpdate, HttpStatus.OK);
    }
}
