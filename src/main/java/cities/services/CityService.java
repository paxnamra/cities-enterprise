package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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

    public City updateCityNameAndImage(String cityName, City editCity) {
        Optional<City> optionalCity = getCityByName(cityName);

        if (optionalCity.isPresent()) {
            City cityToEdit = optionalCity.get();
            City newCity = new City(cityToEdit.getId(), titleCasePath(editCity.getName()), editCity.getImageLink());
            return repository.save(newCity);
        } else {
            throw new NoSuchElementException("City not found");
        }
    }

    private String titleCasePath(String pathVariable) {
        return Arrays.stream(pathVariable.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
