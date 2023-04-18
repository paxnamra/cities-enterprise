package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<City> getAllCities(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<City> getCityByName(String name) {
        return repository.findCityByName(toTitleCase(name));
    }

    public City updateCityNameAndImage(String cityName, City newCityData) {
        Optional<City> optionalCity = getCityByName(cityName);

        if (optionalCity.isPresent()) {
            City cityToEdit = optionalCity.get();
            City editedCity = new City(cityToEdit.getId(), toTitleCase(newCityData.getName()), newCityData.getImageLink());
            return repository.save(editedCity);
        } else {
            throw new NoSuchElementException("City not found");
        }
    }

    private String toTitleCase(String pathVariable) {
        return Arrays.stream(pathVariable.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
