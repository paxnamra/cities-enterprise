package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<City> extractCities() {
        return (List<City>) repository.findAll();
    }

    public Optional<City> getCityByName(String name) {
        return repository.findCityByName(titleCasePath(name));
    }

    private String titleCasePath(String pathVariable) {
        return Arrays.stream(pathVariable.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
