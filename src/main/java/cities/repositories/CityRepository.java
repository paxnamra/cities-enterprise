package cities.repositories;

import cities.models.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing city data in the database, providing basic CRUD operations
 * and pagination support.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long>, PagingAndSortingRepository<City, Long> {

    /**
     * Finds a city by its name.
     *
     * @param name String representing the name of the city.
     * @return Optional containing the City object if found, otherwise an empty Optional.
     */
    Optional<City> findCityByName(String name);
}
