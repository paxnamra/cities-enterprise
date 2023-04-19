package cities.services.interfaces;

import cities.models.City;

import java.util.List;
import java.util.Optional;

public interface ICityService {
    List<City> getAllCities(Integer page, Integer size);
    Optional<City> getCityByName(String name);
    City updateCityNameAndImage(String cityName, City newCityData);
}
