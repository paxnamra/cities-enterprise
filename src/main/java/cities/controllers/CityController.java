package cities.controllers;

import cities.models.City;
import cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityRepository repository;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = (List<City>) repository.findAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
