package cities.services.interfaces;

import cities.models.City;

import java.util.List;
import java.util.Optional;

/**
 * The ICityService interface defines the contract for managing city-related operations,
 * such as retrieving all cities, getting a city by name, and updating city data.
 */
public interface ICityService {

    /**
     * Retrieves all cities as a list, with optional pagination parameters.
     *
     * @param page Integer representing the page number.
     * @param size Integer representing the page size.
     * @return List of City objects.
     */
    List<City> getAllCities(Integer page, Integer size);

    /**
     * Retrieves a city by its name.
     *
     * @param name String representing the name of the city.
     * @return Optional containing the City object if found, otherwise an empty Optional.
     */
    Optional<City> getCityByName(String name);

    /**
     * Updates the name and image of a city.
     *
     * @param cityName    String representing the name of the city to update.
     * @param newCityData City object containing the new data for the city.
     * @return The updated City object.
     */
    City updateCityNameAndImage(String cityName, City newCityData);
}
