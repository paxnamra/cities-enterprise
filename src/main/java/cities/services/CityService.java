package cities.services;

import cities.models.City;
import cities.repositories.CityRepository;
import cities.services.interfaces.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityService implements ICityService {
    private static final Logger LOG = LoggerFactory.getLogger(CityService.class);

    @Autowired
    private CityRepository repository;
    public List<City> getAllCities(Integer page, Integer size) {
        LOG.info("getAllCities for {} page and {} size.", page, size);
        return pageAsList(page, size);
    }

    public Optional<City> getCityByName(String name) {
        LOG.info("getCityByName for {}. ", name);
        return repository.findCityByName(toTitleCase(name));
    }

    public City updateCityNameAndImage(String cityName, City newCityData) {
        Optional<City> optionalCity = getCityByName(cityName);

        if (optionalCity.isPresent()) {
            City cityToEdit = optionalCity.get();
            City editedCity = new City(cityToEdit.getId(), toTitleCase(newCityData.getName()), newCityData.getImageLink());
            LOG.info("updateCityNameAndImage for {} with new data {}. ", cityName, newCityData);
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

    private List<City> pageAsList(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<City> cities = repository.findAll(paging);

        return cities.hasContent() ? cities.getContent() : new ArrayList<>();
    }
}
