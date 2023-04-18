package cities.controllers;

import cities.models.City;
import cities.services.CityService;
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

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.getAllCities();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<City> getCity(@PathVariable @NonNull String cityName) {
        Optional<City> optionalCity = cityService.getCityByName(cityName);
        return optionalCity.map(city -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(city))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build());
    }

    @PostMapping("/{cityName}/edit")
    public ResponseEntity<City> updateCity(@PathVariable @NonNull String cityName, @RequestBody City updateCity) {
        City cityToUpdate = cityService.updateCityNameAndImage(cityName, updateCity);
        return new ResponseEntity<>(cityToUpdate, HttpStatus.OK);
    }
}
