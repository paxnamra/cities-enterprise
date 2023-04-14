package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<City> extractCities() {
        return (List<City>) repository.findAll();
    }

    public Optional<City> getCityByName(String name) {
        return repository.findCityByName(name);
    }
}
